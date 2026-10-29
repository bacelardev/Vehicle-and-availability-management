package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.StatusVeiculo;
import br.edu.ifba.controleveiculos.model.Veiculo;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.time.LocalDateTime;

/**
 * Consulta de disponibilidade de veículos por período (Guilherme — US03).
 * <p>
 * Um veículo está disponível em um período quando:
 * <ul>
 *   <li>existe na frota;</li>
 *   <li>não está ALUGADO ou em MANUTENCAO (RN03);</li>
 *   <li>não possui manutenção ativa sobreposta ao período (RN03);</li>
 *   <li>não possui reserva ativa sobreposta ao período (RN02).</li>
 * </ul>
 */
public class DisponibilidadeService {

    private final VeiculoService veiculos;
    private final ReservaPort reservas;
    private final ManutencaoPort manutencao;

    public DisponibilidadeService(VeiculoService veiculos, ReservaPort reservas, ManutencaoPort manutencao) {
        this.veiculos = veiculos;
        this.reservas = reservas;
        this.manutencao = manutencao;
    }

    public boolean estaDisponivel(String placa, LocalDateTime inicio, LocalDateTime fim) {
        if (!veiculos.existe(placa)) return false;
        StatusVeiculo status = veiculos.getStatus(placa);
        if (status == StatusVeiculo.ALUGADO || status == StatusVeiculo.MANUTENCAO) return false;
        if (manutencao.temManutencaoAtivaNoPeriodo(placa, inicio, fim)) return false;
        if (reservas.existeReservaAtivaNoPeriodo(placa, inicio, fim)) return false;
        return true;
    }

    /** Lista todos os veículos disponíveis no período informado. */
    public Lista<Veiculo> listarDisponiveis(LocalDateTime inicio, LocalDateTime fim) {
        Lista<Veiculo> resultado = new Lista<>();
        for (Veiculo v : veiculos.listar()) {
            if (estaDisponivel(v.getPlaca(), inicio, fim)) {
                resultado.adicionar(v);
            }
        }
        return resultado;
    }
}
