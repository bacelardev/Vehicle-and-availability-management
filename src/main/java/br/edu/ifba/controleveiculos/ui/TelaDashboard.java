package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Permissao;
import br.edu.ifba.controleveiculos.model.Usuario;
import br.edu.ifba.controleveiculos.service.ServicoDashboard;

/**
 * Tela do dashboard (Henrique Barbosa Ferrão — US10).
 */
public class TelaDashboard {

    private final ServicoDashboard servico;

    public TelaDashboard(ServicoDashboard servico) {
        this.servico = servico;
    }

    public void exibir(Usuario usuario) {
        try {
            System.out.println();
            System.out.println(servico.montar(usuario));
        } catch (SecurityException e) {
            System.out.println("Acesso negado: " + e.getMessage());
        }
        Entrada.texto("Pressione Enter para voltar...");
    }
}
