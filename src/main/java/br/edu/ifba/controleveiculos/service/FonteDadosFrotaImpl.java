package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Reserva;
import br.edu.ifba.controleveiculos.model.StatusReserva;
import br.edu.ifba.controleveiculos.model.StatusVeiculo;
import br.edu.ifba.controleveiculos.model.Veiculo;

/**
 * Implementação real da {@link FonteDadosFrota}, conectada aos serviços do
 * projeto (Henrique Barbosa Ferrão — US10).
 * <p>
 * Substitui os números fictícios por contagens derivadas da frota real,
 * disponibilizando ao {@link ServicoDashboard} os indicadores do dashboard.
 */
public class FonteDadosFrotaImpl implements FonteDadosFrota {

    private final VeiculoService veiculos;
    private final ReservaService reservas;

    public FonteDadosFrotaImpl(VeiculoService veiculos, ReservaService reservas) {
        this.veiculos = veiculos;
        this.reservas = reservas;
    }

    @Override
    public int totalVeiculos() {
        return veiculos.totalVeiculos();
    }

    @Override
    public int veiculosDisponiveis() {
        int c = 0;
        for (Veiculo v : veiculos.listar()) {
            if (v.getStatus() == StatusVeiculo.DISPONIVEL) c++;
        }
        return c;
    }

    @Override
    public int veiculosReservados() {
        int c = 0;
        for (Veiculo v : veiculos.listar()) {
            if (v.getStatus() == StatusVeiculo.RESERVADO) c++;
        }
        return c;
    }

    @Override
    public int veiculosEmManutencao() {
        int c = 0;
        for (Veiculo v : veiculos.listar()) {
            if (v.getStatus() == StatusVeiculo.MANUTENCAO) c++;
        }
        return c;
    }

    @Override
    public int veiculosAlugados() {
        int c = 0;
        for (Veiculo v : veiculos.listar()) {
            if (v.getStatus() == StatusVeiculo.ALUGADO) c++;
        }
        return c;
    }

    @Override
    public int reservasRealizadas() {
        int c = 0;
        for (Reserva r : reservas.listar()) {
            if (r.getStatus() == StatusReserva.ATIVA || r.getStatus() == StatusReserva.UTILIZADA) c++;
        }
        return c;
    }
}
