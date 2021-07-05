Trabalho 1 de Algoritmos e Grafos 2020-2 (remoto)

Tema: Circuito Euleriano

Para realização do trabalho foram criadas classes em java baseando-se nas classes criadas
pelo professor em aula. As classes Grafo e Vertice foram criadas baseadas nos códigos
feitos em aulas, e alteradas para que se adequassem ao que é pedido para o circuito
euleriano.

A classe Vertice possui em seu escopo diversas variáveis como id, vizinhos, parent, root,
dist que serão usadas na classe Grafo. A classe Vertice possui uma função chamada
add_vizinho que é super importante para o desenvolvimento de um grafo. Nessa classe a
funcão print retorna todos os vizinhos do vértice escolhido.

A classe Grafo possui um Hashmap chamado “vertices” que armazena todos os vértices do
grafo. Para início possui add_vertex e add_edge, funções que criam um vértice e criam
arestas.

Nessa mesma class existe a função getAdjacente que retorna todos os vizinhos do vértice
selecionado, uma função delEdge, a qual deleta uma aresta. Além da getNos que retorna
uma lista de nós, melhor dizendo arestas.

A open_text lê um arquivo de texto, e foi criada em aula. Será usada para ler as entradas do
programa.

A função compact, “compacta” o grafo, por exemplo, os vértices 1 2 3 4 6 viram 1 2 3 4 5.

A função DFS e DFS_visit, serão usadas na função CFC, a qual retorna as componentes
fortemente conexas do grafo.
get_list_roots retorna uma lista de rotas, será usada também para verificar se o grafo possui
mais de uma componente fortemente conexa, se sim, Euler não será aplicado.

A maioria das funções acima foram criadas em aula e alteradas para funcionarem com o
circuito euleriano.

Na classe principal chamada “EulerAlgoritmo” encontra-se o que é pedido no trabalho.
Primeiramente é lido um arquivo com as entradas, essas entradas dão origem ao grafo, ele
é “compactado”. Se o grafo tiver mais de uma componente conexa, à execução para por
aqui. Caso contrário, o algoritmo para encontrar caminho/ciclo euleriano é rodado. Para um
grafo possuir caminho euleriano ele deve possuir exatamente dois vértices com grau ímpar.

Em contraste, para um grafo possuir ciclo euleriano todas as arestas devem possuir grau
par.

A função printaCaminho mostra na tela um caminho ou um ciclo de vértices se houver.

A função obterImparVertices verifica se o grau do vértice é impar ou par. Isso do grafo todo.

A função getEulerCaminho recebe um nó origem e executa uma verificação de isBridge
para cada nó adjacente do nó origem.

A função isBridge executa uma verificação para determinar se a visita da aresta(from, to)
não irá deixar o grafo disjunto. Essa verificação é necessária para garantir as propriedades
do caminho e ciclo euleriano.

A função DFS dessa classe é aplicada para encontrar todos os vértices de um grafo. Para
cada vértice um processo recursivo é iniciado onde visita-se, de forma sequencial, os filhos
adjacentes do vértice corrente que ainda não foram visitados.

OBS: As funções principais estão comentadas no código. Há uma explicação básica de
como elas funcionam. E para testar outras entradas basta alterar o arquivo entrada.txt que
encontra-se na pasta do trabalho.
Links utilizados como base:
http://www.inf.ufsc.br/grafos/temas/euleriano/euleriano.htm
https://www.ufsj.edu.br/portal2-repositorio/File/profmat/Celio.pdf
http://www.tede2.ufrpe.br:8080/tede2/bitstream/tede2/7883/2/Murilo%20Ramos%20da%20C
unha%20Ribeiro.pdf
https://edisciplinas.usp.br/pluginfile.php/4647803/mod_resource/content/1/EP2V2019.pdf