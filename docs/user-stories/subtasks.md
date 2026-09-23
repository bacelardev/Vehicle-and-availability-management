# Subtarefas das User Stories

## US01 — Cadastrar veículo

**Responsável:** Guilherme Pereira Bacelar

### Subtarefas

* [ ] Criar a estrutura da entidade `Veiculo`.
* [ ] Definir os atributos do veículo.
* [ ] Criar método para cadastrar um novo veículo.
* [ ] Implementar validação dos dados obrigatórios.
* [ ] Implementar validação para evitar veículos duplicados.
* [ ] Criar formulário de cadastro no frontend.
* [ ] Implementar integração entre frontend e backend.
* [ ] Persistir os dados do veículo no banco de dados.
* [ ] Exibir mensagem de confirmação após o cadastro.
* [ ] Testar o cadastro com dados válidos e inválidos.

---

## US02 — Consultar e organizar veículos

**Responsável:** Guilherme Pereira Bacelar

### Subtarefas

* [ ] Criar método para listar os veículos cadastrados.
* [ ] Implementar consulta de veículo por identificador.
* [ ] Implementar busca por características do veículo.
* [ ] Implementar organização/ordenação dos veículos.
* [ ] Criar tela de listagem dos veículos.
* [ ] Exibir o status atual de cada veículo.
* [ ] Implementar filtros para facilitar a consulta.
* [ ] Implementar atualização dos dados do veículo.
* [ ] Implementar exclusão de veículo quando permitida pelas regras do sistema.
* [ ] Testar as funcionalidades de consulta e organização.

---

## US03 — Consultar disponibilidade

**Responsável:** Henrique Barbosa Ferrão

### Subtarefas

* [ ] Definir os estados de disponibilidade do veículo.
* [ ] Criar método para verificar a disponibilidade.
* [ ] Verificar conflitos com reservas existentes.
* [ ] Verificar veículos que estão em manutenção.
* [ ] Criar tela de consulta de disponibilidade.
* [ ] Exibir os veículos disponíveis.
* [ ] Implementar filtros por período.
* [ ] Implementar filtros por características do veículo.
* [ ] Garantir que veículos indisponíveis não sejam apresentados como disponíveis.
* [ ] Testar diferentes cenários de disponibilidade.

---

## US04 — Realizar reserva

**Responsável:** Henrique Barbosa Ferrão

### Subtarefas

* [ ] Criar a estrutura da entidade `Reserva`.
* [ ] Definir os dados necessários para uma reserva.
* [ ] Criar método para registrar uma reserva.
* [ ] Validar os dados informados pelo usuário.
* [ ] Verificar a disponibilidade antes de confirmar a reserva.
* [ ] Impedir reservas conflitantes para o mesmo veículo.
* [ ] Definir os estados da reserva.
* [ ] Criar tela de realização de reserva.
* [ ] Implementar cancelamento de reserva.
* [ ] Exibir confirmação da reserva.
* [ ] Testar criação, alteração e cancelamento de reservas.

---

## US05 — Registrar retirada do veículo

**Responsável:** Rodolfo Gonçalves

### Subtarefas

* [ ] Criar estrutura para registrar a retirada.
* [ ] Identificar o veículo relacionado à retirada.
* [ ] Identificar a reserva relacionada, quando existir.
* [ ] Registrar data e horário da retirada.
* [ ] Atualizar o status do veículo.
* [ ] Validar se o veículo está disponível para retirada.
* [ ] Impedir retirada de veículo indisponível.
* [ ] Criar tela para registro da retirada.
* [ ] Registrar o início do período de utilização.
* [ ] Testar os cenários de retirada.

---

## US06 — Registrar devolução do veículo

**Responsável:** Rodolfo Gonçalves

### Subtarefas

* [ ] Criar estrutura para registrar a devolução.
* [ ] Identificar o veículo em utilização.
* [ ] Registrar data e horário da devolução.
* [ ] Atualizar o status do veículo.
* [ ] Registrar informações relacionadas à devolução.
* [ ] Verificar se houve atraso na devolução.
* [ ] Encaminhar o veículo para manutenção quando necessário.
* [ ] Liberar o veículo para novas reservas quando estiver disponível.
* [ ] Criar tela para registro da devolução.
* [ ] Testar os cenários de devolução.

---

## US07 — Bloquear veículo para manutenção

**Responsável:** Mateus Oliveira de Araujo

### Subtarefas

* [ ] Definir os estados relacionados à manutenção.
* [ ] Criar método para bloquear um veículo.
* [ ] Registrar o motivo do bloqueio.
* [ ] Registrar data de início da manutenção.
* [ ] Impedir reservas para veículos bloqueados.
* [ ] Atualizar o status do veículo.
* [ ] Criar tela de bloqueio para manutenção.
* [ ] Permitir a liberação do veículo após a manutenção.
* [ ] Registrar a data de conclusão da manutenção.
* [ ] Testar bloqueio e liberação do veículo.

---

## US08 — Registrar necessidade de manutenção

**Responsável:** Mateus Oliveira de Araujo

### Subtarefas

* [ ] Criar estrutura para registrar solicitações de manutenção.
* [ ] Registrar o veículo que necessita de manutenção.
* [ ] Registrar o problema identificado.
* [ ] Definir prioridade da manutenção.
* [ ] Implementar fila de manutenção.
* [ ] Implementar fila de prioridade para situações urgentes.
* [ ] Organizar as prioridades entre alta, média e baixa.
* [ ] Criar tela de gerenciamento das manutenções.
* [ ] Atualizar o status da manutenção.
* [ ] Registrar a conclusão do serviço.
* [ ] Manter histórico das manutenções realizadas.
* [ ] Testar o fluxo completo de manutenção.

---

## US09 — Gerenciar perfis de acesso

**Responsável:** Mateus Rodrigues França de Almeida

### Subtarefas

* [ ] Criar estrutura da entidade `Usuario`.
* [ ] Definir os tipos de usuário do sistema.
* [ ] Criar cadastro de usuários.
* [ ] Implementar autenticação.
* [ ] Implementar controle de acesso por perfil.
* [ ] Definir permissões para cada perfil.
* [ ] Restringir funcionalidades conforme as permissões.
* [ ] Criar tela de gerenciamento de usuários.
* [ ] Implementar alteração de dados do usuário.
* [ ] Implementar desativação de usuários.
* [ ] Testar acesso com diferentes perfis.

---

## US10 — Visualizar dashboard

**Responsável:** Mateus Rodrigues França de Almeida

### Subtarefas

* [ ] Definir as informações que serão apresentadas no dashboard.
* [ ] Criar indicadores de veículos cadastrados.
* [ ] Criar indicador de veículos disponíveis.
* [ ] Criar indicador de veículos reservados.
* [ ] Criar indicador de veículos em manutenção.
* [ ] Criar indicador de reservas realizadas.
* [ ] Criar gráficos ou componentes de visualização dos dados.
* [ ] Criar layout do dashboard.
* [ ] Integrar o dashboard com os dados do sistema.
* [ ] Atualizar os indicadores conforme as alterações realizadas.
* [ ] Garantir que cada usuário visualize apenas as informações permitidas pelo seu perfil.
* [ ] Testar a apresentação e atualização dos dados.
