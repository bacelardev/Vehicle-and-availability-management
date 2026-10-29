package br.edu.ifba.controleveiculos.structures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Lista simplesmente encadeada genérica.
 * <p>
 * Estrutura acadêmica utilizada pelo sistema para armazenar a frota de veículos,
 * as reservas e os aluguéis (conforme documentação em {@code docs/estrutura-dados}).
 */
public class Lista<T> implements Iterable<T> {

    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
        }
    }

    private No<T> inicio;
    private int tamanho;

    public Lista() {
    }

    /** Insere o elemento ao final da lista. */
    public void adicionar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (inicio == null) {
            inicio = novo;
        } else {
            No<T> atual = inicio;
            while (atual.proximo != null) atual = atual.proximo;
            atual.proximo = novo;
        }
        tamanho++;
    }

    /** Insere o elemento na posição informada (0-based). */
    public void inserir(int indice, T elemento) {
        checarPosicaoInsercao(indice);
        if (indice == tamanho) {
            adicionar(elemento);
            return;
        }
        No<T> novo = new No<>(elemento);
        if (indice == 0) {
            novo.proximo = inicio;
            inicio = novo;
        } else {
            No<T> anterior = inicio;
            for (int i = 0; i < indice - 1; i++) anterior = anterior.proximo;
            novo.proximo = anterior.proximo;
            anterior.proximo = novo;
        }
        tamanho++;
    }

    private void checarPosicaoInsercao(int indice) {
        if (indice < 0 || indice > tamanho) {
            throw new IndexOutOfBoundsException("Índice " + indice + " fora do intervalo (0.." + tamanho + ").");
        }
    }

    /** Retorna o elemento na posição informada (0-based). */
    public T obter(int indice) {
        checarElemento(indice);
        No<T> atual = inicio;
        for (int i = 0; i < indice; i++) atual = atual.proximo;
        return atual.dado;
    }

    private void checarElemento(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice " + indice + " fora do intervalo (0.." + (tamanho - 1) + ").");
        }
    }

    /** Substitui o elemento na posição informada. */
    public void substituir(int indice, T elemento) {
        checarElemento(indice);
        No<T> atual = inicio;
        for (int i = 0; i < indice; i++) atual = atual.proximo;
        atual.dado = elemento;
    }

    /** Remove o elemento da posição informada e o retorna. */
    public T remover(int indice) {
        checarElemento(indice);
        T removido;
        if (indice == 0) {
            removido = inicio.dado;
            inicio = inicio.proximo;
        } else {
            No<T> anterior = inicio;
            for (int i = 0; i < indice - 1; i++) anterior = anterior.proximo;
            removido = anterior.proximo.dado;
            anterior.proximo = anterior.proximo.proximo;
        }
        tamanho--;
        return removido;
    }

    /** Remove a primeira ocorrência igual ao elemento. Retorna true se removeu. */
    public boolean remover(T elemento) {
        if (inicio == null) return false;
        if (Objects.equals(inicio.dado, elemento)) {
            inicio = inicio.proximo;
            tamanho--;
            return true;
        }
        No<T> atual = inicio;
        while (atual.proximo != null) {
            if (Objects.equals(atual.proximo.dado, elemento)) {
                atual.proximo = atual.proximo.proximo;
                tamanho--;
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean vazia() {
        return tamanho == 0;
    }

    public boolean contem(T elemento) {
        return indiceDe(elemento) >= 0;
    }

    public int indiceDe(T elemento) {
        int i = 0;
        for (No<T> atual = inicio; atual != null; atual = atual.proximo) {
            if (Objects.equals(atual.dado, elemento)) return i;
            i++;
        }
        return -1;
    }

    public void limpar() {
        inicio = null;
        tamanho = 0;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] arr = (T[]) new Object[tamanho];
        int i = 0;
        for (No<T> atual = inicio; atual != null; atual = atual.proximo) {
            arr[i++] = atual.dado;
        }
        return arr;
    }

    /**
     * Localiza o primeiro elemento que satisfaz o predicado e o remove.
     * Utilizado, por exemplo, para encontrar um veículo por placa.
     */
    public T removerSe(Predicate<T> predicado) {
        if (inicio == null) return null;
        if (predicado.test(inicio.dado)) {
            T r = inicio.dado;
            inicio = inicio.proximo;
            tamanho--;
            return r;
        }
        No<T> atual = inicio;
        while (atual.proximo != null) {
            if (predicado.test(atual.proximo.dado)) {
                T r = atual.proximo.dado;
                atual.proximo = atual.proximo.proximo;
                tamanho--;
                return r;
            }
            atual = atual.proximo;
        }
        return null;
    }

    /** Ordena os elementos usando o comparador informado. */
    public void ordenar(Comparator<? super T> comparador) {
        Objects.requireNonNull(comparador, "Comparador é obrigatório.");
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) toArray();
        Arrays.sort(arr, 0, tamanho, comparador);
        inicio = null;
        tamanho = 0;
        for (T e : arr) adicionar(e);
    }

    public void forEach(Consumer<? super T> acao) {
        for (No<T> atual = inicio; atual != null; atual = atual.proximo) {
            acao.accept(atual.dado);
        }
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

    @FunctionalInterface
    public interface Predicate<T> {
        boolean test(T elemento);
    }
}
