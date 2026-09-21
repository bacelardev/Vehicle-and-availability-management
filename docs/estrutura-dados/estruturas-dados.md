# Estruturas de Dados

## Lista de Veículos

Será utilizada para armazenar os veículos cadastrados na frota.

Operações principais:

- inserir;
- remover;
- buscar;
- percorrer;
- ordenar.

## Lista de Reservas

Armazena as reservas existentes e permite verificar conflitos entre
intervalos de datas.

## Lista de Aluguéis

Mantém o histórico de aluguéis realizados.

## Fila de Manutenção

Pode ser utilizada para organizar veículos que aguardam manutenção.

Princípio utilizado:

FIFO - First In, First Out.

## Fila de Prioridade de Manutenção

Pode ser utilizada quando veículos com maior necessidade de manutenção
precisarem ser atendidos primeiro.

Exemplo de prioridade:

1. Quilometragem muito acima do limite.
2. Quilometragem próxima do limite.
3. Manutenção preventiva comum.