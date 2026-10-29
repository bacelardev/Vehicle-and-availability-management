package br.edu.ifba.controleveiculos.service;

import java.time.LocalDateTime;

/**
 * PONTO DE INTEGRAÇÃO com o módulo de manutenção.
 * <p>
 * Implementado pelo {@link ManutencaoService}. Permite ao módulo de aluguéis
 * verificar a necessidade de manutenção e encaminhar um veículo para a fila de
 * manutenção na devolução (contribuição de Mateus Almeida — US05/US06/US08).
 */
public interface ManutencaoPort {

    /** O veículo precisa de manutenção com essa quilometragem? */
    boolean necessitaManutencao(String placa, int kmAtual);

    /** Coloca o veículo na fila de manutenção. */
    void encaminharParaManutencao(String placa, String motivo);

    /** Há manutenção ativa que se sobreponha ao período informado? (RN03) */
    boolean temManutencaoAtivaNoPeriodo(String placa, LocalDateTime inicio, LocalDateTime fim);
}
