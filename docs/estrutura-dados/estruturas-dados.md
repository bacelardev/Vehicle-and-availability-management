# Estruturas de Dados

As estruturas de dados foram escolhidas de acordo com o comportamento
esperado das funcionalidades do sistema de Controle de Veículos e
Disponibilidade.

| Estrutura de Dados | Aplicação no Sistema | Elementos Armazenados | Operações Principais | Justificativa |
|---|---|---|---|---|
| **Lista** | Cadastro e gerenciamento da frota | Veículos | Inserção, busca, atualização, remoção e percorrimento | A quantidade de veículos pode variar e é necessário realizar consultas e operações sobre os elementos da frota. |
| **Lista** | Controle de reservas | Reservas | Inserção, busca, atualização, remoção e consulta por período | Permite armazenar as reservas e percorrê-las para verificar a disponibilidade e possíveis conflitos de datas. |
| **Lista** | Histórico de aluguéis | Aluguéis finalizados | Inserção, busca e percorrimento | Permite manter os registros dos aluguéis realizados para consultas posteriores. |
| **Fila** | Controle de veículos aguardando manutenção | Veículos que necessitam de manutenção | Enfileirar, desenfileirar, consultar primeiro elemento e verificar se está vazia | O primeiro veículo a entrar na fila é o primeiro a ser atendido, seguindo o princípio FIFO. |
| **Fila de Prioridade** | Priorização de manutenções | Veículos com diferentes níveis de necessidade | Inserção com prioridade, consulta do maior nível de prioridade e remoção do elemento prioritário | Permite atender primeiro os veículos cuja manutenção apresenta maior urgência. |

## Prioridade de Manutenção

| Prioridade | Situação |
|---|---|
| **Alta** | Quilometragem ultrapassou o limite de manutenção ou apresenta necessidade urgente. |
| **Média** | Veículo está próximo do limite de manutenção. |
| **Baixa** | Manutenção preventiva sem urgência. |

## Princípios Utilizados

### Lista

Utilizada para armazenar conjuntos de elementos que precisam ser
percorridos e manipulados durante o funcionamento do sistema.

### Fila

Utiliza o princípio **FIFO (First In, First Out)**. O primeiro veículo
a entrar na fila de manutenção será o primeiro a ser atendido.

### Fila de Prioridade

Diferentemente da fila convencional, o atendimento considera o nível
de prioridade da manutenção. Assim, um veículo com manutenção urgente
pode ser atendido antes de outro que entrou anteriormente na fila.

## Relação com as Funcionalidades

- **Veículos:** Lista.
- **Reservas:** Lista.
- **Aluguéis:** Lista.
- **Manutenção:** Fila.
- **Manutenção prioritária:** Fila de Prioridade.
