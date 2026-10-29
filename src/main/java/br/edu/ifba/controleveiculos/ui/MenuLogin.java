package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Usuario;
import br.edu.ifba.controleveiculos.service.ServicoUsuario;

/**
 * Tela de autenticação (Henrique Barbosa Ferrão — US09).
 */
public class MenuLogin {

    private static final int MAXIMO_TENTATIVAS = 3;
    private final ServicoUsuario servico;

    public MenuLogin(ServicoUsuario servico) {
        this.servico = servico;
    }

    public Usuario entrar() {
        for (int tentativa = 1; tentativa <= MAXIMO_TENTATIVAS; tentativa++) {
            System.out.println("\n=========== LOGIN ===========");
            String email = Entrada.texto("E-mail: ");
            String senha = Entrada.texto("Senha: ");
            Usuario usuario = servico.autenticar(email, senha);
            if (usuario != null) {
                System.out.println("Bem-vindo(a), " + usuario.getNome() + "!");
                return usuario;
            }
            System.out.println("E-mail ou senha inválidos, ou usuário desativado. (tentativa "
                    + tentativa + "/" + MAXIMO_TENTATIVAS + ")");
        }
        return null;
    }
}
