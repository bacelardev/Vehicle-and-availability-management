package br.edu.ifba.controleveiculos.service;

/**
 * Violação de regra de negócio do sistema.
 * (contribuição de Mateus Almeida)
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
