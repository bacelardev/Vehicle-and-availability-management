package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.StatusVeiculo;

/**
 * PONTO DE INTEGRAÇÃO com o módulo de veículos.
 * <p>
 * Implementado pelo {@link VeiculoService}. Permite que os módulos de aluguéis
 * e manutenção consultem e atualizem o estado do veículo sem acoplar-se à sua
 * estrutura interna (contribuição de Mateus Almeida — US05/US06).
 */
public interface VeiculoPort {

    boolean existe(String placa);

    StatusVeiculo getStatus(String placa);

    int getQuilometragem(String placa);

    void atualizarStatus(String placa, StatusVeiculo novoStatus);

    void atualizarQuilometragem(String placa, int novaQuilometragem);
}
