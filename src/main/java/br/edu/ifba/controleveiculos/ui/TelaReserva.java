package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Reserva;
import br.edu.ifba.controleveiculos.service.RegraNegocioException;
import br.edu.ifba.controleveiculos.service.ReservaService;
import br.edu.ifba.controleveiculos.structures.Lista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Tela de realização e gerenciamento de reservas (US04).
 */
public class TelaReserva {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final Scanner entrada;
    private final ReservaService reservas;

    public TelaReserva(Scanner entrada, ReservaService reservas) {
        this.entrada = entrada;
        this.reservas = reservas;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== RESERVAS =====");
            System.out.println("1 - Nova reserva");
            System.out.println("2 - Listar reservas");
            System.out.println("3 - Cancelar reserva");
            System.out.println("0 - Voltar");
            opcao = Entrada.inteiro("Opção: ");
            try {
                switch (opcao) {
                    case 1 -> novaReserva();
                    case 2 -> listar();
                    case 3 -> cancelar();
                    case 0 -> { }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RegraNegocioException | IllegalArgumentException | DateTimeParseException e) {
                System.out.println("Não foi possível: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void novaReserva() {
        System.out.println();
        String placa = Entrada.texto("Placa: ").toUpperCase();
        String cliente = Entrada.texto("CPF do cliente: ");
        LocalDateTime inicio = LocalDateTime.parse(Entrada.texto("Início (dd/MM/yyyy HH:mm): ").trim(), FMT);
        LocalDateTime fim = LocalDateTime.parse(Entrada.texto("Fim   (dd/MM/yyyy HH:mm): ").trim(), FMT);
        Reserva r = reservas.reservar(placa, cliente, inicio, fim);
        System.out.println("Reserva criada: nº " + r.getId() + " para " + r.getPlaca()
                + " (" + r.getClienteId() + ").");
    }

    private void listar() {
        Lista<Reserva> lista = reservas.listar();
        if (lista.vazia()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void cancelar() {
        long id = Entrada.inteiro("ID da reserva a cancelar: ");
        reservas.cancelar(id);
        System.out.println("Reserva cancelada.");
    }
}
