package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Permissao;
import br.edu.ifba.controleveiculos.model.PerfilUsuario;
import br.edu.ifba.controleveiculos.model.Usuario;
import br.edu.ifba.controleveiculos.service.ServicoUsuario;

/**
 * Tela de gerenciamento de usuários (Henrique Barbosa Ferrão — US09).
 */
public class MenuUsuarios {

    private final ServicoUsuario servico;

    public MenuUsuarios(ServicoUsuario servico) {
        this.servico = servico;
    }

    public void exibir(Usuario logado) {
        if (logado == null || !logado.temPermissao(Permissao.GERENCIAR_USUARIOS)) {
            System.out.println("Acesso negado: seu perfil não pode gerenciar usuários.");
            return;
        }
        int opcao;
        do {
            System.out.println();
            System.out.println("===== GERENCIAMENTO DE USUÁRIOS =====");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Alterar dados de um usuário");
            System.out.println("4 - Desativar usuário");
            System.out.println("0 - Voltar");
            opcao = Entrada.inteiro("Opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrar(logado);
                    case 2 -> listar(logado);
                    case 3 -> alterar(logado);
                    case 4 -> desativar(logado);
                    case 0 -> { }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void cadastrar(Usuario logado) {
        String nome = Entrada.texto("Nome: ");
        String email = Entrada.texto("E-mail: ");
        String senha = Entrada.texto("Senha (mínimo 6 caracteres): ");
        PerfilUsuario perfil = lerPerfil(false);
        Usuario criado = servico.cadastrar(logado, nome, email, senha, perfil);
        System.out.println("Usuário cadastrado com ID " + criado.getId() + ".");
    }

    private void listar(Usuario logado) {
        Usuario[] usuarios = servico.listarTodos(logado);
        System.out.println("ID  | Nome                 | E-mail                       | Perfil        | Situação");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
        if (usuarios.length == 0) System.out.println("Nenhum usuário cadastrado.");
    }

    private void alterar(Usuario logado) {
        int id = Entrada.inteiro("ID do usuário: ");
        Usuario atual = servico.buscarPorId(logado, id);
        System.out.println("Atual: " + atual);
        System.out.println("(Deixe em branco para manter o valor atual)");
        String nome = Entrada.texto("Novo nome: ");
        String email = Entrada.texto("Novo e-mail: ");
        String senha = Entrada.texto("Nova senha: ");
        PerfilUsuario perfil = lerPerfil(true);
        servico.alterarDados(logado, id, nome, email, senha, perfil);
        System.out.println("Dados atualizados.");
    }

    private void desativar(Usuario logado) {
        int id = Entrada.inteiro("ID do usuário a desativar: ");
        servico.desativar(logado, id);
        System.out.println("Usuário desativado.");
    }

    private PerfilUsuario lerPerfil(boolean opcional) {
        System.out.println("Perfil: 1 - ADMINISTRADOR | 2 - ATENDENTE" + (opcional ? " | 0 - manter" : ""));
        int escolha = Entrada.inteiro("Perfil: ");
        if (opcional && escolha == 0) return null;
        return switch (escolha) {
            case 1 -> PerfilUsuario.ADMINISTRADOR;
            case 2 -> PerfilUsuario.ATENDENTE;
            default -> throw new IllegalArgumentException("Perfil inválido.");
        };
    }
}
