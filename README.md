# O Jantar dos Filósofos - Programação Paralela

Este repositório contém as soluções para a Avaliação Final de Programação Paralela e Distribuída. O projeto explora problemas clássicos de concorrência, Deadlock e Starvation.

## 📂 Estrutura do Projeto

* `src/tarefa1`: Implementação básica (demonstração de Deadlock).
* `src/tarefa2`: Solução com Hierarquia de Recursos (Quebra de simetria).
* `src/tarefa3`: Solução com Semáforos (Limitação de acesso).
* `src/tarefa4`: Solução com Monitores (Mesa centralizada e Fairness).
* `test/`: Testes unitários para validar a lógica.
* `RELATORIO.md`: Análise detalhada e comparativa das soluções.

## 🚀 Como Compilar e Executar

### Pré-requisitos
* Java JDK 8 ou superior.

### Compilação
Abra o terminal na raiz do projeto e crie a pasta de binários:
```bash
mkdir bin
javac -d bin src/tarefa1/*.java src/tarefa2/*.java src/tarefa3/*.java src/tarefa4/*.java
```

### Execução
Para rodar cada tarefa, utilize os comandos abaixo:

### Tarefa 1 (Deadlock)

```Bash

java -cp bin tarefa1.Main
(Nota: Esta tarefa pode travar propositadamente).
```
### Tarefa 2 (Hierarquia)

``` Bash

java -cp bin tarefa2.Main
```
### Tarefa 3 (Semáforos)

```Bash

java -cp bin tarefa3.Main
```
### Tarefa 4 (Monitores)

```Bash

java -cp bin tarefa4.Main
```
### 🧪 Como Executar os Testes
Para validar a lógica do Monitor (Tarefa 4):

``` Bash

javac -cp src test/*.java
# Teste Tarefa 1
java -cp "src;test" .\test\TesteTarefa1.java
# Teste Tarefa 2
java -cp "src;test" .\test\TesteTarefa2.java
# Teste Tarefa 3
java -cp "src;test" .\test\TesteTarefa3.java
# Teste Tarefa 4
java -cp "src;test" .\test\TesteTarefa4.java

```

### 📊 Relatório Final
Consulte o arquivo RELATORIO.md para ver a comparação de desempenho, gráficos e conclusões sobre as diferentes abordagens.

Desenvolvido para a disciplina de Programação Paralela.