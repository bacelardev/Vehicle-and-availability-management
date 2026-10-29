package br.edu.ifba.controleveiculos.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Fila de prioridade genérica.
 * <p>
 * O atendimento considera o nível de prioridade: o elemento com a maior
 * prioridade é atendido primeiro. Em caso de empate, respeita o FIFO de
 * inserção (conforme documentação em {@code docs/estrutura-dados}).
 *
 * @param <T> tipo dos elementos
 */
public class FilaPrioridade<T> implements Iterable<T> {

    private static class No<T> {
        final T dado;
        final int prioridade;
        final long ordem;
        No<T> proximo;

        No(T dado, int prioridade, long ordem) {
            this.dado = dado;
            this.prioridade = prioridade;
            this.ordem = ordem;
        }
    }

    private No<T> inicio; // cabeça = maior prioridade (FIFO no empate)
    private int tamanho;
    private long contadorOrdem;

    public FilaPrioridade() {
    }

    /**
     * Enfileira considerando a prioridade (quanto maior, mais urgente).
     * Em empate de prioridade, o elemento inserido primeiro é atendido antes.
     */
    public void enfileirar(T dado, int prioridade) {
        Objects.requireNonNull(dado, "Elemento é obrigatório.");
        No<T> novo = new No<>(dado, prioridade, contadorOrdem++);
        if (inicio == null
                || novo.prioridade > inicio.prioridade
                || (novo.prioridade == inicio.prioridade && novo.ordem < inicio.ordem)) {
            novo.proximo = inicio;
            inicio = novo;
            tamanho++;
            return;
        }
        No<T> atual = inicio;
        while (atual.proximo != null
                && novo.prioridade < atual.proximo.prioridade) {
            atual = atual.proximo;
        }
        // empatado: avança ao final do grupo de mesma prioridade (FIFO)
        while (atual.proximo != null
                && atual.proximo.prioridade == novo.prioridade
                && novo.ordem > atual.proximo.ordem) {
            atual = atual.proximo;
        }
        novo.proximo = atual.proximo;
        atual.proximo = novo;
        tamanho++;
    }

    /** Desenfileira o elemento de maior prioridade (FIFO no empate). */
    public T desenfileirar() {
        if (estaVazia()) return null;
        T dado = inicio.dado;
        inicio = inicio.proximo;
        tamanho--;
        return dado;
    }

    /** Consulta o elemento de maior prioridade sem removê-lo. */
    public T consultarFrente() {
        return estaVazia() ? null : inicio.dado;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    /** Remove a primeira ocorrência igual (por referência ==) ao elemento. */
    public boolean remover(T elemento) {
        if (inicio == null) return false;
        if (inicio.dado == elemento) {
            inicio = inicio.proximo;
            tamanho--;
            return true;
        }
        No<T> atual = inicio;
        while (atual.proximo != null) {
            if (atual.proximo.dado == elemento) {
                atual.proximo = atual.proximo.proximo;
                tamanho--;
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private No<T> atual = inicio;

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public T next() {
                if (atual == null) throw new NoSuchElementException();
                T dado = atual.dado;
                atual = atual.proximo;
                return dado;
            }
        };
    }
}
