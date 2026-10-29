package br.edu.ifba.controleveiculos.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Visão mínima de uma reserva (contribuição de Mateus Almeida) para o módulo de aluguel.
 */
public class ReservaInfo {

    private final long id;
    private final String clienteId;
    private final String placa;
    private final LocalDateTime inicio;
    private final LocalDateTime fim;

    public ReservaInfo(long id, String clienteId, String placa, LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.clienteId = Objects.requireNonNull(clienteId);
        this.placa = Objects.requireNonNull(placa).toUpperCase();
        this.inicio = Objects.requireNonNull(inicio);
        this.fim = Objects.requireNonNull(fim);
    }

    public long getId() { return id; }
    public String getClienteId() { return clienteId; }
    public String getPlaca() { return placa; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFim() { return fim; }
}
