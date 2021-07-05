import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Grafo {
	private HashMap<Integer, Vertice> vertices;
	private int time;
	//private Boolean acyclic;

	public Grafo() {
		vertices = new HashMap<Integer, Vertice>();
	}

	public void add_vertex(int id) {
		if (id < 1 || this.vertices.get(id) == null) {
			Vertice v = new Vertice(id);
			vertices.put(v.id, v);
			reset();
		}
		/*
		 * else System.out.println("Id invÃ¡lido ou jÃ¡ utilizado!");
		 */
	}

	public void add_edge(Integer id1, Integer id2) {
		Vertice v1 = vertices.get(id1);
		Vertice v2 = vertices.get(id2);
		//O comentario abaixo se tornou irrelevante na versao final do trabalho
		// Caso o vértice não exista, ele é criado automaticamente através da funcao
		// add_edge para não haver erro no algoritmo euleriano nem com as entradas
		if (v1 == null || v2 == null) {
			System.out.printf("VÃ©rtice inexistente!");
			/*
			 * add_vertex(id1); v1 = vertices.get( id1 ); add_vertex(id2); v2 =
			 * vertices.get( id2 );
			 */
			return;
		}
		v1.add_vizinho(v2);
		v2.add_vizinho(v1);
		reset();
	}

	// Função que retorna uma lista de vizinhos
	public List<Vertice> getAdjacent(int i) {
		if (vertices.containsKey(i)) {
			return new ArrayList<Vertice>(Arrays.asList(vertices.get(i).vizinhos.values().toArray(new Vertice[] {})));
		} else {
			return new ArrayList<Vertice>();
		}
	}

	// Função que deleta uma aresta
	public void delEdge(Vertice i, Vertice j) {
		if (vertices.containsKey(i.id)) {
			vertices.get(i.id).vizinhos.remove(j.id);
			vertices.get(j.id).vizinhos.remove(i.id);
		}
	}

	// Função que retorna uma lista de vértices
	public List<Vertice> getNos() {
		return new ArrayList<Vertice>(Arrays.asList(vertices.values().toArray(new Vertice[] {})));
	}

	// Função que retorna o tamanho do HashMap vertices
	public int getSize() {
		return vertices.size();
	}

	public void open_text(String arq_ent) {
		String thisLine = null;
		vertices = new HashMap<Integer, Vertice>();
		String pieces[];

		try {
			FileReader file_in = new FileReader(arq_ent);
			BufferedReader br1 = new BufferedReader(file_in);
			while ((thisLine = br1.readLine()) != null) {
				// retira excessos de espaÃ§os em branco
				thisLine = thisLine.replaceAll("\\s+", " ");
				pieces = thisLine.split(" ");
				int v1 = Integer.parseInt(pieces[0]);
				this.add_vertex(v1);
				for (int i = 2; i < pieces.length; i++) {
					int v2 = Integer.parseInt(pieces[i]);
					// pode ser a primeira ocorrÃªncia do v2
					this.add_vertex(v2);
					this.add_edge(v1, v2);
				}
			}
			br1.close();
			System.out.print("Arquivo lido com sucesso.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Essa funcao compacta o grafo. Por exemplo: vertices 1 2 3 4 6 vira 1 2 3 4 5
	public void compact() {
		int n = vertices.size();
		// ids utilizados de 1 a n
		int[] small = new int[n + 1];
		Vertice[] big = new Vertice[n];

		int qbig = 0;
		for (Vertice v1 : vertices.values()) {
			if (v1.id <= n)
				small[v1.id] = 1;
			else
				big[qbig++] = v1;
		}

		int i = 1;
		for (int pairs = 0; pairs < qbig; i++) {
			if (small[i] == 0)
				small[pairs++] = i;
		}

		for (i = 0; i < qbig; i++) {
			int old_id = big[i].id;
			big[i].id = small[i];

			vertices.remove(old_id);
			vertices.put(big[i].id, big[i]);

			for (Vertice v1 : vertices.values()) {
				if (v1.vizinhos.get(old_id) != null) {
					v1.vizinhos.remove(old_id);
					v1.vizinhos.put(big[i].id, big[i]);
				}
			}
		}
	}

	private void reset() {
		//acyclic = null;
		time = 0;
		for (Vertice v1 : vertices.values())
			v1.reset();
	}

	// Busca em profundidade usada na funcao CFS, a qual e usada para descobrir se
	// o grafo e fortemente conexo
	public void DFS(List<Vertice> ordering) {
		reset();
		if (ordering == null) {
			ordering = new ArrayList<Vertice>();
			ordering.addAll(vertices.values());
		}

		//acyclic = true;
		for (Vertice v1 : ordering)
			if (v1.d == null)
				DFS_visit(v1);
	}

	private void DFS_visit(Vertice v1) {
		v1.d = ++time;
		for (Vertice neig : v1.vizinhos.values()) {
			if (neig.d == null) {
				neig.parent = v1;
				DFS_visit(neig);
			} else if (neig.d < v1.d && neig.f == null){
				//acyclic = false;
			}
		}
		v1.f = ++time;
	}

	public Grafo reverse() {
		Grafo d2 = new Grafo();
		for (Vertice v11 : this.vertices.values()) {
			d2.add_vertex(v11.id);
		}
		for (Vertice v11 : this.vertices.values()) {
			for (Vertice v12 : v11.vizinhos.values()) {
				Vertice v21 = d2.vertices.get(v11.id);
				Vertice v22 = d2.vertices.get(v12.id);
				v22.add_vizinho(v21);
			}
		}
		return d2;
	}

	// Funcao que retorna a lista de rotas do grafo, pode haver 1 2 ou mais...
	public List<Vertice> get_list_roots() {
		List<Vertice> list_roots = new ArrayList<Vertice>();
		for (Vertice v1 : vertices.values()) {
			if (v1.parent == null)
				list_roots.add(v1);
		}
		return list_roots;
	}

	// Funcao que verifica as rotas
	public void CFC() {
		this.DFS(null);
		Grafo d2 = this.reverse();

		List<Vertice> decreasing_f2 = new ArrayList<Vertice>();
		for (Vertice v1 : this.vertices.values()) {
			Vertice v2 = d2.vertices.get(v1.id);
			v2.size = v1.f;
			decreasing_f2.add(v2);
		}
		Collections.sort(decreasing_f2);

		d2.DFS(decreasing_f2);

		this.reset();
		for (Vertice v21 : d2.vertices.values()) {
			Vertice v11 = this.vertices.get(v21.id);
			if (v21.parent != null) {
				Vertice v12 = this.vertices.get(v21.parent.id);
				v11.parent = v12;
			}
		}
	}

	public void print() {
		for (Vertice v : vertices.values())
			v.print();
		// CFC
		System.out.print("\nComponentes fortemente conexas");
		for (Vertice v1 : vertices.values())
			v1.root = v1.get_root();
		List<Vertice> list_roots = get_list_roots();
		for (Vertice v1 : list_roots) {
			System.out.print("\nOutra CFC:");
			for (Vertice v2 : vertices.values()) {
				if (v2.root == v1)
					System.out.print(" " + v2.id);
			}
		}
		System.out.print("\n");

	}
}
