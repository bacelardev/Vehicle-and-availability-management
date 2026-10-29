package br.edu.ifba.controleveiculos.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Fila simplesmente encadeada genérica (FIFO).
 * <p>
 * Aplicada no sistema para a fila de veículos aguardando manutenção, onde o
 * primeiro a entrar é o primeiro a ser atendido (conforme documentação em
 * {@code docs/estrutura-dados}).
 *
 * @param <T> tipo dos elementos
 */
public class Fila<T> implements Iterable<T> {

    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
        }
    }

    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    public Fila() {
    }

    /** Enfileira um elemento no fim da fila. */
    public void enfileirar(T dado) {
        No<T> novo = new No<>(dado);
        if (estaVazia()) {
            inicio = fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }
        tamanho++;
    }

    /** Desenfileira e retorna o elemento do início, ou null se vazia. */
    public T desenfileirar() {
        if (estaVazia()) return null;
        T dado = inicio.dado;
        inicio = inicio.proximo;
        if (inicio == null) fim = null;
        tamanho--;
        return dado;
    }

    /** Consulta o elemento do início sem removê-lo, ou null se vazia. */
    public T consultarFrente() {
        return estaVazia() ? null : inicio.dado;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    /** Remove a primeira ocorrência igual ao elemento informado, se existir. */
    public boolean remover(T elemento) {
        if (estaVazia()) return false;
        if (inicio.dado == elemento) {
            desenfileirar();
            return true;
        }
        No<T> anterior = inicio;
        while (anterior.proximo != null) {
            if (anterior.proximo.dado == elemento) {
                anterior.proximo = anterior.proximo.proximo;
                if (anterior.proximo == null) fim = anterior;
                tamanho--;
                return true;
            }
            anterior = anterior.proximo;
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
