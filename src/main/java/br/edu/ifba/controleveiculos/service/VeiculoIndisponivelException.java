package br.edu.ifba.controleveiculos.service;

/**
 * GDV-65: tentativa de retirar/alugar um veículo que não está disponível.
 * (contribuição de Mateus Almeida)
 */
public class VeiculoIndisponivelException extends RegraNegocioException {

    public VeiculoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
