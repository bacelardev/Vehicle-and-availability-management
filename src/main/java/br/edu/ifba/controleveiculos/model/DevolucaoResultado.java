package br.edu.ifba.controleveiculos.model;

import java.time.Duration;
import java.util.Objects;

/**
 * Resumo do que aconteceu na devolução (usado pela tela e pelos testes).
 * (contribuição de Mateus Almeida)
 */
public class DevolucaoResultado {

    private final Aluguel aluguel;
    private final int kmPercorridos;
    private final Duration atraso;
    private final boolean encaminhadoParaManutencao;

    public DevolucaoResultado(Aluguel aluguel, int kmPercorridos, Duration atraso,
                              boolean encaminhadoParaManutencao) {
        this.aluguel = Objects.requireNonNull(aluguel);
        this.kmPercorridos = kmPercorridos;
        this.atraso = Objects.requireNonNull(atraso);
        this.encaminhadoParaManutencao = encaminhadoParaManutencao;
    }

    public Aluguel getAluguel() { return aluguel; }
    public int getKmPercorridos() { return kmPercorridos; }
    public Duration getAtraso() { return atraso; }
    public boolean houveAtraso() { return !atraso.isZero(); }
    public boolean isEncaminhadoParaManutencao() { return encaminhadoParaManutencao; }
}
