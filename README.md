# Prova de Programação Paralela - Jantar dos Filósofos

Este repositório contém as soluções para a avaliação final da disciplina de Programação Paralela, implementando o problema clássico do Jantar dos Filósofos em Java.

**Aluno:** [Seu Nome Aqui]
**Data:** Dezembro/2025

## Estrutura do Projeto

O projeto segue a estrutura solicitada:
* `src/tarefa1`: Implementação ingênua (com Deadlock).
* `src/tarefa2`: Solução com Hierarquia de Recursos (Filósofo Canhoto).
* `src/tarefa3`: Solução com Semáforos (Limitação de acesso).
* `src/tarefa4`: Solução com Monitores (Fairness).
* `test/`: Testes unitários básicos para validação das classes.
* `RELATORIO.md`: Análise comparativa detalhada (Tarefa 5).

## Instruções de Compilação e Execução

Para compilar e executar qualquer tarefa, navegue até a raiz do projeto (`prova-jantar-filosofos`) e utilize os comandos abaixo.

### Tarefa 1: Deadlock
Demonstra o travamento do sistema quando todos pegam o garfo esquerdo simultaneamente.
```bash
javac src/tarefa1/*.java
java -cp src tarefa1.Main
```

Nota: O programa foi configurado para forçar o erro rapidamente. Após o travamento, use Ctrl+C para encerrar.

### Tarefa 2: Prevenção de Deadlock (Hierarquia)
O Filósofo 4 pega os garfos na ordem inversa (Direita -> Esquerda), quebrando o ciclo de espera.

```Bash
javac src/tarefa2/*.java
java -cp src tarefa2.Main
```
### Tarefa 3: Semáforos
Usa um Semaphore(4) para impedir que os 5 filósofos sentem à mesa ao mesmo tempo.

```Bash

javac src/tarefa3/*.java
java -cp src tarefa3.Main
```

### Execução dos Testes
Para verificar a integridade estrutural das classes:

```Bash

javac -cp src test/*.java
# Teste Tarefa 1
java -cp "src;test" TesteTarefa1
# Teste Tarefa 2
java -cp "src;test" TesteTarefa2
# Teste Tarefa 3
java -cp "src;test" TesteTarefa3
```






## Documentação das Soluções
Tarefa 1: O Problema do Deadlock
Nesta implementação, cada thread (filósofo) tenta pegar o garfo à sua esquerda e depois o da direita. O Deadlock ocorre porque existe uma Espera Circular: todos os 5 filósofos podem pegar o garfo esquerdo ao mesmo tempo. Todos ficam esperando o garfo da direita (que está na mão do vizinho), e ninguém solta o garfo que tem. As 4 condições de Coffman são satisfeitas.

Tarefa 2: Solução Hierárquica
Para prevenir o deadlock, quebramos a condição de "Espera Circular".

Lógica: Os filósofos 0 a 3 pegam Esquerda -> Direita. O Filósofo 4 pega Direita -> Esquerda.

Resultado: Se todos tentarem pegar o primeiro garfo ao mesmo tempo, o Filósofo 4 tentará pegar o garfo que o Filósofo 0 já pegou (ou vai pegar). Isso cria uma assimetria que garante que pelo menos um filósofo consiga pegar ambos os garfos.

Tarefa 3: Solução com Semáforos
Limitamos o número de filósofos à mesa para N-1 (4 filósofos).

Lógica: Usamos Semaphore(4). Antes de pegar qualquer garfo, o filósofo deve adquirir uma permissão (acquire).

Resultado: Pelo princípio da casa dos pombos, se temos 5 garfos e apenas 4 filósofos com permissão para comer, garantimos que pelo menos 1 filósofo terá acesso aos dois garfos vizinhos, impedindo o deadlock.