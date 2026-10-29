package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Categoria;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.util.Comparator;

/**
 * Gestão de categorias de veículos.
 * <p>
 * As categorias são referenciadas pelos veículos da frota; esta service mantém
 * o registro em memória usando a estrutura acadêmica {@link Lista}.
 */
public class CategoriaService {

    private final Lista<Categoria> categorias = new Lista<>();
    private long proximoId = 1;

    public Categoria cadastrar(String nome, String descricao) {
        if (nome == null || nome.isBlank()) {
            throw new RegraNegocioException("O nome da categoria é obrigatório.");
        }
        Categoria categoria = new Categoria((int) proximoId++, nome.trim(),
                descricao == null ? "" : descricao.trim());
        categorias.adicionar(categoria);
        return categoria;
    }

    public Categoria buscarPorId(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public Categoria buscarPorNome(String nome) {
        if (nome == null) return null;
        for (Categoria c : categorias) {
            if (c.getNome().equalsIgnoreCase(nome.trim())) return c;
        }
        return null;
    }

    public void listar() {
        if (categorias.vazia()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }
        for (Categoria c : categorias) {
            System.out.println(c);
        }
    }

    public boolean existeAlguma() {
        return !categorias.vazia();
    }
}
