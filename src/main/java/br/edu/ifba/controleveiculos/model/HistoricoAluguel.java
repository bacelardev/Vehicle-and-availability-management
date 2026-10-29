package br.edu.ifba.controleveiculos.model;

import br.edu.ifba.controleveiculos.structures.Lista;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Histórico de aluguéis finalizados.
 * <p>
 * Armazenamento interno feito com a estrutura acadêmica {@link Lista}
 * (contribuição de Mateus Almeida — US06). Consultas retornam visões em
 * {@link List} para consumo da UI e dos testes.
 */
public class HistoricoAluguel {

    private final Lista<Aluguel> registros = new Lista<>();

    public void registrar(Aluguel aluguel) {
        registros.adicionar(aluguel);
    }

    public List<Aluguel> listarTodos() {
        return new ArrayList<>(Arrays.asList(registros.toArray()));
    }

    public List<Aluguel> listarMaisRecentesPrimeiro() {
        Aluguel[] arr = registros.toArray();
        Arrays.sort(arr, 0, registros.tamanho(), Comparator.comparing(Aluguel::getDataDevolucao).reversed());
        return new ArrayList<>(Arrays.asList(arr));
    }

    public List<Aluguel> consultarPorPlaca(String placa) {
        List<Aluguel> resultado = new ArrayList<>();
        for (Aluguel a : registros) {
            if (a.getPlaca().equalsIgnoreCase(placa)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public List<Aluguel> consultarPorCliente(String clienteId) {
        List<Aluguel> resultado = new ArrayList<>();
        for (Aluguel a : registros) {
            if (a.getClienteId().equals(clienteId)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    /** Soma dos km percorridos por um veículo em todos os aluguéis finalizados. */
    public int totalKmPercorridos(String placa) {
        int total = 0;
        for (Aluguel a : consultarPorPlaca(placa)) {
            total += a.calcularKmPercorridos();
        }
        return total;
    }

    public boolean estaVazio() {
        return registros.vazia();
    }
}
