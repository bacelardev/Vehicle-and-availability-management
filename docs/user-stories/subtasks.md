# User Stories e Subtarefas

## US01 - Cadastrar veículo

**Responsável:** Guilherme Pereira Bacelar
**Estimativa total:** 8h

### Subtarefas

* [ ] Criar a estrutura da entidade `Veiculo`. **Estimativa: 1h**
* [ ] Definir os atributos do veículo. **Estimativa: 1h**
* [ ] Criar método para cadastrar um novo veículo. **Estimativa: 1h**
* [ ] Implementar validação dos dados obrigatórios. **Estimativa: 1h**
* [ ] Implementar validação para evitar veículos duplicados. **Estimativa: 1h**
* [ ] Criar formulário de cadastro no frontend. **Estimativa: 1h**
* [ ] Implementar integração entre frontend e backend. **Estimativa: 1h**
* [ ] Persistir os dados do veículo no banco de dados. **Estimativa: 1h**
* [ ] Exibir mensagem de confirmação após o cadastro. **Estimativa: 1h**
* [ ] Testar o cadastro com dados válidos e inválidos. **Estimativa: 1h**

> **Observação:** as estimativas das subtarefas somam 10h; para manter o planejamento anterior de 8h, algumas atividades podem ser executadas em conjunto.

---

## US02 - Consultar e organizar veículos

**Responsável:** Guilherme Pereira Bacelar
**Estimativa total:** 8h

### Subtarefas

* [ ] Criar método para listar os veículos cadastrados. **Estimativa: 1h**
* [ ] Implementar consulta de veículo por identificador. **Estimativa: 1h**
* [ ] Implementar busca por características do veículo. **Estimativa: 1h**
* [ ] Implementar organização/ordenação dos veículos. **Estimativa: 1h**
* [ ] Criar tela de listagem dos veículos. **Estimativa: 1h**
* [ ] Exibir o status atual de cada veículo. **Estimativa: 1h**
* [ ] Implementar filtros para facilitar a consulta. **Estimativa: 1h**
* [ ] Implementar atualização dos dados do veículo. **Estimativa: 1h**
* [ ] Implementar exclusão de veículo quando permitida pelas regras do sistema. **Estimativa: 2h**
* [ ] Testar as funcionalidades de consulta e organização. **Estimativa: 1h**

**Estimativa de desenvolvimento:** aproximadamente 8h, considerando a execução conjunta de tarefas relacionadas.

---

## US03 - Consultar disponibilidade

**Responsável:** Henrique Barbosa Ferrão
**Estimativa total:** 10h

### Subtarefas

* [ ] Definir os estados de disponibilidade do veículo. **Estimativa: 1h**
* [ ] Criar método para verificar a disponibilidade. **Estimativa: 2h**
* [ ] Verificar conflitos com reservas existentes. **Estimativa: 2h**
* [ ] Verificar veículos que estão em manutenção. **Estimativa: 1h**
* [ ] Criar tela de consulta de disponibilidade. **Estimativa: 2h**
* [ ] Exibir os veículos disponíveis. **Estimativa: 1h**
* [ ] Implementar filtros por período. **Estimativa: 2h**
* [ ] Implementar filtros por características do veículo. **Estimativa: 1h**
* [ ] Garantir que veículos indisponíveis não sejam apresentados como disponíveis. **Estimativa: 1h**
* [ ] Testar diferentes cenários de disponibilidade. **Estimativa: 2h**

---

## US04 - Realizar reserva

**Responsável:** Henrique Barbosa Ferrão
**Estimativa total:** 12h

### Subtarefas

