package br.edu.ifba.controleveiculos.structures;

import br.edu.ifba.controleveiculos.model.Usuario;

/**
 * Lista com capacidade fixa para gerenciar usuários em memória.
 * <p>
 * Estrutura acadêmica (baseada em vetor) que respalda a disciplina. A capacidade
 * é definida na criação do {@link br.edu.ifba.controleveiculos.service.ServicoUsuario}.
 */
public class ListaUsuarios {

    private final Usuario[] itens;
    private int quantidade;

    public ListaUsuarios(int capacidade) {
        if (capacidade <= 0) throw new IllegalArgumentException("A capacidade deve ser positiva.");
        this.itens = new Usuario[capacidade];
        this.quantidade = 0;
    }

    public boolean estaCheia() {
        return quantidade == itens.length;
    }

    public boolean estaVazia() {
        return quantidade == 0;
    }

    public int quantidade() {
        return quantidade;
    }

    public void anexar(Usuario usuario) {
        if (estaCheia()) {
            throw new IllegalStateException("Lista de usuários cheia.");
        }
        itens[quantidade] = usuario;
        quantidade++;
    }

    public Usuario selecionar(int posicao) {
        if (posicao < 0 || posicao >= quantidade) {
            throw new IndexOutOfBoundsException("Posição inválida.");
        }
        return itens[posicao];
    }

    public Usuario buscarPorId(int id) {
        for (int i = 0; i < quantidade; i++) {
            if (itens[i].getId() == id) {
                return itens[i];
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null) return null;
        for (int i = 0; i < quantidade; i++) {
            if (itens[i].getEmail().equalsIgnoreCase(email)) {
                return itens[i];
            }
        }
        return null;
    }
}
