package br.edu.ifba.controleveiculos.service;

import br.edu.ifba.controleveiculos.model.Permissao;
import br.edu.ifba.controleveiculos.model.Usuario;

/** Monta indicadores a partir da fonte conectada à frota em memória. */
public class ServicoDashboard {
    private final FonteDadosFrota fonte;

    public ServicoDashboard(FonteDadosFrota fonte) {
        this.fonte = java.util.Objects.requireNonNull(fonte);
    }

    public String montar(Usuario usuario) {
        if (usuario == null || !usuario.temPermissao(Permissao.VER_DASHBOARD)) {
            throw new SecurityException("perfil sem permissão para visualizar o dashboard.");
        }
        return "===== DASHBOARD DA FROTA =====\n"
                + "Veículos: " + fonte.totalVeiculos() + "\n"
                + "Disponíveis: " + fonte.veiculosDisponiveis() + "\n"
                + "Reservados: " + fonte.veiculosReservados() + "\n"
                + "Alugados: " + fonte.veiculosAlugados() + "\n"
                + "Em manutenção: " + fonte.veiculosEmManutencao() + "\n"
                + "Reservas realizadas: " + fonte.reservasRealizadas();
    }
}
