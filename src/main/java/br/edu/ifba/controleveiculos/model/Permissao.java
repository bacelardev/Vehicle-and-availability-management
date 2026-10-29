package br.edu.ifba.controleveiculos.model;

/**
 * Permissões de acesso do sistema, definidas pelo módulo de usuários
 * (contribuição de Henrique Barbosa Ferrão — US09).
 */
public enum Permissao {
    CADASTRAR_VEICULO,
    EDITAR_VEICULO,
    CADASTRAR_MANUTENCAO,
    CONSULTAR_HISTORICO,
    VER_VEICULOS_PARA_MANUTENCAO,
    CONSULTAR_VEICULOS,
    VERIFICAR_DISPONIBILIDADE,
    REALIZAR_RESERVA,
    REGISTRAR_RETIRADA,
    REGISTRAR_DEVOLUCAO,
    REGISTRAR_QUILOMETRAGEM,
    GERENCIAR_USUARIOS,
    VER_DASHBOARD,
    VER_INDICADORES_GERENCIAIS
}
