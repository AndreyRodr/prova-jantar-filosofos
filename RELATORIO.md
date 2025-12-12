# Relatório Comparativo: O Jantar dos Filósofos

## 1. Introdução
O problema do Jantar dos Filósofos é um cenário clássico de concorrência proposto por Edsger Dijkstra. Ele ilustra os desafios de sincronização de recursos compartilhados (os garfos) entre múltiplos processos (os filósofos), visando evitar problemas como *Deadlock* (impasse) e *Starvation* (inanição).

Este projeto implementou e comparou quatro abordagens diferentes para resolver este problema utilizando a linguagem Java.

## 2. Metodologia
Os testes foram realizados num ambiente com as seguintes configurações:
- **Linguagem:** Java
- **Tempo de Execução:** 5 minutos por solução (configurável no Main)
- **Número de Filósofos:** 5
- **Métricas Coletadas:** Número de refeições por filósofo e observação de deadlocks.

## 3. Resultados e Análise

### Tarefa 1: Solução Ingénua (Com Deadlock)
Nesta abordagem, cada filósofo tenta pegar o garfo esquerdo e depois o direito.
- **Resultado:** Ocorreu *Deadlock*.
- **Análise:** O impasse acontece quando todos os filósofos pegam o garfo esquerdo simultaneamente. Cada um fica à espera do garfo direito, que está na mão do vizinho, criando um ciclo de dependência circular (Hold and Wait).

### Tarefa 2: Solução com Hierarquia de Recursos (Prevenção)
Um dos filósofos (o canhoto) inverte a ordem de aquisição dos garfos.
- **Deadlock:** Prevenido. A inversão da ordem quebra a condição de espera circular.
- **Performance:** [PREENCHER - EX: Alta]
- **Total de Refeições (Média):** [PREENCHER - EX: 150 refeições]

### Tarefa 3: Solução com Semáforos
Utiliza um `Semaphore` para limitar o acesso à mesa a no máximo 4 filósofos simultaneamente.
- **Deadlock:** Prevenido. Matematicamente impossível ocorrer deadlock se N-1 filósofos disputam N recursos.
- **Performance:** [PREENCHER - EX: Muito Alta]
- **Complexidade:** Baixa, pois delega a gestão para o SO via Semáforo.

### Tarefa 4: Solução com Monitores (Fairness)
Utiliza uma classe `Mesa` centralizada com `wait()` e `notifyAll()`, gerindo estados (PENSANDO, FAMINTO, COMENDO).
- **Deadlock:** Prevenido pela exclusão mútua do monitor.
- **Starvation:** Prevenido. O monitor verifica ativamente se os vizinhos podem comer quando alguém solta os garfos.
- **Fairness:** Garante uma distribuição mais justa das refeições.

## 4. Tabela Comparativa

| Métrica | Tarefa 2 (Hierarquia) | Tarefa 3 (Semáforos) | Tarefa 4 (Monitores) |
| :--- | :---: | :---: | :---: |
| **Previne Deadlock?** | Sim | Sim | Sim |
| **Previne Starvation?** | Parcialmente | Sim (pelo SO) | Sim (Garantido) |
| **Complexidade** | Baixa | Média | Alta |
| **Justiça (Fairness)** | Média | Alta | Muito Alta |

## 5. Conclusão
Após a análise das execuções:

1.  **Tarefa 2** é a mais simples de implementar, mas pode ser menos "justa" dependendo da sorte do agendador de threads.
2.  **Tarefa 3** oferece um excelente equilíbrio entre performance e simplicidade de código.
3.  **Tarefa 4** é a solução mais robusta e controlada. Embora seja mais complexa de programar, oferece controle total sobre a justiça do sistema, sendo ideal para sistemas críticos onde a inanição não é tolerável.

Para cenários de alto desempenho, a solução com **Semáforos** mostrou-se muito eficiente. Para cenários que exigem garantias estritas de justiça, a solução com **Monitores** é a vencedora.