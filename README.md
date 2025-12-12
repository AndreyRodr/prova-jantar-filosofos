(Tarefa 1): O deadlock ocorre devido à dependência circular. Todos os filósofos executam exatamente o mesmo algoritmo: pegam o garfo à esquerda e esperam pelo da direita. Se todos os 5 filósofos ficarem com fome simultaneamente e pegarem seus garfos esquerdos, nenhum garfo direito estará livre. Como o recurso (synchronized) é bloqueante e não há timeout ou mecanismo de desistência, as threads ficam presas indefinidamente no estado de espera (BLOCKED), satisfazendo as quatro condições de Coffman para deadlock, especialmente a "Espera Circular".

(Tarefa 2): O deadlock acontece quando existe uma cadeia circular de dependências (Filósofo 0 espera Garfo 1, F1 espera G2... F4 espera G0). Ao fazer com que o Filósofo 4 pegue os garfos na ordem inversa (primeiro o G0, depois o G4), quebramos a circularidade. Se todos tentarem pegar o primeiro garfo ao mesmo tempo:

Filósofos 0, 1, 2, 3 pegam os garfos 0, 1, 2, 3.

Filósofo 4 tenta pegar o garfo 0 (que está com o Filósofo 0). Ele bloqueia e não pega o garfo 4.

Como o garfo 4 está livre, o Filósofo 3 (que já tem o 3) consegue pegar o 4 e comer.

O ciclo é desfeito.

(Tarefa 3): A solução limita o número de filósofos concorrentes a $N-1$ (neste caso, 4). O Princípio da Casa dos Pombos garante que, se temos 5 garfos e apenas 4 filósofos sentados, mesmo que cada um pegue um garfo (4 garfos ocupados), haverá necessariamente 1 garfo livre. Como os filósofos estão dispostos em círculo, este garfo livre estará ao lado de um filósofo que já tem um garfo, permitindo que ele complete o par, coma e liberte os dois garfos, permitindo que o ciclo prossiga.