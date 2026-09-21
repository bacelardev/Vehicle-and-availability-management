# Distribuição da Equipe

## Integrantes

- Guilherme Pereira Bacelar
- Henrique Barbosa Ferrão
- Rodolfo Gonçalves
- Mateus Oliveira de Araujo
- Mateus Rodrigues França de Almeida

---

## 1. Guilherme Pereira Bacelar - Controle de Veículos e Categorias

### Responsabilidade principal

Responsável pelo módulo de cadastro, consulta e organização dos veículos da frota.

### Funcionalidades

- Cadastro de veículos;
- Edição de veículos;
- Remoção de veículos;
- Consulta de veículos por placa;
- Consulta por modelo;
- Consulta por categoria;
- Classificação dos veículos;
- Registro da capacidade de passageiros;
- Registro do valor da diária;
- Registro da quilometragem atual;
- Controle inicial do status do veículo;
- Ordenação dos veículos por categoria, valor da diária ou ano.

### Entidades

- Veiculo
- Categoria

### User Stories

- **US01 — Cadastro de veículos**
- **US02 — Consulta e organização da frota**

### Estruturas de Dados

- Lista de veículos;
- Estruturas auxiliares para busca e ordenação, quando aplicável.

### Participação na documentação

- Entidades relacionadas aos veículos;
- Regras de cadastro;
- Fluxo de cadastro e consulta;
- Estruturas de dados utilizadas no módulo;
- Telas de cadastro e consulta;
- Parte correspondente do diagrama UML.

---

## 2. Henrique Barbosa Ferrão — Disponibilidade e Reservas

### Responsabilidade principal

Responsável pelo controle da disponibilidade dos veículos e pelo processo de reservas.

### Funcionalidades

- Consulta de veículos disponíveis;
- Pesquisa por período;
- Criação de reservas;
- Cancelamento de reservas;
- Verificação de conflitos entre reservas;
- Impedimento de reservas em períodos sobrepostos;
- Verificação da disponibilidade do veículo;
- Verificação de períodos de manutenção antes da reserva.

### Entidades

- Reserva
- Cliente

### User Stories

- **US03 — Consulta de disponibilidade**
- **US04 — Reserva de veículo**

### Estruturas de Dados

- Lista de reservas;
- Estrutura para consulta e verificação de conflitos de períodos.

### Participação na documentação

- Entidade Reserva;
- Regras relacionadas à disponibilidade;
- Regras de conflito de períodos;
- Fluxo de reservas;
- Estruturas de dados utilizadas;
- Telas de disponibilidade e reserva;
- Parte correspondente do diagrama UML.

---

## 3. Rodolfo Gonçalves — Aluguéis e Quilometragem

### Responsabilidade principal

Responsável pelo controle da retirada, utilização e devolução dos veículos.

### Funcionalidades

- Registro da retirada do veículo;
- Registro da quilometragem inicial;
- Registro da devolução;
- Registro da quilometragem final;
- Cálculo dos quilômetros percorridos;
- Consulta do histórico de aluguéis;
- Atualização do status do veículo após devolução.

### Entidades

- Aluguel
- Histórico de Aluguel

### User Stories

- **US05 — Retirada de veículo**
- **US06 — Devolução de veículo**

### Estruturas de Dados

- Lista de aluguéis;
- Estrutura para armazenamento e consulta do histórico.

### Regra principal

A quilometragem percorrida será calculada através da fórmula:

```
quilômetros percorridos = quilometragem de devolução - quilometragem de retirada
```

### Participação na documentação

- Entidade Aluguel;
- Fluxo de retirada;
- Fluxo de devolução;
- Regras de quilometragem;
- Histórico de aluguéis;
- Estruturas de dados utilizadas;
- Telas de retirada e devolução;
- Parte correspondente do diagrama UML.
