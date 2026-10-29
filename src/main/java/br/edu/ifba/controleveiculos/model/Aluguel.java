package br.edu.ifba.controleveiculos.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade Aluguel (contribuição de Mateus Almeida — US05/US06).
 * <p>
 * Preserva a lógica original: km percorridos = km devolução - km retirada (RN04),
 * cálculo de atraso e encaminhamento para manutenção.
 */
public class Aluguel {

    private final long id;
    private final String placa;
    private final String clienteId;
    private final Long reservaId;                 // null quando não há reserva
    private final LocalDateTime dataRetirada;
    private final int kmRetirada;
    private final LocalDateTime dataPrevistaDevolucao;

    private LocalDateTime dataDevolucao;
    private Integer kmDevolucao;
    private String observacoesDevolucao;
    private StatusAluguel status;

    public Aluguel(long id, String placa, String clienteId, Long reservaId,
                   LocalDateTime dataRetirada, int kmRetirada, LocalDateTime dataPrevistaDevolucao) {
        this.id = id;
        this.placa = Objects.requireNonNull(placa, "Placa é obrigatória.").toUpperCase();
        this.clienteId = Objects.requireNonNull(clienteId, "Cliente é obrigatório.");
        this.reservaId = reservaId;
        this.dataRetirada = Objects.requireNonNull(dataRetirada, "Data retirada é obrigatória.");
        this.kmRetirada = kmRetirada;
        this.dataPrevistaDevolucao = Objects.requireNonNull(dataPrevistaDevolucao, "Data prevista é obrigatória.");
        this.status = StatusAluguel.EM_ANDAMENTO;
    }

    /** Regra RN04: km percorridos = km devolução - km retirada. */
    public int calcularKmPercorridos() {
        exigirDevolvido();
        return kmDevolucao - kmRetirada;
    }

    /** Atraso em relação à data prevista (Duration.ZERO se entregue no prazo). */
    public Duration calcularAtraso() {
        exigirDevolvido();
        if (dataDevolucao.isAfter(dataPrevistaDevolucao)) {
            return Duration.between(dataPrevistaDevolucao, dataDevolucao);
        }
        return Duration.ZERO;
    }

    public boolean houveAtraso() {
        return !calcularAtraso().isZero();
    }

    public void finalizar(LocalDateTime dataDevolucao, int kmDevolucao, String observacoes) {
        this.dataDevolucao = Objects.requireNonNull(dataDevolucao, "Data devolução é obrigatória.");
        this.kmDevolucao = kmDevolucao;
        this.observacoesDevolucao = observacoes;
        this.status = StatusAluguel.FINALIZADO;
    }

    public boolean estaEmAndamento() {
        return status == StatusAluguel.EM_ANDAMENTO;
    }

    private void exigirDevolvido() {
        if (kmDevolucao == null || dataDevolucao == null) {
            throw new IllegalStateException("Aluguel ainda não foi devolvido.");
        }
    }

    public long getId() { return id; }
    public String getPlaca() { return placa; }
    public String getClienteId() { return clienteId; }
    public Long getReservaId() { return reservaId; }
    public LocalDateTime getDataRetirada() { return dataRetirada; }
    public int getKmRetirada() { return kmRetirada; }
    public LocalDateTime getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public LocalDateTime getDataDevolucao() { return dataDevolucao; }
    public Integer getKmDevolucao() { return kmDevolucao; }
    public String getObservacoesDevolucao() { return observacoesDevolucao; }
    public StatusAluguel getStatus() { return status; }

    @Override
    public String toString() {
        return "Aluguel{id=" + id + ", placa=" + placa + ", cliente=" + clienteId
                + ", reserva=" + reservaId + ", retirada=" + dataRetirada + ", kmRetirada=" + kmRetirada
                + ", previstaDevolucao=" + dataPrevistaDevolucao
                + ", devolucao=" + dataDevolucao + ", kmDevolucao=" + kmDevolucao
                + ", status=" + status + "}";
    }
}
