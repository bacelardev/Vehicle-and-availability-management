package br.edu.ifba.controleveiculos.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa um período em que o veículo fica indisponível para manutenção
 * (contribuição de Rodolfo — US07/US08).
 */
public class Manutencao {

    private final long id;
    private final String placa;
    private final String motivo;
    private final TipoManutencao tipo;
    private final PrioridadeManutencao prioridade;
    private final LocalDateTime dataCadastro;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusManutencao status;
    private int kmReferencia;

    public Manutencao(long id, String placa, String motivo, TipoManutencao tipo,
                      PrioridadeManutencao prioridade, int kmReferencia,
                      LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.id = id;
        this.placa = Objects.requireNonNull(placa, "Placa é obrigatória.").toUpperCase();
        this.motivo = Objects.requireNonNull(motivo, "Motivo é obrigatório.");
        this.tipo = Objects.requireNonNull(tipo, "Tipo é obrigatório.");
        this.prioridade = Objects.requireNonNull(prioridade, "Prioridade é obrigatória.");
        this.kmReferencia = kmReferencia;
        this.dataCadastro = LocalDateTime.now();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = StatusManutencao.AGENDADA;
    }

    public long getId() { return id; }
    public String getPlaca() { return placa; }
    public String getMotivo() { return motivo; }
    public TipoManutencao getTipo() { return tipo; }
    public PrioridadeManutencao getPrioridade() { return prioridade; }
    public int getKmReferencia() { return kmReferencia; }
    public void setKmReferencia(int kmReferencia) { this.kmReferencia = kmReferencia; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public StatusManutencao getStatus() { return status; }
    public void setStatus(StatusManutencao status) { this.status = Objects.requireNonNull(status); }

    public boolean estaAtiva() {
        return status == StatusManutencao.AGENDADA || status == StatusManutencao.EM_ANDAMENTO;
    }

    /** True se este período de manutenção se sobrepõe ao intervalo informado. */
    public boolean sobreponAoIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        if (dataInicio == null || dataFim == null) return false;
        return dataInicio.isBefore(fim) && inicio.isBefore(dataFim);
    }

    @Override
    public String toString() {
        return "Manutencao{id=" + id +
                ", placa=" + placa +
                ", motivo=" + motivo +
                ", tipo=" + tipo +
                ", prioridade=" + prioridade +
                ", status=" + status +
                ", km=" + kmReferencia +
                ", inicio=" + dataInicio +
                ", fim=" + dataFim + "}";
    }
}
