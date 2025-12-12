# 📊 Relatório Comparativo: O Jantar dos Filósofos

Este documento apresenta a análise de desempenho, justiça e corretude das soluções implementadas para o problema clássico de concorrência "O Jantar dos Filósofos".

---

## 1. Introdução

O problema do Jantar dos Filósofos, proposto por Edsger Dijkstra, ilustra os desafios da sincronização em sistemas operacionais e processamento paralelo. Cinco processos (filósofos) competem por recursos compartilhados limitados (garfos) para realizar seu trabalho (comer).

O objetivo deste estudo é comparar três abordagens distintas para gerenciar essa concorrência, avaliando como cada uma lida com:
* **Deadlock:** Travamento completo do sistema.
* **Starvation:** Inanição de um processo específico.
* **Throughput:** Capacidade de processamento (refeições/tempo).

---

## 2. Metodologia

Os testes foram realizados em um ambiente controlado com as seguintes especificações:

* **Hardware/SO:** Java Virtual Machine (JVM).
* **Duração do Teste:** 5 minutos (300 segundos) por tarefa.
* **Linguagem:** Java (JDK 8+).

### Métricas Coletadas
1.  **Total de Refeições:** Soma de quantas vezes todos os filósofos comeram (Throughput).
2.  **Justiça (Fairness):** Calculada através do **Coeficiente de Variação (CV)** do número de refeições.
    * *Fórmula: CV = (Desvio Padrão / Média) * 100*.
    * *Interpretação:* Quanto menor a porcentagem, mais justa foi a distribuição de recursos.

---

## 3. Resultados Obtidos

Abaixo estão os dados consolidados após a execução de 5 minutos para cada solução.

### 3.1. Números Brutos (Refeições por Filósofo)

| Filósofo | Tarefa 2 (Hierarquia) | Tarefa 3 (Semáforos) | Tarefa 4 (Monitor) |
| :---: | :---: | :---: | :---: |
| **0** | 49 | 39 | 60 |
| **1** | 53 | 37 | 60 |
| **2** | 57 | 37 | 57 |
| **3** | 59 | 38 | 56 |
| **4** | 49 | 37 | 57 |
| **TOTAL**| **267** | **188** | **290** |

### 3.2. Comparativo de Métricas

| Métrica | Tarefa 2 (Hierarquia) | Tarefa 3 (Semáforos) | Tarefa 4 (Monitor) |
| :--- | :---: | :---: | :---: |
| **Throughput (Refeições/min)** | 53.4 | 37.6 | **58.0** 🏆 |
| **Média de Refeições ($\mu$)** | 53.4 | 37.6 | 58.0 |
| **Desvio Padrão ($\sigma$)** | 4.08 | **0.80** | 1.67 |
| **Fairness (CV %)** | 7.64% (Menos Justo) | **2.13% (Mais Justo)** ⚖️ | 2.88% (Muito Justo) |

---

## 4. Análise Crítica

### 4.1. Prevenção de Deadlock
Todas as três soluções preveniram o Deadlock com sucesso durante os 5 minutos de teste.
* **Hierarquia:** Funcionou quebrando a simetria (filósofo 4 inverte a ordem).
* **Semáforos:** Funcionou limitando a 4 o número de filósofos à mesa (Multiplex).
* **Monitores:** Funcionou garantindo acesso atômico ao estado da mesa.

### 4.2. Prevenção de Starvation e Fairness
* **Tarefa 3 (Semáforos):** Foi a solução mais "democrática". O desvio padrão foi mínimo (0.80), o que significa que praticamente todos comeram a mesma quantidade. O semáforo Java (FIFO por padrão em alguns contextos ou pelo sistema operacional) ajudou a regular a entrada.
* **Tarefa 4 (Monitores):** Apresentou excelente justiça (CV de 2.88%). O uso de `notifyAll()` permitiu que as threads acordassem e competissem, mas a lógica da mesa garantiu que ninguém ficasse muito tempo sem comer.
* **Tarefa 2 (Hierarquia):** Foi a menos justa. O Coeficiente de Variação foi quase 4x maior que o dos Semáforos. Observou-se que o Filósofo 3 comeu 59 vezes enquanto os Filósofos 0 e 4 comeram apenas 49. Isso ocorre porque a hierarquia favorece filósofos que conseguem pegar o garfo de menor ID mais rápido, criando "vizinhos dominantes".

### 4.3. Performance e Throughput
* **Vencedor: Tarefa 4 (Monitores)** com 290 refeições totais. A abstração de alto nível permitiu uma gestão muito eficiente das trocas de contexto.
* **Intermediário: Tarefa 2 (Hierarquia)** com 267 refeições. É uma solução rápida pois tem pouco overhead de lógica, mas sofreu com disputas desiguais.
* **Mais Lento: Tarefa 3 (Semáforos)** com 188 refeições.
    * *Justificativa:* A estratégia de limitar a mesa a 4 filósofos (Multiplex) é conservadora. Embora garanta a segurança, ela reduz a probabilidade estatística de dois filósofos não-vizinhos comerem simultaneamente em comparação com as outras abordagens, além do overhead de adquirir/soltar permissões do semáforo.

---

## 5. Conclusão

Com base nos dados empíricos coletados:

1.  **Melhor Solução Geral:** A **Tarefa 4 (Monitores)**.
    * Ela combinou o **maior throughput** (58 refeições/min) com uma **justiça excelente** (CV < 3%). É a implementação mais robusta para sistemas de produção.

2.  **Solução Mais Estável:** A **Tarefa 3 (Semáforos)**.
    * Se o requisito for garantia estrita de igualdade entre processos, independente da velocidade, esta foi a melhor abordagem (CV 2.13%).

3.  **Solução de Baixo Custo:** A **Tarefa 2 (Hierarquia)**.
    * Boa performance, mas propensa a desbalanceamento (Starvation parcial) em longas execuções.

**Recomendação Final:** Para cenários modernos de alta concorrência em Java, o uso de **Monitores** (ou Locks com Condition) é superior ao uso de Semáforos puros ou Hierarquias simples.

---
