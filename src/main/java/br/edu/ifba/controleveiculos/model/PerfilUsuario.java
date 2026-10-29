package br.edu.ifba.controleveiculos.model;

import java.util.EnumSet;
import java.util.Set;
import java.util.Collections;

/**
 * Perfis de usuário e as permissões associadas
 * (contribuição de Henrique Barbosa Ferrão — US09).
 * <p>
 * Alinhado à documentação {@code docs/requisistos/perfis-usuarios.md}:
 * Administrador e Atendente.
 */
public enum PerfilUsuario {

    ADMINISTRADOR(EnumSet.of(
            Permissao.CADASTRAR_VEICULO,
            Permissao.EDITAR_VEICULO,
            Permissao.CADASTRAR_MANUTENCAO,
            Permissao.VER_VEICULOS_PARA_MANUTENCAO,
            Permissao.CONSULTAR_HISTORICO,
            Permissao.CONSULTAR_VEICULOS,
            Permissao.GERENCIAR_USUARIOS,
            Permissao.VER_DASHBOARD,
            Permissao.VER_INDICADORES_GERENCIAIS)),

    ATENDENTE(EnumSet.of(
            Permissao.CONSULTAR_VEICULOS,
            Permissao.VERIFICAR_DISPONIBILIDADE,
            Permissao.REALIZAR_RESERVA,
            Permissao.REGISTRAR_RETIRADA,
            Permissao.REGISTRAR_DEVOLUCAO,
            Permissao.REGISTRAR_QUILOMETRAGEM,
            Permissao.VER_DASHBOARD));

    private final Set<Permissao> permissoes;

    PerfilUsuario(Set<Permissao> permissoes) {
        this.permissoes = Collections.unmodifiableSet(EnumSet.copyOf(permissoes));
    }

    public boolean temPermissao(Permissao permissao) {
        return permissoes.contains(permissao);
    }

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }
}
