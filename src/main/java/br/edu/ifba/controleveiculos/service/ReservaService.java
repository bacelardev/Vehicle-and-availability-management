package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Reserva;
import br.edu.ifba.controleveiculos.model.ReservaInfo;
import br.edu.ifba.controleveiculos.model.StatusReserva;
import br.edu.ifba.controleveiculos.model.StatusVeiculo;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Gestão de reservas (Guilherme — US04).
 * <p>
 * Regras aplicadas:
 * <ul>
 *   <li>RN02: impede reservas sobrepostas para o mesmo veículo;</li>
 *   <li>RN03: impede reservas enquanto o veículo está ALUGADO ou em MANUTENCAO.</li>
 * </ul>
 * Armazenamento via estrutura acadêmica {@link Lista}.
 */
public class ReservaService implements ReservaPort {

    private final Lista<Reserva> reservas = new Lista<>();
    private long proximoId = 1;
    private final VeiculoPort veiculos;
    private final ManutencaoPort manutencao;

    public ReservaService(VeiculoPort veiculos, ManutencaoPort manutencao) {
        this.veiculos = veiculos;
        this.manutencao = manutencao;
    }

    /**
     * Cria uma reserva. O veículo deve estar DISPONIVEL e não sobrepor nenhuma
     * reserva ativa nem período de manutenção.
     */
    public Reserva reservar(String placa, String clienteId, LocalDateTime inicio, LocalDateTime fim) {
        if (placa == null || clienteId == null || inicio == null || fim == null) {
            throw new RegraNegocioException("Dados da reserva são obrigatórios.");
        }
        if (!fim.isAfter(inicio)) {
            throw new RegraNegocioException("A data de fim deve ser posterior ao início.");
        }
        if (!veiculos.existe(placa)) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        StatusVeiculo status = veiculos.getStatus(placa);
        // RN03: manutenção e aluguel bloqueiam; RESERVADO é bloqueado pelo teste de sobreposição
        if (status == StatusVeiculo.MANUTENCAO || status == StatusVeiculo.ALUGADO) {
            throw new VeiculoIndisponivelException(
                    "O veículo " + placa + " não pode ser reservado: status " + status + ".");
        }
        if (manutencao.temManutencaoAtivaNoPeriodo(placa, inicio, fim)) {
            throw new VeiculoIndisponivelException(
                    "O veículo " + placa + " está com manutenção no período solicitado.");
        }
        // RN02: sobreposição de reservas
        if (existeReservaAtivaNoPeriodo(placa, inicio, fim)) {
            throw new RegraNegocioException(
                    "Já existe uma reserva para o veículo " + placa + " nesse período.");
        }
        Reserva r = new Reserva(proximoId++, placa.toUpperCase(), clienteId, inicio, fim);
        reservas.adicionar(r);
        veiculos.atualizarStatus(placa, StatusVeiculo.RESERVADO);
        return r;
    }

    public void cancelar(long id) {
        Reserva r = buscarPorId(id);
        if (r.getStatus() != StatusReserva.ATIVA) {
            throw new RegraNegocioException("Apenas reservas ativas podem ser canceladas.");
        }
        r.setStatus(StatusReserva.CANCELADA);
        veiculos.atualizarStatus(r.getPlaca(), StatusVeiculo.DISPONIVEL);
    }

    public void marcarComoUtilizada(long reservaId) {
        Reserva r = buscarPorId(reservaId);
        if (r.getStatus() != StatusReserva.ATIVA) {
            throw new RegraNegocioException("A reserva " + reservaId + " não está ativa.");
        }
        r.setStatus(StatusReserva.UTILIZADA);
    }

    public Reserva buscarPorId(long id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) return r;
        }
        throw new RegraNegocioException("Reserva não encontrada: " + id);
    }

    @Override
    public Optional<ReservaInfo> buscarReservaAtiva(String placa, String clienteId) {
        if (placa == null || clienteId == null) return Optional.empty();
        String alvo = placa.toUpperCase();
        for (Reserva r : reservas) {
            if (r.estaAtiva()
                    && r.getPlaca().equalsIgnoreCase(alvo)
                    && r.getClienteId().equals(clienteId)) {
                return Optional.of(paraInfo(r));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existeReservaAtivaNoPeriodo(String placa, LocalDateTime inicio, LocalDateTime fim) {
        if (placa == null) return false;
        String alvo = placa.toUpperCase();
        for (Reserva r : reservas) {
            if (r.estaAtiva()
                    && r.getPlaca().equalsIgnoreCase(alvo)
                    && r.sobreponAoIntervalo(inicio, fim)) {
                return true;
            }
        }
        return false;
    }

    public Lista<Reserva> listar() {
        return reservas;
    }

    private static ReservaInfo paraInfo(Reserva r) {
        return new ReservaInfo(r.getId(), r.getClienteId(), r.getPlaca(),
                r.getDataInicio(), r.getDataFim());
    }
}
