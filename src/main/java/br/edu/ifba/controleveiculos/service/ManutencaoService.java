package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Manutencao;
import br.edu.ifba.controleveiculos.model.PrioridadeManutencao;
import br.edu.ifba.controleveiculos.model.StatusManutencao;
import br.edu.ifba.controleveiculos.model.StatusVeiculo;
import br.edu.ifba.controleveiculos.model.TipoManutencao;
import br.edu.ifba.controleveiculos.model.Veiculo;
import br.edu.ifba.controleveiculos.structures.Fila;
import br.edu.ifba.controleveiculos.structures.FilaPrioridade;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.time.LocalDateTime;

/**
 * Gestão de manutenção de veículos (Rodolfo — US07/US08).
 * <p>
 * Regras aplicadas:
 * <ul>
 *   <li>RN03: ao bloquear para manutenção, o veículo recebe status MANUTENCAO e
 *       não pode ser reservado ou alugado;</li>
 *   <li>RN03: manutenção ativa em período bloqueado impede disponibilidade;</li>
 *   <li>US08: identifica veículos que atingiram o limite de quilometragem.</li>
 * </ul>
 * <p>
 * Consome as estruturas acadêmicas {@link Fila} (FIFO) e
 * {@link FilaPrioridade} (atendimento prioritário), conforme documentação.
 */
public class ManutencaoService implements ManutencaoPort {

    private final VeiculoPort veiculos;
    private final Lista<Manutencao> todos = new Lista<>();
    private final Fila<Manutencao> filaEspera = new Fila<>();
    private final FilaPrioridade<Manutencao> filaPrioridade = new FilaPrioridade<>();
    private final long[] proximoId = {1};
    private int limiteKmManutencao = 10000;

    public ManutencaoService(VeiculoPort veiculos) {
        this.veiculos = veiculos;
    }

    public int getLimiteKmManutencao() {
        return limiteKmManutencao;
    }

    public void setLimiteKmManutencao(int limiteKmManutencao) {
        if (limiteKmManutencao <= 0) {
            throw new IllegalArgumentException("Limite de km para manutenção deve ser positivo.");
        }
        this.limiteKmManutencao = limiteKmManutencao;
    }

    // ---- ManutencaoPort ----

    @Override
    public boolean necessitaManutencao(String placa, int kmAtual) {
        return kmAtual >= limiteKmManutencao;
    }

    @Override
    public void encaminharParaManutencao(String placa, String motivo) {
        if (!veiculos.existe(placa)) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        int km = veiculos.getQuilometragem(placa);
        PrioridadeManutencao prioridade = PrioridadeManutencao.aPartirDeKm(km, limiteKmManutencao);
        Manutencao m = new Manutencao(proximoId(), placa.toUpperCase(), motivo,
                TipoManutencao.CORRETIVA, prioridade, km, LocalDateTime.now(), null);
        registrar(m);
        veiculos.atualizarStatus(placa, StatusVeiculo.MANUTENCAO);
    }

    @Override
    public boolean temManutencaoAtivaNoPeriodo(String placa, LocalDateTime inicio, LocalDateTime fim) {
        if (placa == null || inicio == null || fim == null) return false;
        String alvo = placa.toUpperCase();
        for (Manutencao m : todos) {
            if (m.estaAtiva() && m.getPlaca().equalsIgnoreCase(alvo) && m.sobreponAoIntervalo(inicio, fim)) {
                return true;
            }
        }
        return false;
    }

    // ---- US07: bloqueio / agendamento de manutenção ----

    /** Agenda uma manutenção e bloqueia o veículo para o período (RN03). */
    public Manutencao registrarManutencao(String placa, String motivo, TipoManutencao tipo,
                                          PrioridadeManutencao prioridade, int kmReferencia,
                                          LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (!veiculos.existe(placa)) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        if (motivo == null || motivo.isBlank()) {
            throw new RegraNegocioException("O motivo da manutenção é obrigatório.");
        }
        if (dataInicio != null && dataFim != null && !dataFim.isAfter(dataInicio)) {
            throw new RegraNegocioException("A data de fim deve ser posterior ao início.");
        }
        Manutencao m = new Manutencao(proximoId(), placa.toUpperCase(), motivo, tipo,
                prioridade, kmReferencia, dataInicio, dataFim);
        registrar(m);
        veiculos.atualizarStatus(placa, StatusVeiculo.MANUTENCAO);
        return m;
    }

