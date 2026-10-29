package br.edu.ifba.controleveiculos;

import br.edu.ifba.controleveiculos.model.Categoria;
import br.edu.ifba.controleveiculos.model.PerfilUsuario;
import br.edu.ifba.controleveiculos.model.Usuario;
import br.edu.ifba.controleveiculos.service.*;
import br.edu.ifba.controleveiculos.ui.*;

import java.util.Scanner;

/** Ponto de composição e inicialização da aplicação de console. */
public final class App {
    private App() { }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Entrada.configurar(scanner);

        CategoriaService categorias = new CategoriaService();
        Categoria padrao = categorias.cadastrar("Geral", "Categoria inicial");
        VeiculoService veiculos = new VeiculoService();
        ManutencaoService manutencao = new ManutencaoService(veiculos);
        ReservaService reservas = new ReservaService(veiculos, manutencao);
        DisponibilidadeService disponibilidade = new DisponibilidadeService(veiculos, reservas, manutencao);
        AluguelService alugueis = new AluguelService(new AluguelRepository(),
                new br.edu.ifba.controleveiculos.model.HistoricoAluguel(),
                veiculos, reservas, manutencao);
        ServicoUsuario usuarios = new ServicoUsuario(100);

        System.out.println("Sistema de Controle de Veículos e Disponibilidade");
        System.out.println("Dados mantidos somente durante esta execução.");
        System.out.println("Categoria inicial disponível: " + padrao.getNome());
        System.out.println("Configure a conta administradora inicial.");
        String nome = Entrada.texto("Nome do administrador: ");
        String email = Entrada.texto("E-mail do administrador: ");
        String senha = Entrada.texto("Senha do administrador (mínimo 6 caracteres): ");
        try {
            usuarios.cadastrar(nome, email, senha, PerfilUsuario.ADMINISTRADOR);
        } catch (IllegalArgumentException e) {
            System.out.println("Não foi possível criar o administrador: " + e.getMessage());
            return;
        }

        Usuario logado = new MenuLogin(usuarios).entrar();
        if (logado == null) {
            System.out.println("Número máximo de tentativas atingido. Encerrando.");
            return;
        }

        ServicoDashboard dashboard = new ServicoDashboard(new FonteDadosFrotaImpl(veiculos, reservas));
        ConsoleMenu menu = new ConsoleMenu(logado, scanner,
                new TelaDashboard(dashboard), new MenuUsuarios(usuarios),
                new TelaVeiculo(scanner, veiculos, categorias),
                new TelaDisponibilidade(scanner, disponibilidade),
                new TelaReserva(scanner, reservas), new TelaAluguel(scanner, alugueis),
                new TelaManutencao(scanner, manutencao, veiculos));
        menu.exibir();
    }
}
