# 🍝 O Jantar dos Filósofos - Programação Paralela

Este repositório contém as soluções desenvolvidas para a Avaliação Final de Programação Paralela e Distribuída. O projeto explora problemas clássicos de concorrência, focando na resolução do problema do Jantar dos Filósofos através de diferentes primitivas de sincronização.

## 📂 Estrutura do Projeto

A organização dos arquivos segue a lógica das tarefas propostas:

* `src/tarefa1`: **Implementação Ingênua** (Demonstração de Deadlock).
* `src/tarefa2`: **Solução com Hierarquia** (Quebra de simetria / Resource Hierarchy).
* `src/tarefa3`: **Solução com Semáforos** (Controle de acesso / Multiplex).
* `src/tarefa4`: **Solução com Monitores** (Abstração de alto nível e Fairness).
* `test/`: Testes unitários para validar a lógica de cada solução.
* `RELATORIO.md`: Análise detalhada, métricas de desempenho e gráficos comparativos.

---

## 🚀 Como Compilar e Executar

### Pré-requisitos
* **Java JDK 8** ou superior instalado e configurado no PATH.
* Sistema Operacional: Os comandos abaixo seguem a sintaxe Windows (PowerShell/CMD). Para Linux/Mac, substitua `\` por `/` e `;` por `:`.

---

## 📝 Detalhes das Tarefas

Abaixo estão os detalhes de implementação e execução para cada abordagem.

### 💀 Tarefa 1: A Solução Ingênua (Deadlock)
Esta versão implementa a solução clássica onde cada filósofo é uma Thread em loop infinito (Pensar → Comer). A exclusão mútua dos garfos é garantida através da palavra-chave `synchronized`.

* **Objetivo:** Demonstrar a ocorrência de *Deadlock* em uma execução de pelo menos 30 segundos.
* **Logs:** O sistema registra no console os estados: Pensando, Tentando pegar garfos, Comendo e Soltando garfos.

**Por que ocorre Deadlock?**
> A implementação sofre de **Hold and Wait** (Segurar e Esperar) com dependência circular.
> 1. Todos os 5 filósofos podem tentar comer simultaneamente.
> 2. Cada um bloqueia (`synchronized`) o garfo à sua esquerda com sucesso.
> 3. Em seguida, todos tentam bloquear o garfo à sua direita.
> 4. Como o garfo da direita de um filósofo é o da esquerda do vizinho (que já está bloqueado), todos ficam travados esperando infinitamente um recurso que nunca será liberado.

**Execução:**
```bash
java .\src\tarefa1\Main.java
# Nota: Execute por aprox. 30 segundos. O programa deve travar (Deadlock).
# Pressione Ctrl+C para encerrar.
```

### 🔢 Tarefa 2: Hierarquia de Recursos (Quebra de Simetria)
Esta solução modifica a implementação da Tarefa 1 introduzindo uma assimetria na aquisição dos garfos para evitar a espera circular.

* **Lógica Alterada:**
    * Filósofos 0 a 3: Pegam primeiro o garfo **Esquerdo**, depois o **Direito**.
    * Filósofo 4: Pega primeiro o garfo **Direito**, depois o **Esquerdo**.
* **Objetivo:** Executar por pelo menos 2 minutos sem travamentos e coletar estatísticas de quantas vezes cada um comeu.

**Análise da Solução:**
> **1. Por que previne Deadlock?**
> A mudança na ordem de aquisição do último filósofo quebra a condição de "Espera Circular". Se todos tentarem comer ao mesmo tempo, os filósofos 0 a 3 pegarão seus garfos esquerdos. Porém, o filósofo 4 tentará pegar seu garfo *direito* (que já está sendo disputado ou segurado pelo filósofo 0). Isso força uma competição pelo primeiro recurso, impedindo que todos fiquem segurando um garfo e esperando pelo próximo indefinidamente.
>
> **2. Ainda existe Starvation?**
> **Sim.** Embora o Deadlock seja resolvido, esta solução não garante justiça (*Fairness*). Se os vizinhos de um filósofo forem muito rápidos e alternarem seus turnos de comer perfeitamente, o filósofo do meio pode nunca conseguir obter os dois garfos simultaneamente, resultando em inanição.
>
> **3. Comparação com Tarefa 1:**
> Ao contrário da Tarefa 1, que trava (Deadlock) em poucos segundos/minutos, esta solução mantém o sistema rodando continuamente por longos períodos (testado por > 2 min).

**Estatísticas de Execução (2 minutos):**
* Filósofo 0 comeu: 21 vezes
* Filósofo 1 comeu: 22 vezes
* Filósofo 2 comeu: 23 vezes
* Filósofo 3 comeu: 24 vezes
* Filósofo 4 comeu: 21 vezes

**Execução:**
```bash
java .\src\tarefa2\Main.java
# Execute por 2 minutos. O programa NÃO deve travar.
```

### 🚦 Tarefa 3: Solução com Semáforos (Multiplex)
Esta implementação utiliza a classe `java.util.concurrent.Semaphore` para controlar o acesso aos recursos. A estratégia adotada limita o número de filósofos que podem tentar comer simultaneamente.

* **Estratégia (Multiplex):** Um semáforo "porteiro" é inicializado com **4 permissões**.
* **Lógica:**
    1. Antes de tentar pegar qualquer garfo, o filósofo deve adquirir uma permissão do semáforo (`acquire()`).
    2. Se conseguir, ele entra na disputa pelos garfos (sincronizados).
    3. Após comer e soltar os garfos, ele libera a permissão (`release()`).
* **Objetivo:** Rodar por 2 minutos e comparar o desempenho com a Tarefa 2.

**Análise da Solução:**
> **1. Por que previne Deadlock?**
> A causa raiz do deadlock é o ciclo onde 5 filósofos seguram 5 garfos esquerdos. Ao limitar o acesso à mesa a apenas 4 filósofos, garantimos que, pelo Princípio da Casa dos Pombos, pelo menos um filósofo conseguirá pegar o garfo da direita (pois sobram 5 garfos para 4 pessoas). Isso quebra a condição de espera circular.
>
> **2. Vantagens e Desvantagens:**
> * **Vantagem:** Implementação limpa e menos propensa a erros complexos de lógica (o Java gerencia a fila do semáforo). Resolve o deadlock garantido.
> * **Desvantagem:** Introduz um limite artificial de concorrência. Embora no caso de 5 filósofos o máximo de pessoas comendo seja 2, em mesas maiores isso poderia reduzir o throughput se o limite for mal calculado.
>
> **3. Comparação com Tarefa 2:**
**Estatísticas de Execução (2 minutos):**
* Filósofo 0 comeu: 23 vezes
* Filósofo 1 comeu: 22 vezes
* Filósofo 2 comeu: 23 vezes
* Filósofo 3 comeu: 22 vezes
* Filósofo 4 comeu: 22 vezes

**Execução:**
```bash
java .\src\tarefa3\Main.java
# Execute por 2 minutos.
```
### 🛡️ Tarefa 4: Solução com Monitores (Mesa Centralizada e Fairness)
Nesta abordagem, a complexidade de sincronização é removida dos filósofos e encapsulada em uma classe `Mesa` (Monitor). Os filósofos apenas solicitam permissão para comer e avisam quando terminaram.

* **Componente Central:** Classe `Mesa` com métodos `synchronized`.
* **Mecanismo:** Uso de `wait()` para suspender filósofos famintos e `notifyAll()` para acordá-los quando garfos são liberados.
* **Diferencial:** Implementação de um sistema de fila ou prioridade para garantir que filósofos que estão esperando há mais tempo tenham preferência (Fairness).

**Análise da Solução:**
> **1. Como Deadlock é prevenido?**
> O Monitor torna a ação de pegar garfos atômica em relação ao estado da mesa. Não existe a situação de "Segurar e Esperar" (Hold and Wait) parcial. Ou o filósofo consegue **ambos** os garfos de uma vez (porque o monitor verificou o estado global), ou ele entra em espera (`wait`) sem segurar nenhum recurso.
>
> **2. Como Starvation é prevenido (Fairness)?**
> Diferente das soluções anteriores, o Monitor possui controle total sobre quem come. A implementação inclui uma lógica de prioridade (ex: Fila FIFO ou verificação de estado `FAMINTO` dos vizinhos) que impede que dois filósofos rápidos conspirem para deixar o filósofo do meio sem comer indefinidamente.
>
> **3. Trade-offs e Comparação:**
> * **Vantagens:** É a solução mais robusta e "justa". O código do filósofo fica limpo, pois ele não precisa saber sobre semáforos ou IDs de garfos.
> * **Desvantagens:** Pode haver um leve overhead de desempenho devido ao uso de `notifyAll()` (que acorda todas as threads para verificar o estado, conhecido como *Thundering Herd*), embora em um cenário com apenas 5 filósofos isso seja imperceptível.
> * **Desempenho:** 
**Estatísticas de Execução (2 minutos):**
* Filósofo 0 comeu: 23 vezes
* Filósofo 1 comeu: 21 vezes
* Filósofo 2 comeu: 24 vezes
* Filósofo 3 comeu: 24 vezes
* Filósofo 4 comeu: 24 vezes

**Execução:**
```bash
java .\src\tarefa4\Main.java
# Execute por 2 minutos. Observe a distribuição equilibrada das refeições.
```
Rodar testes individuais:

``` Bash

# Teste Tarefa 1 (Verificação de falha/deadlock)
java -cp "src;test" .\test\TesteTarefa1.java

# Teste Tarefa 2 (Hierarquia)
java -cp "src;test" .\test\TesteTarefa2.java

# Teste Tarefa 3 (Semáforos)
java -cp "src;test" .\test\TesteTarefa3.java

# Teste Tarefa 4 (Monitores)
java -cp "src;test" .\test\TesteTarefa4.java
```
### 📊 Relatório Final
Para uma análise aprofundada sobre:

# Comparação de desempenho (tempo de execução vs. throughput).

# Justificativa teórica para a prevenção de Deadlocks.

# Gráficos de utilização de CPU/Threads.

Consulte o arquivo ./RELATORIO.md.
