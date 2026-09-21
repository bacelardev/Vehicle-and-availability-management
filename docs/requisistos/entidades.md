# Entidades

## Veículo

Representa um veículo pertencente à frota.

### Atributos

- placa
- modelo
- marca
- ano
- categoria
- valorDiaria
- capacidadePassageiros
- quilometragemAtual
- status

## Reserva

Representa a reserva de um veículo para determinado cliente e período.

### Atributos

- id
- cliente
- veiculo
- dataInicio
- dataFim
- status

## Aluguel

Representa a utilização efetiva do veículo.

### Atributos

- id
- cliente
- veiculo
- dataRetirada
- dataDevolucao
- kmRetirada
- kmDevolucao

## Manutenção

Representa um período em que o veículo ficará indisponível.

### Atributos

- id
- veiculo
- dataInicio
- dataFim
- motivo