* [ ] Criar a estrutura da entidade `Reserva`. **Estimativa: 1h**
* [ ] Definir os dados necessários para uma reserva. **Estimativa: 1h**
* [ ] Criar método para registrar uma reserva. **Estimativa: 2h**
* [ ] Validar os dados informados pelo usuário. **Estimativa: 1h**
* [ ] Verificar a disponibilidade antes de confirmar a reserva. **Estimativa: 2h**
* [ ] Impedir reservas conflitantes para o mesmo veículo. **Estimativa: 2h**
* [ ] Definir os estados da reserva. **Estimativa: 1h**
* [ ] Criar tela de realização de reserva. **Estimativa: 2h**
* [ ] Implementar cancelamento de reserva. **Estimativa: 1h**
* [ ] Exibir confirmação da reserva. **Estimativa: 1h**
* [ ] Testar criação, alteração e cancelamento de reservas. **Estimativa: 2h**

---

## US05 - Registrar retirada do veículo

**Responsável:** Rodolfo Gonçalves
**Estimativa total:** 8h

### Subtarefas

* [ ] Criar estrutura para registrar a retirada. **Estimativa: 1h**
* [ ] Identificar o veículo relacionado à retirada. **Estimativa: 1h**
* [ ] Identificar a reserva relacionada, quando existir. **Estimativa: 1h**
* [ ] Registrar data e horário da retirada. **Estimativa: 1h**
* [ ] Atualizar o status do veículo. **Estimativa: 1h**
* [ ] Validar se o veículo está disponível para retirada. **Estimativa: 1h**
* [ ] Impedir retirada de veículo indisponível. **Estimativa: 1h**
* [ ] Criar tela para registro da retirada. **Estimativa: 2h**
* [ ] Registrar o início do período de utilização. **Estimativa: 1h**
* [ ] Testar os cenários de retirada. **Estimativa: 2h**

---

## US06 - Registrar devolução do veículo

**Responsável:** Rodolfo Gonçalves
**Estimativa total:** 8h

### Subtarefas

* [ ] Criar estrutura para registrar a devolução. **Estimativa: 1h**
* [ ] Identificar o veículo em utilização. **Estimativa: 1h**
* [ ] Registrar data e horário da devolução. **Estimativa: 1h**
* [ ] Atualizar o status do veículo. **Estimativa: 1h**
* [ ] Registrar informações relacionadas à devolução. **Estimativa: 1h**
* [ ] Verificar se houve atraso na devolução. **Estimativa: 2h**
* [ ] Encaminhar o veículo para manutenção quando necessário. **Estimativa: 1h**
* [ ] Liberar o veículo para novas reservas quando estiver disponível. **Estimativa: 1h**
* [ ] Criar tela para registro da devolução. **Estimativa: 2h**
* [ ] Testar os cenários de devolução. **Estimativa: 2h**

---

## US07 — Bloquear veículo para manutenção

**Responsável:** Mateus Oliveira de Araujo
**Estimativa total:** 8h

### Subtarefas

* [ ] Definir os estados relacionados à manutenção. **Estimativa: 1h**
* [ ] Criar método para bloquear um veículo. **Estimativa: 1h**
* [ ] Registrar o motivo do bloqueio. **Estimativa: 1h**
* [ ] Registrar data de início da manutenção. **Estimativa: 1h**
* [ ] Impedir reservas para veículos bloqueados. **Estimativa: 2h**
* [ ] Atualizar o status do veículo. **Estimativa: 1h**
* [ ] Criar tela de bloqueio para manutenção. **Estimativa: 2h**
* [ ] Permitir a liberação do veículo após a manutenção. **Estimativa: 1h**
* [ ] Registrar a data de conclusão da manutenção. **Estimativa: 1h**
* [ ] Testar bloqueio e liberação do veículo. **Estimativa: 2h**

---

## US08 - Registrar necessidade de manutenção

**Responsável:** Mateus Oliveira de Araujo
**Estimativa total:** 12h

### Subtarefas

