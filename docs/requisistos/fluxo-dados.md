# Fluxo de Dados

## Cadastro

1. O administrador informa os dados do veículo.
2. O sistema verifica se a placa já existe.
3. Caso não exista, o veículo é inserido na estrutura de dados.
4. O veículo é iniciado com status DISPONIVEL.

## Busca

O usuário poderá buscar veículos por:

- placa;
- modelo;
- categoria.

## Reserva

1. O atendente informa o período desejado.
2. O sistema verifica as reservas existentes.
3. O sistema verifica períodos de manutenção.
4. Se não existir conflito, a reserva é criada.

## Retirada

1. A reserva é localizada.
2. A quilometragem atual é registrada.
3. O veículo recebe status ALUGADO.

## Devolução

1. A quilometragem final é registrada.
2. O sistema calcula:

quilômetros percorridos = kmDevolucao - kmRetirada

3. O histórico de aluguel é atualizado.
4. O sistema verifica necessidade de manutenção.
5. Se não houver manutenção pendente, o veículo volta para DISPONIVEL.