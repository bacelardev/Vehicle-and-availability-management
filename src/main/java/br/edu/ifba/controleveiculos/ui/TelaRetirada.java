package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.Aluguel;
import br.edu.ifba.controleveiculos.service.AluguelService;
import br.edu.ifba.controleveiculos.service.RegraNegocioException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Tela de registro de retirada (contribuição de Mateus Almeida — US05).
 */
public class TelaRetirada {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final AluguelService servico;

    public TelaRetirada(AluguelService servico) {
        this.servico = servico;
    }

    public void exibir() {
        System.out.println("\n=== Retirada de Veículo ===");
        String placa = Entrada.texto("Placa: ").toUpperCase();
        String cliente = Entrada.texto("Cliente (CPF): ");
        String prev = Entrada.texto("Previsão de devolução (dd/MM/yyyy HH:mm) [Enter = usar a da reserva]: ");

        try {
            LocalDateTime previsao = prev.isBlank() ? null : LocalDateTime.parse(prev, FMT);
            Aluguel a = servico.registrarRetirada(placa, cliente, previsao);

            System.out.println("\nRetirada registrada com sucesso!");
            System.out.println("Aluguel nº " + a.getId());
            System.out.println("Veículo: " + a.getPlaca());
            System.out.println("Retirada em: " + a.getDataRetirada().format(FMT));
            System.out.println("Km inicial: " + a.getKmRetirada());
            System.out.println("Devolução prevista: " + a.getDataPrevistaDevolucao().format(FMT));
            System.out.println(a.getReservaId() != null
                    ? "Reserva vinculada: nº " + a.getReservaId()
                    : "Retirada sem reserva.");
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy HH:mm.");
        } catch (RegraNegocioException e) {
            System.out.println("Não foi possível registrar: " + e.getMessage());
        }
    }
}
