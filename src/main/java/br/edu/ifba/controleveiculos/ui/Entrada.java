package br.edu.ifba.controleveiculos.ui;

import java.util.Scanner;

/**
 * Leitura de entradas do console.
 * <p>
 * (contribuição de Henrique Barbosa Ferrão). Utiliza um único {@link Scanner}
 * compartilhado (configurado em {@link App}) para evitar a concorrência entre
 * múltiplos buffers sobre o mesmo {@code System.in}.
 */
public final class Entrada {

    private static Scanner LEITOR;

    private Entrada() {
    }

    /** Configura o scanner compartilhado usado por toda a UI. */
    public static void configurar(Scanner scanner) {
        LEITOR = scanner;
    }

    public static String texto(String mensagem) {
        if (LEITOR == null) {
            throw new IllegalStateException("A entrada do console ainda não foi configurada.");
        }
        System.out.print(mensagem);
        return LEITOR.nextLine().trim();
    }

    public static int inteiro(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(texto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public static double decimal(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(texto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
