import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EulerAlgoritmo {

    public static void main(String[] args) throws Exception {
        Grafo g1 = new Grafo();
        // Professor, se nao quiser usar o scanner, comente essa parte do codigo e
        // descomente o que estao comentado logo abaixo. No fim da no mesmo...
        // Para testar outras entradas, altere entrada.txt que se encontra na pasta
        Scanner scan1 = new Scanner(System.in);

        String line1 = "\n 0 Sair \n 1 Ler de arquivo texto para pegar as entradas";
        String line2 = "\n 2 Compactar - CFC - Euler - Print \n Escolha a operacao: ";
        String menu = line1 + line2;

        while (true) {
            System.out.printf(menu);
            int choice = scan1.nextInt();
            switch (choice) {
                case 0:
                    scan1.close();
                    return;
                case 1:
                    String arq_ent = "codigo/entrada.txt";
                    g1.open_text(arq_ent);
                    break;
                case 2:
                    if (g1.getSize() != 0) {
                        g1.compact();
                        g1.CFC();
                        g1.print();
                        if (g1.get_list_roots().size() > 1) {
                            System.out.println(
                                    "O grafo possui mais de uma componente fortemente conexa, nao pode haver ciclo nem caminho euleriano\n");
                            return;
                        }
                        int oddCount = obterImparVertices(g1);
                        if (oddCount == 0) {
                            System.out.println("O grafo possui ciclo eulerino");
                            printaCaminho(getEulerCaminho(g1));
                        } else if (oddCount == 2) {
                            System.out.println("Nao ha ciclo eulerinao, mas ha caminho euleriano");
                            printaCaminho(getEulerCaminho(g1));
                        } else {
                            System.out.println("Nao ha caminho nem ciclo euleriano nesse grafo");
                        }
                        return;
                    } else {
                        System.out.println("Voc� precisa primeiro ler o arquivo de entrada e de forma correta");
                    }
                    break;
            }
        }
         /*String arq_ent = "entrada.txt"; 
         g1.open_text( arq_ent ); 
         g1.compact();
         g1.CFC(); 
         g1.print(); 
         //Verifica se o grafo possui mais de uma componente conexa, se sim, nao ha execucao do algoritmo para encontrar caminho ou ciclo euleriano
         if(g1.get_list_roots().size() > 1) { 
        	 System.out.println("O grafo possui mais de uma componente fortemente conexa, nao pode haver ciclo nem caminho euleriano\n"); 
        	 return; 
         } 
         int oddCount = obterImparVertices(g1); 
         if(oddCount == 0) {
        	 System.out.println("O grafo possui ciclo eulerino");
        	 printaCaminho(getEulerCaminho(g1)); 
         } 
         else if(oddCount == 2) {
        	 System.out.println("Nao ha ciclo eulerinao, mas ha caminho euleriano");
         	printaCaminho(getEulerCaminho(g1)); 
         } 
         else {
         System.out.println("Nao ha caminho nem ciclo euleriano nesse grafo"); 
         }*/
         
    }

    public static void printaCaminho(List<Vertice> caminho) {
        StringBuilder rota = new StringBuilder();
        for (Vertice vertex : caminho) {
            rota.append(vertex.id).append("-");
        }
        rota.deleteCharAt(rota.length() - 1);
        System.out.println(rota);
    }

    /*
     * A funcao obterImpartVertices retorna o numero de vertices �mpar. Em relacao a
     * busca do caminho/ciclo, caso o grafo possua um ciclo (todos os vertices tem
     * grau par) e adicionado como origem o primeiro vertice (poderia ser qualquer
     * vertice) para invocar a funcao getEulerCaminho. Na outra situacao em que o
     * grafo possui um caminho euleriano (possui dois vertices de grau �mpar) e
     * adicionado o primeiro vertice de grau �mpar como origem no caminho e e
     * invocado a funcao getEulerCaminho.
     */
    public static int obterImparVertices(Grafo grafo) {
        int oddCount = 0;
        for (Vertice no : grafo.getNos()) {
            if (no.degree() % 2 == 1) {
                oddCount++;
            }
        }
        return oddCount;
    }

    public static Vertice getEulerPathOrigin(Grafo grafo) {
        for (Vertice no : grafo.getNos()) {
            if (no.degree() % 2 == 1) {
                return no;
            }
        }
        throw new RuntimeException("Error ao encontrar a origem do caminho de Euler");
    }

    /*
     * A ultima funcao getEulerCaminho, dado um grafo, retorna o caminho ou ciclo
     * Euleriano se o grafo respeitar as propriedades de caminho ou ciclo Euleriano,
     * caso contrario e retornado um erro.
     */
    public static List<Vertice> getEulerCaminho(Grafo grafo) throws Exception {
        List<Vertice> caminho = new ArrayList<Vertice>();
        int oddCount = obterImparVertices(grafo);
        if (oddCount == 0) {
            caminho.add(grafo.getNos().get(0));
            getEulerCaminho(grafo, caminho, caminho.get(0));
        } else if (oddCount == 2) {
            caminho.add(getEulerPathOrigin(grafo));
            getEulerCaminho(grafo, caminho, caminho.get(0));
        } else {
            throw new Exception("As propriedades do Euler foram quebradas");
        }
        return caminho;
    }

    /*
     * A funcao getEulerCaminho e um procedimento recursivo para encontrar um
     * caminho/ciclo a partir de um no de origem Esse algoritmo funciona da seguinte
     * forma: dado um no de origem from, se uma dada aresta(from, to) nao e definida como ponte ou
     * e a unica aresta para sair de from entao a mesma e removida do grafo e o registro
     * de avanco para to e adicionado e lista caminho. Assim, garante-se que caminho
     * reversos nao serao feitos.
     */
    public static void getEulerCaminho(Grafo grafo, List<Vertice> caminho, Vertice from) {
        Vertice[] nos = from.vizinhos.values().toArray(new Vertice[] {});
        for (Vertice to : nos) {
            if (!isBridge(grafo, from, to)) {
                caminho.add(to);
                grafo.delEdge(from, to);
                getEulerCaminho(grafo, caminho, to);
                break;
            }
        }
    }

    /*
     * Dados dois vertices from e to, a funcao isBridge verifica se a visita da
     * aresta(from, to) nao ira deixar o grafo disjunto. Essa verificao e necessaria para
     * garantir as propriedades do caminho e ciclo euleriano, assim a remocao de uma
     * aresta nao ira impossibilitar a visita dos proximos vertices nao visitados.
     */
    public static boolean isBridge(Grafo grafo, Vertice from, Vertice to) {
        if (from.vizinhos.size() == 1) {
            return false;
        }
        int contagemPonte = DFS(new HashSet<Vertice>(), to);

        grafo.delEdge(from, to);
        int naoContagemPonte = DFS(new HashSet<Vertice>(), to);

        grafo.add_edge(from.id, to.id);
        return naoContagemPonte < contagemPonte;
    }

    /*
     * A busca DFS calcula a cobertura de um vertice dado. A cobertura indica
     * quantos vertices podem ser alcancados partindo de uma determinada origem. O
     * DFS geralmente lida com uma lista de vertices, mas no algoritmo de Fleury,
     * utilizado para achar o caminho ou ciclo euleriano  sera necessario processar
     * somente o vertice corrente para encontrar a quantidade de vertices cobertos.
     * Logo o algoritmo foi adaptado. Portanto, essa versao do DFS funciona da
     * seguinte forma: dado um vertice de origem from, um processo recursivo comeca
     * para cada vertice adjacente de forma e contar a quantidade de vertices que
     * sao cobertos.
     */
    public static int DFS(Set<Vertice> visitado, Vertice from) {
        int contador = 1;
        visitado.add(from);
        Vertice[] nos = from.vizinhos.values().toArray(new Vertice[] {});
        for (Vertice to : nos) {
            if (!visitado.contains(to)) {
                contador = contador + DFS(visitado, to);
            }
        }
        return contador;
    }
}
