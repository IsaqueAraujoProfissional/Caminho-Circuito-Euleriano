import java.util.HashMap;

public class Vertice implements Comparable<Vertice> {
    protected Integer id;
    protected HashMap<Integer, Vertice> vizinhos;
    protected Vertice parent, root;
    protected Integer dist, d, f;
    protected int size;

    public Vertice(int id) {
        // id >= 1
        this.id = id;
        vizinhos = new HashMap<Integer, Vertice>();
        parent = null;
        dist = d = null;
    }

    protected void reset() {
        parent = null;
        d = null;
        f = null;
        dist = null;
    }

    public void add_vizinho(Vertice viz) {
        vizinhos.put(viz.id, viz);
    }

    public int degree() {
        return vizinhos.size();
    }

    public void discover(Vertice parent) {
        this.parent = parent;
        this.dist = parent.dist + 1;
    }

    protected Vertice get_root() {
        if (parent == null)
            root = this;
        else
            root = parent.get_root();
        return root;
    }

    public void print() {
        System.out.print("\nId do vertice " + id + ", Vizinhanca: ");
        for (Vertice v : vizinhos.values())
            System.out.print(" " + v.id);
    }

    @Override
    public int compareTo(Vertice otherVertex) {
        if (otherVertex.size > this.size)
            return 1;
        else
            return -1;
    }
}
