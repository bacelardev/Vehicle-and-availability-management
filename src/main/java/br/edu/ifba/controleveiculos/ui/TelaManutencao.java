package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Manutencao;
import br.edu.ifba.controleveiculos.model.PrioridadeManutencao;
import br.edu.ifba.controleveiculos.model.TipoManutencao;
import br.edu.ifba.controleveiculos.service.ManutencaoService;
import br.edu.ifba.controleveiculos.service.RegraNegocioException;
import br.edu.ifba.controleveiculos.service.VeiculoService;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Tela de gerenciamento de manutenção (US07/US08).
 */
public class TelaManutencao {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final Scanner entrada;
    private final ManutencaoService manutencao;
    private final VeiculoService veiculos;

    public TelaManutencao(Scanner entrada, ManutencaoService manutencao, VeiculoService veiculos) {
        this.entrada = entrada;
        this.manutencao = manutencao;
        this.veiculos = veiculos;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== MANUTENÇÃO =====");
            System.out.println("1 - Bloquear veículo para manutenção");
            System.out.println("2 - Identificar veículos que necessitam manutenção");
            System.out.println("3 - Listar manutenções ativas");
            System.out.println("4 - Concluir manutenção");
            System.out.println("5 - Cancelar manutenção");
            System.out.println("6 - Histórico por placa");
            System.out.println("7 - Atender próximo da fila (prioridade)");
            System.out.println("0 - Voltar");
            opcao = Entrada.inteiro("Opção: ");
            try {
                switch (opcao) {
                    case 1 -> bloquear();
                    case 2 -> identificar();
                    case 3 -> ativas();
                    case 4 -> concluir();
                    case 5 -> cancelar();
                    case 6 -> historico();
                    case 7 -> atenderProximo();
                    case 0 -> { }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RegraNegocioException | IllegalArgumentException | DateTimeParseException e) {
                System.out.println("Não foi possível: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void bloquear() {
        System.out.println();
        String placa = Entrada.texto("Placa: ").toUpperCase();
        String motivo = Entrada.texto("Motivo: ");
        TipoManutencao tipo = lerTipo();
        PrioridadeManutencao prioridade = lerPrioridade();
        String ini = Entrada.texto("Data início (dd/MM/yyyy HH:mm) [Enter = imediata]: ");
        String fim = Entrada.texto("Data fim (dd/MM/yyyy HH:mm) [Enter = indefinida]: ");
        LocalDateTime dataIni = ini.isBlank() ? null : LocalDateTime.parse(ini.trim(), FMT);
        LocalDateTime dataFim = fim.isBlank() ? null : LocalDateTime.parse(fim.trim(), FMT);
        int km = veiculos.existe(placa) ? veiculos.getQuilometragem(placa) : 0;
        Manutencao m = manutencao.registrarManutencao(placa, motivo, tipo, prioridade, km, dataIni, dataFim);
        System.out.println("Veículo bloqueado para manutenção: " + m.getId() + " (" + m.getPlaca() + ").");
    }

    private void identificar() {
        Lista<Manutencao> identificados = manutencao.identificarNecessidades(veiculos, manutencao.getLimiteKmManutencao());
        if (identificados.vazia()) {
            System.out.println("Nenhum veículo atinge o limite de quilometragem ("
                    + manutencao.getLimiteKmManutencao() + " km).");
            return;
        }
        System.out.println("Veículos que necessitam de manutenção:");
        identificados.forEach(m -> System.out.println("  " + m.getPlaca() + " - " + m.getMotivo()
                + " [" + m.getPrioridade() + "]"));
    }

    private void ativas() {
        Lista<Manutencao> ativas = manutencao.listarAtivas();
        if (ativas.vazia()) {
            System.out.println("Nenhuma manutenção ativa.");
            return;
        }
        ativas.forEach(System.out::println);
    }

    private void concluir() {
        long id = Entrada.inteiro("ID da manutenção a concluir: ");
        Manutencao m = manutencao.concluir(id);
        System.out.println("Manutenção " + m.getId() + " concluída. Veículo " + m.getPlaca() + " liberado.");
    }

    private void cancelar() {
        long id = Entrada.inteiro("ID da manutenção a cancelar: ");
        Manutencao m = manutencao.cancelar(id);
        System.out.println("Manutenção " + m.getId() + " cancelada.");
    }

    private void historico() {
        String placa = Entrada.texto("Placa: ").toUpperCase();
        Lista<Manutencao> lista = manutencao.listarPorPlaca(placa);
        if (lista.vazia()) {
            System.out.println("Nenhum registro de manutenção para " + placa + ".");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void atenderProximo() {
        Manutencao m = manutencao.atenderProximo();
        if (m == null) {
            System.out.println("Nenhuma manutenção na fila.");
            return;
        }
        System.out.println("Atendendo: " + m);
    }

    private TipoManutencao lerTipo() {
        System.out.println("Tipo: 1 - PREVENTIVA | 2 - CORRETIVA");
        return Entrada.inteiro("Tipo: ") == 2 ? TipoManutencao.CORRETIVA : TipoManutencao.PREVENTIVA;
    }

    private PrioridadeManutencao lerPrioridade() {
        System.out.println("Prioridade: 1 - ALTA | 2 - MÉDIA | 3 - BAIXA");
        return switch (Entrada.inteiro("Prioridade: ")) {
            case 2 -> PrioridadeManutencao.MEDIA;
            case 3 -> PrioridadeManutencao.BAIXA;
            default -> PrioridadeManutencao.ALTA;
        };
    }
}
