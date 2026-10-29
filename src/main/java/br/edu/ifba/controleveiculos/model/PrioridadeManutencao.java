package br.edu.ifba.controleveiculos.model;

/**
 * Prioridade de uma manutenção (conforme documentação
 * {@code docs/estrutura-dados#prioridade-de-manutenção}).
 */
public enum PrioridadeManutencao {
    ALTA(3),
    MEDIA(2),
    BAIXA(1);

    private final int nivel;

    PrioridadeManutencao(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public static PrioridadeManutencao aPartirDeKm(int kmPercorridos, int limiteManutencao) {
        if (kmPercorridos >= limiteManutencao) {
            return ALTA;
        }
        if (kmPercorridos >= limiteManutencao * 0.8) {
            return MEDIA;
        }
        return BAIXA;
    }
}