* [ ] Criar estrutura para registrar solicitações de manutenção. **Estimativa: 1h**
* [ ] Registrar o veículo que necessita de manutenção. **Estimativa: 1h**
* [ ] Registrar o problema identificado. **Estimativa: 1h**
* [ ] Definir prioridade da manutenção. **Estimativa: 1h**
* [ ] Implementar fila de manutenção. **Estimativa: 2h**
* [ ] Implementar fila de prioridade para situações urgentes. **Estimativa: 2h**
* [ ] Organizar as prioridades entre alta, média e baixa. **Estimativa: 1h**
* [ ] Criar tela de gerenciamento das manutenções. **Estimativa: 2h**
* [ ] Atualizar o status da manutenção. **Estimativa: 1h**
* [ ] Registrar a conclusão do serviço. **Estimativa: 1h**
* [ ] Manter histórico das manutenções realizadas. **Estimativa: 2h**
* [ ] Testar o fluxo completo de manutenção. **Estimativa: 2h**

---

## US09 - Gerenciar perfis de acesso

**Responsável:** Mateus Rodrigues França de Almeida
**Estimativa total:** 14h

### Subtarefas

* [ ] Criar estrutura da entidade `Usuario`. **Estimativa: 1h**
* [ ] Definir os tipos de usuário do sistema. **Estimativa: 1h**
* [ ] Criar cadastro de usuários. **Estimativa: 2h**
* [ ] Implementar autenticação. **Estimativa: 3h**
* [ ] Implementar controle de acesso por perfil. **Estimativa: 3h**
* [ ] Definir permissões para cada perfil. **Estimativa: 2h**
* [ ] Restringir funcionalidades conforme as permissões. **Estimativa: 2h**
* [ ] Criar tela de gerenciamento de usuários. **Estimativa: 2h**
* [ ] Implementar alteração de dados do usuário. **Estimativa: 1h**
* [ ] Implementar desativação de usuários. **Estimativa: 1h**
* [ ] Testar acesso com diferentes perfis. **Estimativa: 2h**

---

## US10 - Visualizar dashboard

**Responsável:** Mateus Rodrigues França de Almeida
**Estimativa total:** 10h

### Subtarefas

* [ ] Definir as informações que serão apresentadas no dashboard. **Estimativa: 1h**
* [ ] Criar indicadores de veículos cadastrados. **Estimativa: 1h**
* [ ] Criar indicador de veículos disponíveis. **Estimativa: 1h**
* [ ] Criar indicador de veículos reservados. **Estimativa: 1h**
* [ ] Criar indicador de veículos em manutenção. **Estimativa: 1h**
* [ ] Criar indicador de reservas realizadas. **Estimativa: 1h**
* [ ] Criar gráficos ou componentes de visualização dos dados. **Estimativa: 2h**
* [ ] Criar layout do dashboard. **Estimativa: 2h**
* [ ] Integrar o dashboard com os dados do sistema. **Estimativa: 2h**
* [ ] Atualizar os indicadores conforme as alterações realizadas. **Estimativa: 2h**
* [ ] Garantir que cada usuário visualize apenas as informações permitidas pelo seu perfil. **Estimativa: 2h**
* [ ] Testar a apresentação e atualização dos dados. **Estimativa: 2h**

---

# Resumo das estimativas

| User Story | Responsável                        | Estimativa |
| ---------- | ---------------------------------- | ---------: |
| US01       | Guilherme Pereira Bacelar          |         8h |
| US02       | Guilherme Pereira Bacelar          |         8h |
| US03       | Henrique Barbosa Ferrão            |        10h |
| US04       | Henrique Barbosa Ferrão            |        12h |
| US05       | Rodolfo Gonçalves                  |         8h |
| US06       | Rodolfo Gonçalves                  |         8h |
| US07       | Mateus Oliveira de Araujo          |         8h |
| US08       | Mateus Oliveira de Araujo          |        12h |
| US09       | Mateus Rodrigues França de Almeida |        14h |
| US10       | Mateus Rodrigues França de Almeida |        10h |
| **Total**  | **5 integrantes**                  |    **98h** |