    public Manutencao agendarBloqueio(String placa, String motivo, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return registrarManutencao(placa, motivo, TipoManutencao.PREVENTIVA,
                PrioridadeManutencao.BAIXA, veiculos.getQuilometragem(placa), dataInicio, dataFim);
    }

    /** Conclui uma manutenção (libera o veículo para DISPONIVEL). */
    public Manutencao concluir(long id) {
        Manutencao m = buscarPorId(id);
        if (m.getStatus() == StatusManutencao.CONCLUIDA || m.getStatus() == StatusManutencao.CANCELADA) {
            throw new RegraNegocioException("A manutenção " + id + " já está finalizada.");
        }
        m.setStatus(StatusManutencao.CONCLUIDA);
        filaEspera.remover(m);
        filaPrioridade.remover(m);
        veiculos.atualizarStatus(m.getPlaca(), StatusVeiculo.DISPONIVEL);
        return m;
    }

    public Manutencao cancelar(long id) {
        Manutencao m = buscarPorId(id);
        if (m.getStatus() == StatusManutencao.CONCLUIDA) {
            throw new RegraNegocioException("Manutenção concluída não pode ser cancelada.");
        }
        m.setStatus(StatusManutencao.CANCELADA);
        filaEspera.remover(m);
        filaPrioridade.remover(m);
        // libera o veículo apenas se não houver outra manutenção ativa
        if (!existeOutraAtiva(m)) {
            veiculos.atualizarStatus(m.getPlaca(), StatusVeiculo.DISPONIVEL);
        }
        return m;
    }

    // ---- US08: identificação por quilometragem ----

    /**
     * Varre a frota e enfileira (na fila de prioridade) os veículos cujo
     * quilometragem atingiu ou ultrapassou o limite e que não estejam já em
     * manutenção. Não altera o status do veículo (identificação/passivo).
     */
    public Lista<Manutencao> identificarNecessidades(VeiculoService frota, int limiteKm) {
        Lista<Manutencao> identificados = new Lista<>();
        for (Veiculo v : frota.listar()) {
            if (v.getStatus() == StatusVeiculo.MANUTENCAO) continue;
            int km = (int) Math.round(v.getQuilometragemAtual());
            if (km >= limiteKm) {
                PrioridadeManutencao p = PrioridadeManutencao.aPartirDeKm(km, limiteKm);
                Manutencao m = new Manutencao(proximoId(), v.getPlaca(),
                        "Limite de quilometragem atingido (" + km + " km).",
                        TipoManutencao.PREVENTIVA, p, km, LocalDateTime.now(), null);
                registrarSilencioso(m);
                identificados.adicionar(m);
            }
        }
        return identificados;
    }

    // ---- Consultas ----

    public Manutencao buscarPorId(long id) {
        for (Manutencao m : todos) {
            if (m.getId() == id) return m;
        }
        throw new RegraNegocioException("Manutenção não encontrada: " + id);
    }

    public Lista<Manutencao> listarAtivas() {
        Lista<Manutencao> ativas = new Lista<>();
        for (Manutencao m : todos) {
            if (m.estaAtiva()) ativas.adicionar(m);
        }
        return ativas;
    }

    public Lista<Manutencao> listarPorPlaca(String placa) {
        Lista<Manutencao> resultado = new Lista<>();
        if (placa == null) return resultado;
        String alvo = placa.toUpperCase();
        for (Manutencao m : todos) {
            if (m.getPlaca().equalsIgnoreCase(alvo)) resultado.adicionar(m);
        }
        return resultado;
    }

    public Manutencao proximoDaFila() {
        return filaPrioridade.consultarFrente();
    }

    public Manutencao atenderProximo() {
        Manutencao m = filaPrioridade.desenfileirar();
        if (m == null) return null;
        filaEspera.remover(m);
        m.setStatus(StatusManutencao.EM_ANDAMENTO);
        veiculos.atualizarStatus(m.getPlaca(), StatusVeiculo.MANUTENCAO);
        return m;
    }

    // ---- Interno ----

    private void registrar(Manutencao m) {
        todos.adicionar(m);
        filaEspera.enfileirar(m);
        filaPrioridade.enfileirar(m, m.getPrioridade().getNivel());
    }

    private void registrarSilencioso(Manutencao m) {
        todos.adicionar(m);
        filaPrioridade.enfileirar(m, m.getPrioridade().getNivel());
    }

    private long proximoId() {
        return proximoId[0]++;
    }

    private boolean existeOutraAtiva(Manutencao exceto) {
        for (Manutencao m : todos) {
            if (m != exceto && m.estaAtiva()) return true;
        }
        return false;
    }
}
