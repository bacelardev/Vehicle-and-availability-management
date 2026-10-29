package br.edu.ifba.controleveiculos.ui;

import br.edu.ifba.controleveiculos.model.DevolucaoResultado;
import br.edu.ifba.controleveiculos.service.AluguelService;
import br.edu.ifba.controleveiculos.service.RegraNegocioException;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * Tela de registro de devolução (contribuição de Mateus Almeida — US06).
 */
public class TelaDevolucao {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final AluguelService servico;

    public TelaDevolucao(AluguelService servico) {
        this.servico = servico;
    }

    public void exibir() {
        System.out.println("\n=== Devolução de Veículo ===");
        String placa = Entrada.texto("Placa: ").toUpperCase();
        String kmTexto = Entrada.texto("Quilometragem na devolução: ");
        String obs = Entrada.texto("Observações (opcional): ");

        try {
            int km = Integer.parseInt(kmTexto);
            DevolucaoResultado r = servico.registrarDevolucao(placa, km, obs.isBlank() ? null : obs);

            System.out.println("\nDevolução registrada com sucesso!");
            System.out.println("Devolvido em: " + r.getAluguel().getDataDevolucao().format(FMT));
            System.out.println("Km percorridos: " + r.getKmPercorridos());
            if (r.houveAtraso()) {
                System.out.println("ATENÇÃO: devolução com atraso de " + formatar(r.getAtraso()) + ".");
            } else {
                System.out.println("Devolução dentro do prazo.");
            }
            System.out.println(r.isEncaminhadoParaManutencao()
                    ? "Veículo encaminhado para manutenção."
                    : "Veículo liberado para novas reservas.");
        } catch (NumberFormatException e) {
            System.out.println("Quilometragem inválida. Digite apenas números.");
        } catch (RegraNegocioException e) {
            System.out.println("Não foi possível registrar: " + e.getMessage());
        }
    }

    private String formatar(Duration d) {
        long dias = d.toDays();
        long horas = d.toHoursPart();
        long minutos = d.toMinutesPart();
        return dias + "d " + horas + "h " + minutos + "min";
    }
}
