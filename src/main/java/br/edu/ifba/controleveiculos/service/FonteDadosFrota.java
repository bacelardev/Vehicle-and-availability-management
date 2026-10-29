package br.edu.ifba.controleveiculos.service;

/**
 * Fonte de dados do dashboard.
 * <p>
 * (contribuição de Henrique Barbosa Ferrão — US10)
 */
public interface FonteDadosFrota {

    int totalVeiculos();

    int veiculosDisponiveis();

    int veiculosReservados();

    int veiculosEmManutencao();

    default int veiculosAlugados() {
        return 0;
    }

    int reservasRealizadas();
}
