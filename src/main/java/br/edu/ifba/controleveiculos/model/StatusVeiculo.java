package br.edu.ifba.controleveiculos.model;

/**
 * Status do veículo (RN05).
 * <p>
 * Unificado como enum para todo o sistema, substituindo o uso de {@code String}
 * que gerava inconsistências. O módulo de aluguéis (contribuição individual de
 * Mateus Almeida) já utilizava um enum equivalente; foi consolidado aqui.
 */
public enum StatusVeiculo {
    DISPONIVEL,
    RESERVADO,
    ALUGADO,
    MANUTENCAO
}
