package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Veiculo;
import br.edu.ifba.controleveiculos.service.DisponibilidadeService;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Tela de consulta de disponibilidade por período (US03).
 */
public class TelaDisponibilidade {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final Scanner entrada;
    private final DisponibilidadeService disponibilidade;

    public TelaDisponibilidade(Scanner entrada, DisponibilidadeService disponibilidade) {
        this.entrada = entrada;
        this.disponibilidade = disponibilidade;
    }

    public void exibir() {
        System.out.println("\n=== Consulta de Disponibilidade ===");
        String ini = Entrada.texto("Data/hora início (dd/MM/yyyy HH:mm): ");
        String fim = Entrada.texto("Data/hora fim   (dd/MM/yyyy HH:mm): ");
        try {
            LocalDateTime inicio = LocalDateTime.parse(ini.trim(), FMT);
            LocalDateTime fimDt = LocalDateTime.parse(fim.trim(), FMT);
            if (!fimDt.isAfter(inicio)) {
                System.out.println("O fim deve ser posterior ao início.");
                return;
            }
            listaDisponiveis(inicio, fimDt);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy HH:mm.");
        }
    }

    private void listaDisponiveis(LocalDateTime inicio, LocalDateTime fim) {
        Lista<Veiculo> disponiveis = disponibilidade.listarDisponiveis(inicio, fim);
        if (disponiveis.vazia()) {
            System.out.println("Nenhum veículo disponível nesse período.");
            return;
        }
        System.out.println("Veículos disponíveis de " + inicio.format(FMT) + " a " + fim.format(FMT) + ":");
        disponiveis.forEach(v -> System.out.println("  " + v.getPlaca() + " - " + v.getModelo()
                + " (" + v.getCategoria().getNome() + ") - R$ " + v.getValorDiaria()));
    }
}
