package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.PerfilUsuario;
import br.edu.ifba.controleveiculos.model.Permissao;
import br.edu.ifba.controleveiculos.model.Usuario;
import br.edu.ifba.controleveiculos.structures.ListaUsuarios;

/**
 * Gestão de usuários (Henrique Barbosa Ferrão — US09).
 * <p>
 * Preserva a lógica original: hash de senha via PBKDF2-HMAC-SHA256 com salt
 * aleatório, controle de permissões por perfil e proteção do último
 * administrador.
 */
public class ServicoUsuario {

    private final ListaUsuarios lista;
    private int proximoId;

    public ServicoUsuario(int capacidade) {
        if (capacidade <= 0) throw new IllegalArgumentException("A capacidade deve ser positiva.");
        this.lista = new ListaUsuarios(capacidade);
        this.proximoId = 1;
    }

    public Usuario cadastrar(Usuario solicitante, String nome, String email, String senha, PerfilUsuario perfil) {
        exigirGerenciamento(solicitante);
        return cadastrar(nome, email, senha, perfil);
    }

    public Usuario[] listarTodos(Usuario solicitante) {
        exigirGerenciamento(solicitante);
        return listarTodos();
    }

    public Usuario buscarPorId(Usuario solicitante, int id) {
        exigirGerenciamento(solicitante);
        return buscarPorId(id);
    }

    public Usuario alterarDados(Usuario solicitante, int id, String nome, String email,
                                String senha, PerfilUsuario perfil) {
        exigirGerenciamento(solicitante);
        return alterarDados(id, nome, email, senha, perfil);
    }

    public void desativar(Usuario solicitante, int id) {
        exigirGerenciamento(solicitante);
        Usuario usuario = buscarPorId(id);
        if (usuario.getId() == solicitante.getId()) {
            throw new IllegalStateException("Você não pode desativar o próprio usuário.");
        }
        if (usuario.getPerfil() == PerfilUsuario.ADMINISTRADOR && contarAdministradoresAtivos() <= 1) {
            throw new IllegalStateException("Não é possível desativar o último administrador ativo.");
        }
        if (!usuario.isAtivo()) {
            throw new IllegalStateException("Usuário já está desativado.");
        }
        usuario.desativar();
    }

    private void exigirGerenciamento(Usuario solicitante) {
        if (solicitante == null || !solicitante.temPermissao(Permissao.GERENCIAR_USUARIOS)
                || lista.buscarPorId(solicitante.getId()) != solicitante) {
            throw new SecurityException("Usuário sem permissão para gerenciar contas.");
        }
    }

    public Usuario cadastrar(String nome, String email, String senha, PerfilUsuario perfil) {
        validarObrigatorios(nome, email, senha, perfil);
        String emailLimpo = email.trim();
        if (lista.buscarPorEmail(emailLimpo) != null) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        }
        Usuario usuario = new Usuario(proximoId++, nome.trim(), emailLimpo, senha, perfil);
        lista.anexar(usuario);
        return usuario;
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || senha == null) return null;
        Usuario usuario = lista.buscarPorEmail(email.trim());
        if (usuario != null && usuario.autenticar(email.trim(), senha)) {
            return usuario;
        }
        return null;
    }

    public Usuario buscarPorId(int id) {
        Usuario usuario = lista.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return usuario;
    }

    public Usuario[] listarTodos() {
        Usuario[] copia = new Usuario[lista.quantidade()];
        for (int i = 0; i < copia.length; i++) {
            copia[i] = lista.selecionar(i);
        }
        return copia;
    }

    public Usuario alterarDados(int id, String nome, String email, String senha, PerfilUsuario perfil) {
        Usuario usuario = buscarPorId(id);
        if (!usuario.isAtivo()) throw new IllegalStateException("Não é possível alterar usuário desativado.");
        if (senha != null && !senha.isBlank()) validarSenha(senha);
        if (perfil != null && usuario.getPerfil() == PerfilUsuario.ADMINISTRADOR
                && perfil != PerfilUsuario.ADMINISTRADOR && contarAdministradoresAtivos() <= 1) {
            throw new IllegalStateException("Não é possível rebaixar o último administrador ativo.");
        }
        String emailLimpo = null;
        if (email != null && !email.isBlank()) {
            emailLimpo = email.trim();
            validarEmail(emailLimpo);
            Usuario existente = lista.buscarPorEmail(emailLimpo);
            if (existente != null && existente.getId() != usuario.getId()) {
                throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
            }
        }
        if (emailLimpo != null) usuario.setEmail(emailLimpo);
        if (nome != null && !nome.isBlank()) usuario.setNome(nome.trim());
        if (senha != null && !senha.isBlank()) usuario.setSenha(senha);
        if (perfil != null) usuario.setPerfil(perfil);
        return usuario;
    }

    private int contarAdministradoresAtivos() {
        int total = 0;
        for (int i = 0; i < lista.quantidade(); i++) {
            Usuario u = lista.selecionar(i);
            if (u.isAtivo() && u.getPerfil() == PerfilUsuario.ADMINISTRADOR) total++;
        }
        return total;
    }

    private void validarObrigatorios(String nome, String email, String senha, PerfilUsuario perfil) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("O nome é obrigatório.");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("O e-mail é obrigatório.");
        validarEmail(email.trim());
        if (senha == null || senha.isBlank()) throw new IllegalArgumentException("A senha é obrigatória.");
        validarSenha(senha);
        if (perfil == null) throw new IllegalArgumentException("O perfil é obrigatório.");
    }

    private void validarEmail(String email) {
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

    private void validarSenha(String senha) {
        if (senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }
    }
}
