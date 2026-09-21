# Regras de Negócio

## RN01 - Placa única

Não podem existir dois veículos cadastrados com a mesma placa.

## RN02 - Reserva sobreposta

Um veículo não pode possuir duas reservas em períodos que se sobreponham.

## RN03 - Manutenção

Um veículo em manutenção não pode ser reservado ou alugado.

## RN04 - Quilometragem

A quilometragem registrada na devolução deve ser maior ou igual à
quilometragem registrada na retirada.

## RN05 - Status

O veículo deve possuir um dos seguintes status:

- DISPONIVEL
- RESERVADO
- ALUGADO
- MANUTENCAO

## RN06 - Capacidade

A quantidade de passageiros não pode ultrapassar a capacidade máxima
do veículo.