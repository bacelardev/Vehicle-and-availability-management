# Entidades do Sistema

As entidades representam os principais elementos envolvidos no sistema de
Controle de Veículos e Disponibilidade da locadora.

| Entidade | Descrição | Principais atributos | Relacionamentos |
|---|---|---|---|
| **Veículo** | Representa cada veículo pertencente à frota da locadora. | `id`, `placa`, `marca`, `modelo`, `ano`, `categoria`, `valorDiaria`, `capacidadePassageiros`, `quilometragemAtual`, `status` | Pertence a uma Categoria; possui Reservas, Aluguéis e Manutenções |
| **Categoria** | Classifica os veículos de acordo com suas características. | `id`, `nome`, `descricao` | Possui vários Veículos |
| **Cliente** | Representa a pessoa que realiza uma reserva ou aluguel. | `id`, `nome`, `cpf`, `telefone`, `email`, `cnh` | Pode possuir várias Reservas e Aluguéis |
| **Reserva** | Representa a solicitação de um veículo para determinado período. | `id`, `dataInicio`, `dataFim`, `status`, `dataCriacao` | Pertence a um Cliente e a um Veículo |
| **Aluguel** | Representa a locação efetivamente realizada. | `id`, `dataRetirada`, `dataDevolucao`, `quilometragemRetirada`, `quilometragemDevolucao`, `status` | Pertence a um Cliente e a um Veículo |
| **Manutenção** | Representa um período em que o veículo fica indisponível para manutenção. | `id`, `dataInicio`, `dataFim`, `motivo`, `tipo`, `status` | Pertence a um Veículo |
| **Usuário** | Representa uma pessoa autorizada a acessar o sistema. | `id`, `nome`, `email`, `senha`, `perfil`, `status` | Possui um Perfil |
| **Perfil** | Define as permissões de acesso do usuário. | `id`, `nome`, `descricao` | Pode estar associado a vários Usuários |

## Relacionamentos

- Uma **Categoria** pode possuir vários **Veículos**.
- Um **Veículo** pode possuir várias **Reservas**.
- Um **Cliente** pode realizar várias **Reservas**.
- Um **Veículo** pode possuir vários **Aluguéis**.
- Um **Cliente** pode realizar vários **Aluguéis**.
- Um **Veículo** pode possuir vários registros de **Manutenção**.
- Um **Perfil** pode estar associado a vários **Usuários**.
- Cada **Usuário** possui um **Perfil**.

## Observação

As entidades **Reserva** e **Aluguel** são mantidas separadamente. A Reserva
representa a solicitação antecipada de um veículo, enquanto o Aluguel
representa a utilização efetiva do veículo, incluindo as informações de
retirada, devolução e quilometragem.
