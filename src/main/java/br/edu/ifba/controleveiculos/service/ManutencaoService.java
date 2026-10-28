import java.util.ArrayList;

public class GerenciadorFrota {
    private ArrayList<Veiculo> veiculos;
    private ArrayList<Categoria> categorias;

    public GerenciadorFrota() {
        veiculos = new ArrayList<>();
        categorias = new ArrayList<>();
    }

    // Cadastro de categorias
    public void cadastrarCategoria(Categoria categoria) {
        categorias.add(categoria);
        System.out.println("Categoria cadastrada!");
    }

    // Cadastro de veículos
    public void cadastrarVeiculo(Veiculo veiculo) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca())) {
                System.out.println("Placa já cadastrada!");
                return;
            }
        }
        veiculos.add(veiculo);
        System.out.println("Veículo cadastrado com sucesso!");
    }

    // Consulta de todos os veículos
    public void consultarVeiculos() {
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }
        for (Veiculo v : veiculos) {
            System.out.println(v);
        }
    }

    // Consulta por placa
    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    // Consulta por categoria
    public void consultarPorCategoria(String nomeCategoria) {
        boolean encontrado = false;
        for (Veiculo v : veiculos) {
            if (v.getCategoria().getNome().equalsIgnoreCase(nomeCategoria)) {
                System.out.println(v);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum veículo encontrado nessa categoria.");
        }
    }

}
