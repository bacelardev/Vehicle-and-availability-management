package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Aluguel;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório em memória de aluguéis.
 * <p>
 * Armazenamento feito com a estrutura acadêmica {@link Lista}
 * (contribuição de Mateus Almeida — US05/US06).
 */
public class AluguelRepository {

    private final Lista<Aluguel> alugueis = new Lista<>();
    private long proximoId = 1;

    public long gerarId() {
        return proximoId++;
    }

    public void salvar(Aluguel aluguel) {
        alugueis.adicionar(aluguel);
    }

    public Optional<Aluguel> buscarPorId(long id) {
        for (Aluguel a : alugueis) {
            if (a.getId() == id) return Optional.of(a);
        }
        return Optional.empty();
    }

    public Optional<Aluguel> buscarEmAndamentoPorPlaca(String placa) {
        if (placa == null) return Optional.empty();
        String alvo = placa.toUpperCase();
        for (Aluguel a : alugueis) {
            if (a.estaEmAndamento() && a.getPlaca().equalsIgnoreCase(alvo)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    public List<Aluguel> listarTodos() {
        List<Aluguel> copia = new ArrayList<>();
        for (Aluguel a : alugueis) copia.add(a);
        return copia;
    }
}
