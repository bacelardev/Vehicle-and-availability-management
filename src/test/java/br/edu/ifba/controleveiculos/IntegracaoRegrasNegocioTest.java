package br.edu.ifba.controleveiculos;

import br.edu.ifba.controleveiculos.model.*;
import br.edu.ifba.controleveiculos.service.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class IntegracaoRegrasNegocioTest {
    private static final LocalDateTime INICIO = LocalDateTime.of(2026, 11, 1, 10, 0);
    private static final LocalDateTime FIM = INICIO.plusHours(4);

    private static final class Cenario {
        final Categoria categoria = new Categoria(1, "Sedan", "");
        final VeiculoService veiculos = new VeiculoService();
        final ManutencaoService manutencao = new ManutencaoService(veiculos);
        final ReservaService reservas = new ReservaService(veiculos, manutencao);
        Cenario() { veiculos.cadastrar("ABC1234", "Marca", "Modelo", 2024, 100,
                categoria, 5, 1200); }
    }

    @Test void placaDeveSerUnicaECapacidadePositiva() {
        Cenario c = new Cenario();
        assertThrows(RegraNegocioException.class, () -> c.veiculos.cadastrar("abc1234", "X", "Y",
                2024, 100, c.categoria, 5, 0));
        assertThrows(RegraNegocioException.class, () -> c.veiculos.cadastrar("DEF5678", "X", "Y",
                2024, 100, c.categoria, 0, 0));
    }

    @Test void reservaSobrepostaBloqueiaEConsultaPeriodoRespeitaLimites() {
        Cenario c = new Cenario();
        c.reservas.reservar("ABC1234", "cliente", INICIO, FIM);
        DisponibilidadeService disponibilidade = new DisponibilidadeService(c.veiculos, c.reservas, c.manutencao);
        assertFalse(disponibilidade.estaDisponivel("ABC1234", INICIO.plusHours(1), FIM.plusHours(1)));
        assertTrue(disponibilidade.estaDisponivel("ABC1234", FIM, FIM.plusHours(1)));
        assertThrows(RegraNegocioException.class,
                () -> c.reservas.reservar("ABC1234", "outro", INICIO.plusHours(1), FIM));
    }

    @Test void manutencaoBloqueiaReservaETransitaParaConcluida() {
        Cenario c = new Cenario();
        Manutencao m = c.manutencao.registrarManutencao("ABC1234", "Revisão",
                TipoManutencao.PREVENTIVA, PrioridadeManutencao.ALTA, 1200, INICIO, FIM);
        assertEquals(StatusVeiculo.MANUTENCAO, c.veiculos.getStatus("ABC1234"));
        assertThrows(VeiculoIndisponivelException.class,
                () -> c.reservas.reservar("ABC1234", "cliente", INICIO, FIM));
        assertEquals(StatusManutencao.CONCLUIDA, c.manutencao.concluir(m.getId()).getStatus());
        assertEquals(StatusVeiculo.DISPONIVEL, c.veiculos.getStatus("ABC1234"));
    }

    @Test void filaDeManutencaoPriorizaMaiorNivel() {
        Cenario c = new Cenario();
        Manutencao baixa = c.manutencao.registrarManutencao("ABC1234", "Baixa",
                TipoManutencao.PREVENTIVA, PrioridadeManutencao.BAIXA, 1200, INICIO, FIM);
        // A segunda placa precisa existir para que o serviço possa enfileirá-la.
        c.veiculos.cadastrar("XYZ9999", "Marca", "Outro", 2024, 100, c.categoria, 7, 0);
        Manutencao alta = c.manutencao.registrarManutencao("XYZ9999", "Alta", TipoManutencao.CORRETIVA,
                PrioridadeManutencao.ALTA, 1200, INICIO, FIM);
        assertEquals(alta.getId(), c.manutencao.atenderProximo().getId());
        assertEquals(StatusManutencao.EM_ANDAMENTO, c.manutencao.buscarPorId(alta.getId()).getStatus());
        assertNotNull(baixa);
    }

    @Test void devolucaoNaoAceitaQuilometragemMenorQueRetirada() {
        Cenario c = new Cenario();
        Clock clock = Clock.fixed(Instant.parse("2026-10-28T12:00:00Z"), ZoneOffset.UTC);
        AluguelService alugueis = new AluguelService(new AluguelRepository(), new HistoricoAluguel(),
                c.veiculos, c.reservas, c.manutencao, clock);
        alugueis.registrarRetirada("ABC1234", "cliente", INICIO);
        assertThrows(RegraNegocioException.class, () -> alugueis.registrarDevolucao("ABC1234", 1199));
        assertEquals(StatusVeiculo.ALUGADO, c.veiculos.getStatus("ABC1234"));
        assertEquals(0, alugueis.registrarDevolucao("ABC1234", 1200).getKmPercorridos());
    }

    @Test void usuarioAutenticaEProtegeUltimoAdministrador() {
        ServicoUsuario s = new ServicoUsuario(5);
        Usuario admin = s.cadastrar("Admin", "admin@example.test", "senha-segura", PerfilUsuario.ADMINISTRADOR);
        assertNotNull(s.autenticar("ADMIN@example.test", "senha-segura"));
        assertNull(s.autenticar("admin@example.test", "incorreta"));
        assertThrows(IllegalStateException.class, () -> s.desativar(admin, admin.getId()));
        assertThrows(IllegalStateException.class,
                () -> s.alterarDados(admin, admin.getId(), null, null, null, PerfilUsuario.ATENDENTE));
    }
}
