package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Categoria;
import br.edu.ifba.controleveiculos.model.StatusVeiculo;
import br.edu.ifba.controleveiculos.model.Veiculo;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.util.Comparator;

/**
 * Gestão da frota de veículos (Mateus Oliveira de Araujo — US01/US02).
 * <p>
 * Consolida a lógica de cadastro e consulta que estava no antigo
 * {@code GerenciadorFrota} dentro de {@code ManutencaoService}, corrigindo a
 * responsabilidade da classe para refletir seu nome.
 * <p>
 * Regra RN01: não podem existir dois veículos com a mesma placa.
 * Estrutura acadêmica {@link Lista} usada para armazenar os veículos.
 */
public class VeiculoService implements VeiculoPort {

    private final Lista<Veiculo> veiculos = new Lista<>();
    private long proximoId = 1;

    // ---- VeiculoPort (integração com aluguéis/manutenção) ----

    @Override
    public boolean existe(String placa) {
        return buscarPorPlaca(placa) != null;
    }

    @Override
    public StatusVeiculo getStatus(String placa) {
        Veiculo v = buscarPorPlaca(placa);
        if (v == null) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        return v.getStatus();
    }

    @Override
    public int getQuilometragem(String placa) {
        Veiculo v = buscarPorPlaca(placa);
        if (v == null) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        return (int) Math.round(v.getQuilometragemAtual());
    }

    @Override
    public void atualizarStatus(String placa, StatusVeiculo novoStatus) {
        Veiculo v = buscarPorPlaca(placa);
        if (v == null) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        v.setStatus(novoStatus);
    }

    @Override
    public void atualizarQuilometragem(String placa, int novaQuilometragem) {
        Veiculo v = buscarPorPlaca(placa);
        if (v == null) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        if (novaQuilometragem < 0) {
            throw new RegraNegocioException("Quilometragem não pode ser negativa.");
        }
        v.setQuilometragemAtual(novaQuilometragem);
    }

    // ---- RN01: cadastro com checagem de placa única ----

    public Veiculo cadastrar(String placa, String marca, String modelo, int ano,
                             double valorDiaria, Categoria categoria, int capacidadePassageiros,
                             double quilometragemAtual) {
        if (placa == null || placa.isBlank()) {
            throw new RegraNegocioException("A placa é obrigatória.");
        }
        if (categoria == null) {
            throw new RegraNegocioException("A categoria é obrigatória.");
        }
        if (capacidadePassageiros <= 0) {
            throw new RegraNegocioException("A capacidade de passageiros deve ser positiva (RN06).");
        }
        if (valorDiaria < 0) {
            throw new RegraNegocioException("O valor da diária não pode ser negativo.");
        }
        if (quilometragemAtual < 0) {
            throw new RegraNegocioException("A quilometragem não pode ser negativa.");
        }
        // RN01: placa única
        if (existe(placa)) {
            throw new RegraNegocioException("Placa já cadastrada: " + placa.toUpperCase());
        }
        Veiculo v = new Veiculo((int) proximoId++, placa.toUpperCase(), marca, modelo, ano,
                valorDiaria, categoria, capacidadePassageiros, quilometragemAtual);
        veiculos.adicionar(v);
        return v;
    }

    // ---- Consultas (US02) ----

    public Veiculo buscarPorPlaca(String placa) {
        if (placa == null) return null;
        String alvo = placa.toUpperCase();
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(alvo)) return v;
        }
        return null;
    }

    public Lista<Veiculo> listar() {
        return veiculos;
    }

    public Lista<Veiculo> consultarPorModelo(String modelo) {
        Lista<Veiculo> resultado = new Lista<>();
        if (modelo == null) return resultado;
        String alvo = modelo.toLowerCase();
        for (Veiculo v : veiculos) {
            if (v.getModelo().toLowerCase().contains(alvo)) resultado.adicionar(v);
        }
        return resultado;
    }

    public Lista<Veiculo> consultarPorCategoria(String nomeCategoria) {
        Lista<Veiculo> resultado = new Lista<>();
        if (nomeCategoria == null) return resultado;
        String alvo = nomeCategoria.toLowerCase();
        for (Veiculo v : veiculos) {
            if (v.getCategoria().getNome().equalsIgnoreCase(alvo)) resultado.adicionar(v);
        }
        return resultado;
    }

    public Lista<Veiculo> consultarDisponiveis() {
        Lista<Veiculo> resultado = new Lista<>();
        for (Veiculo v : veiculos) {
            if (v.estaDisponivel()) resultado.adicionar(v);
        }
        return resultado;
    }

    /** Ordena a frota com o comparador informado (US02). */
    public void ordenar(Comparator<Veiculo> comparador) {
        veiculos.ordenar(comparador);
    }

    // ---- Exclusão (US02): só permitida quando o veículo não está em uso ----

    public boolean removerPorPlaca(String placa) {
        Veiculo v = buscarPorPlaca(placa);
        if (v == null) {
            throw new RegraNegocioException("Veículo não encontrado: " + placa);
        }
        if (v.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new RegraNegocioException(
                    "Não é possível remover o veículo " + placa + ": status " + v.getStatus());
        }
        veiculos.remover(v);
        return true;
    }

    public int totalVeiculos() {
        return veiculos.tamanho();
    }
}
