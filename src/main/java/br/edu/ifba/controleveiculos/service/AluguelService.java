package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Aluguel;
import br.edu.ifba.controleveiculos.model.DevolucaoResultado;
import br.edu.ifba.controleveiculos.model.HistoricoAluguel;
import br.edu.ifba.controleveiculos.model.ReservaInfo;
import br.edu.ifba.controleveiculos.model.StatusVeiculo;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Regras de US05 (Retirada) e US06 (Devolução) — aluguéis e quilometragem.
 * <p>
 * (contribuição de Mateus Almeida). Preserva a lógica original:
 * RN04 (km devolução ≥ km retirada), cálculo de atraso e encaminhamento para
 * manutenção quando o veículo atingir o limite de quilometragem.
 */
public class AluguelService {

    private final AluguelRepository repositorio;
    private final HistoricoAluguel historico;
    private final VeiculoPort veiculos;
    private final ReservaPort reservas;
    private final ManutencaoPort manutencao;
    private final Clock relogio;

    public AluguelService(AluguelRepository repositorio, HistoricoAluguel historico,
                          VeiculoPort veiculos, ReservaPort reservas, ManutencaoPort manutencao) {
        this(repositorio, historico, veiculos, reservas, manutencao, Clock.systemDefaultZone());
    }

    /** Construtor com relógio injetável (usado nos testes de atraso). */
    public AluguelService(AluguelRepository repositorio, HistoricoAluguel historico,
                          VeiculoPort veiculos, ReservaPort reservas, ManutencaoPort manutencao,
                          Clock relogio) {
        this.repositorio = repositorio;
        this.historico = historico;
        this.veiculos = veiculos;
        this.reservas = reservas;
        this.manutencao = manutencao;
        this.relogio = relogio;
    }

    /**
     * US05 — registra a retirada.
     *
     * @param dataPrevistaDevolucao obrigatória quando não há reserva; quando há
     *                              reserva, pode ser null e usa-se o fim da reserva.
     */
    public Aluguel registrarRetirada(String placa, String clienteId, LocalDateTime dataPrevistaDevolucao) {
        // GDV-60: identificar o veículo
        if (!veiculos.existe(placa)) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        if (repositorio.buscarEmAndamentoPorPlaca(placa).isPresent()) {
            throw new VeiculoIndisponivelException("O veículo " + placa + " já está em um aluguel em andamento.");
        }

        // GDV-61: reserva relacionada, quando existir
        Optional<ReservaInfo> reserva = reservas.buscarReservaAtiva(placa, clienteId);

        // GDV-64 / GDV-65: validar disponibilidade e impedir retirada indisponível
        StatusVeiculo status = veiculos.getStatus(placa);
        boolean liberado = status == StatusVeiculo.DISPONIVEL
                || (status == StatusVeiculo.RESERVADO && reserva.isPresent());
        if (!liberado) {
            String motivo = (status == StatusVeiculo.RESERVADO)
                    ? "está reservado para outro cliente"
                    : "está com status " + status;
            throw new VeiculoIndisponivelException("Retirada negada: o veículo " + placa + " " + motivo + ".");
        }

        LocalDateTime agora = LocalDateTime.now(relogio);
        LocalDateTime prevista = reserva.map(ReservaInfo::getFim).orElse(dataPrevistaDevolucao);
        if (prevista == null) {
            throw new RegraNegocioException("Informe a data prevista de devolução (não há reserva para este veículo).");
        }
        if (!prevista.isAfter(agora)) {
            throw new RegraNegocioException("A data prevista de devolução deve ser posterior à retirada.");
        }

        // GDV-59 / 62 / 67: registrar retirada, data/hora e início do período de utilização
        int kmInicial = veiculos.getQuilometragem(placa);
        Long reservaId = reserva.map(ReservaInfo::getId).orElse(null);
        Aluguel aluguel = new Aluguel(repositorio.gerarId(), placa.toUpperCase(), clienteId, reservaId,
                agora, kmInicial, prevista);
        repositorio.salvar(aluguel);

        // GDV-63: atualizar o status do veículo
        veiculos.atualizarStatus(placa, StatusVeiculo.ALUGADO);
        reserva.ifPresent(r -> reservas.marcarComoUtilizada(r.getId()));
        return aluguel;
    }

    /** US06 — devolução sem observações. */
    public DevolucaoResultado registrarDevolucao(String placa, int kmDevolucao) {
        return registrarDevolucao(placa, kmDevolucao, null);
    }

    /**
     * US06 — registra a devolução.
     * Calcula km percorridos (RN04) e atraso, grava no histórico e, conforme a
     * necessidade de manutenção, encaminha o veículo para manutenção ou o libera
     * para novas reservas.
     */
    public DevolucaoResultado registrarDevolucao(String placa, int kmDevolucao, String observacoes) {
        // GDV-70: identificar o veículo em utilização
        Aluguel aluguel = repositorio.buscarEmAndamentoPorPlaca(placa)
                .orElseThrow(() -> new RegraNegocioException(
                        "Não há aluguel em andamento para o veículo " + placa));

        // RN04: km de devolução não pode ser menor que a de retirada
        if (kmDevolucao < aluguel.getKmRetirada()) {
            throw new RegraNegocioException("A quilometragem de devolução (" + kmDevolucao
                    + ") não pode ser menor que a de retirada (" + aluguel.getKmRetirada() + ").");
        }

        // GDV-71 / 73: data/hora e informações da devolução
        aluguel.finalizar(LocalDateTime.now(relogio), kmDevolucao, observacoes);
        historico.registrar(aluguel);
        veiculos.atualizarQuilometragem(placa, kmDevolucao);

        // GDV-74: atraso
        Duration atraso = aluguel.calcularAtraso();

        // GDV-72 / 75 / 76: status do veículo + encaminhamento para manutenção
        boolean manutencaoNecessaria = manutencao.necessitaManutencao(placa, kmDevolucao);
        if (manutencaoNecessaria) {
            manutencao.encaminharParaManutencao(placa,
                    "Necessidade identificada na devolução (" + kmDevolucao + " km).");
            veiculos.atualizarStatus(placa, StatusVeiculo.MANUTENCAO);
        } else {
            veiculos.atualizarStatus(placa, StatusVeiculo.DISPONIVEL);
        }

        return new DevolucaoResultado(aluguel, aluguel.calcularKmPercorridos(), atraso, manutencaoNecessaria);
    }

    public List<Aluguel> consultarHistorico() {
        return historico.listarMaisRecentesPrimeiro();
    }

    public List<Aluguel> consultarHistoricoPorPlaca(String placa) {
        return historico.consultarPorPlaca(placa);
    }

    public List<Aluguel> consultarHistoricoPorCliente(String clienteId) {
        return historico.consultarPorCliente(clienteId);
    }
}
