package br.edu.ifba.controleveiculos.model;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Usuário do sistema com autenticação baseada em senha
 * (contribuição de Henrique Barbosa Ferrão — US09).
 * <p>
 * A senha nunca é armazenada em texto claro: é derivada via
 * PBKDF2-HMAC-SHA256 com salt aleatório.
 */
public class Usuario {

    private final int id;
    private String nome;
    private String email;
    private String senhaHash;
    private static final SecureRandom ALEATORIO = new SecureRandom();
    private static final int ITERACOES = 120_000;
    private PerfilUsuario perfil;
    private boolean ativo;

    public Usuario(int id, String nome, String email, String senha, PerfilUsuario perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = gerarHash(senha);
        this.perfil = perfil;
        this.ativo = true;
    }

    private static String gerarHash(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }
        byte[] salt = new byte[16];
        ALEATORIO.nextBytes(salt);
        byte[] hash = derivar(senha, salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    private static byte[] derivar(String senha, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, ITERACOES, 256);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (java.security.GeneralSecurityException e) {
            throw new IllegalStateException("Falha ao processar senha.", e);
        } finally {
            spec.clearPassword();
        }
    }

    private boolean verificarSenha(String senha) {
        if (senha == null) return false;
        String[] partes = senhaHash.split(":", 2);
        byte[] salt = Base64.getDecoder().decode(partes[0]);
        byte[] esperado = Base64.getDecoder().decode(partes[1]);
        return MessageDigest.isEqual(esperado, derivar(senha, salt));
    }

    public boolean autenticar(String email, String senha) {
        return ativo && this.email.equalsIgnoreCase(email.trim()) && verificarSenha(senha);
    }

    public boolean temPermissao(Permissao permissao) {
        return ativo && perfil.temPermissao(permissao);
    }

    public void desativar() {
        this.ativo = false;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senhaHash = gerarHash(senha);
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public String toString() {
        return String.format("%-3d | %-20s | %-28s | %-13s | %s",
                id, nome, email, perfil, ativo ? "ATIVO" : "INATIVO");
    }
}
