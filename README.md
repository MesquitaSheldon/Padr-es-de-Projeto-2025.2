# Framework de Gestão de Documentos (Avaliação III)

**Disciplina:** INF011 - Padrões de Projeto  
**Professor:** Frederico Barboza  
**Semestre:** 2025.2  

## 👤 Participante
* **Nome:** Sheldon Mesquita Maia de Carvalho

---

## 📝 Descrição do Projeto
Este projeto consiste na evolução de um framework de gestão de documentos eletrônicos, implementando requisitos avançados de comportamento e estrutura. O objetivo principal foi aplicar padrões de projeto para resolver problemas de **Autenticação Flexível**, **Histórico de Operações (Undo/Redo)**, **Automação (Macros)** e **Persistência de Logs**.

Todas as funcionalidades implementadas seguem estritamente as especificações da Avaliação III.

---

## 🏗️ Padrões de Projeto Aplicados

### 1. Strategy (Questão I - Autenticação)
Utilizado para permitir a variação do algoritmo de numeração de protocolo sem alterar a classe do Autenticador.

* **Justificativa:** O sistema exigia regras diferentes de formatação baseadas no tipo do documento (Criminal, Pessoal, etc.). O padrão Strategy eliminou as condicionalidades (`if/else`) do código original, delegando a responsabilidade da geração do número para classes específicas que implementam uma interface comum (`NumberRule`).
* **Classes Participantes:**
    * **Context:** `AutenticadorQ1`.
    * **Strategy Interface:** `NumberRule`.
    * **Concrete Strategies:** `DocTypeCriminal`, `DocTypePessoal`, `DocTypePubSig`, `DocTypeDocumento`.

### 2. Command (Questão II - Undo/Redo)
Utilizado para encapsular todas as operações do sistema (Criar, Salvar, Assinar, Proteger, Tornar Urgente) em objetos independentes.

* **Justificativa:** Necessário para atender ao requisito de desfazer e refazer ações. O padrão permite armazenar o histórico de execuções em pilhas (`Stack`), possibilitando a reversão de estado do `GerenciadorDocumentoModel` e a reimplementação de ações desfeitas.
* **Classes Participantes:**
    * **Invoker:** `CommandManager` (Gerencia as pilhas e o log em arquivo).
    * **Command Interface:** `Comand`.
    * **Concrete Commands:** `CreateDoc`, `SalvarDoc`, `AssignCommand`, `ProtectDoc`, `UrgentDoc`.
    * **Receiver:** `GerenciadorDocumentoModel`.

### 3. Composite (Questão II - Macros)
Utilizado para compor comandos simples em estruturas complexas (Macros), permitindo que uma sequência de ações seja tratada como um único comando.

* **Justificativa:** Atende ao requisito de "Ações Rápidas" e gravação de Macros. O Composite permite que o `CommandManager` trate um grupo de comandos (ex: "Criar + Salvar + Assinar") da mesma forma que trata um comando simples, facilitando a execução e o undo em bloco.
* **Classes Participantes:**
    * **Composite:** `MacroCommand` (Contém uma lista de `Comand`).
    * **Leaf:** Comandos concretos (`SalvarDoc`, `AssignCommand`, etc.).

### 4. Prototype (Suporte a Macros)
Utilizado para permitir a clonagem de comandos durante a execução de macros.

* **Justificativa:** Fundamental para o funcionamento correto das Macros. Quando uma macro é gravada, ela guarda um "molde" do comando. Ao executar a macro, o padrão Prototype (`clone()`) cria novas instâncias desses comandos, garantindo que novos documentos sejam criados com IDs únicos a cada execução, em vez de reciclar o mesmo objeto da gravação.
* **Classes Participantes:**
    * **Prototype Interface:** `Comand` (estende `Cloneable`).
    * **Concrete Prototypes:** Todas as implementações de comandos.

### 5. Decorator (Estrutura Base)
Utilizado para adicionar responsabilidades dinâmicas (Assinatura, Urgência, Proteção) aos documentos.

* **Justificativa:** Permite adicionar comportamentos e estados ao objeto documento em tempo de execução sem alterar sua estrutura básica ou criar uma explosão de subclasses.
* **Classes Participantes:**
    * **Component:** `Documento`.
    * **Decorator:** `DocumentoDecorator` e seus filhos (`AssinaturaDecorator`, etc.).

---

## ⚙️ Detalhes de Implementação

* **Log em Arquivo:** Conforme solicitado, o sistema registra todas as operações (execução, undo, redo, consolidação) em um arquivo físico (`log_operacoes.txt`) utilizando `FileWriter`.
* **Consolidação:** Funcionalidade que limpa as pilhas de histórico, impedindo que alterações antigas sejam desfeitas após um ponto de controle.
* **Gravação de Macros:** A interface permite iniciar e parar a gravação de ações, criando dinamicamente um `MacroCommand` que é adicionado à pilha de execução.

## 📦 Como executar
1.  Importe o projeto na sua IDE de preferência.
2.  Execute a classe principal `AppAvaliacaoIII`.
3.  Utilize a interface gráfica para criar documentos, aplicar operações e testar os botões de **Undo**, **Redo**, **Macro** e **Consolidar**.
