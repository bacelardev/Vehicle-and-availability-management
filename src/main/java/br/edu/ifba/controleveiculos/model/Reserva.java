package br.edu.ifba.controleveiculos.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa a solicitação de um veículo para determinado período
 * (contribuição de Guilherme — disponibilidade/reservas).
 * <p>
 * A referência ao veículo se dá pela placa e ao cliente pelo CPF, mantendo
 * consistência com {@link Aluguel}.
 */
public class Reserva {

    private final long id;
    private final String placa;
    private final String clienteId;     // CPF do cliente
    private final LocalDateTime dataInicio;
    private final LocalDateTime dataFim;
    private final LocalDateTime dataCriacao;
    private StatusReserva status;

    public Reserva(long id, String placa, String clienteId,
                   LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.id = id;
        this.placa = Objects.requireNonNull(placa, "Placa é obrigatória.").toUpperCase();
        this.clienteId = Objects.requireNonNull(clienteId, "Cliente é obrigatório.");
        this.dataInicio = Objects.requireNonNull(dataInicio, "Data início é obrigatória.");
        this.dataFim = Objects.requireNonNull(dataFim, "Data fim é obrigatória.");
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusReserva.ATIVA;
    }

    public long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getClienteId() {
        return clienteId;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = Objects.requireNonNull(status, "Status da reserva é obrigatório.");
    }

    /** True se este período se sobrepõe ao período informado (RN02). */
    public boolean sobreponAoIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        return dataInicio.isBefore(fim) && inicio.isBefore(dataFim);
    }

    /** True se esta reserva ainda está vigente (não cancelada/utilizada) no momento. */
    public boolean estaAtiva() {
        return status == StatusReserva.ATIVA;
    }

    @Override
    public String toString() {
        return "Reserva{id=" + id +
                ", placa=" + placa +
                ", cliente=" + clienteId +
                ", de=" + dataInicio +
                ", até=" + dataFim +
                ", status=" + status + "}";
    }
}
