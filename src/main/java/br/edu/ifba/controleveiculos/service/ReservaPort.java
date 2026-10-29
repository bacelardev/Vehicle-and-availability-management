package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.ReservaInfo;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * PONTO DE INTEGRAÇÃO com o módulo de reservas.
 * <p>
 * Implementado pelo {@link ReservaService}. Permite ao módulo de aluguéis
 * localizar uma reserva ativa e marcar uma reserva como utilizada
 * (contribuição de Mateus Almeida — US05/US06), bem como ao módulo de
 * disponibilidade verificar sobreposição de reservas (RN02).
 */
public interface ReservaPort {

    /** Reserva ativa do cliente para o veículo, se existir. */
    Optional<ReservaInfo> buscarReservaAtiva(String placa, String clienteId);

    /** Avisa o módulo de reservas que a reserva virou um aluguel. */
    void marcarComoUtilizada(long reservaId);

    /** Há alguma reserva ativa que se sobreponha ao período informado? (RN02) */
    boolean existeReservaAtivaNoPeriodo(String placa, LocalDateTime inicio, LocalDateTime fim);
}
