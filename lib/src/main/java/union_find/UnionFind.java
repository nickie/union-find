package union_find;

public class UnionFind {

  public UnionFind(int n) {
    size = n;
    parent = new int[size];
    rank = new int[size];
    for (int i = 0; i < size; ++i) {
      parent[i] = i;
      rank[i] = 0;
    }
  }

  public int find(int u) {
    while (true) {
      int p = parent[u];
      if (p == u) return u;
      u = p;
    }
  }

  public void union(int u, int v) {
    int ru = find(u);
    int rv = find(v);
    if (ru != rv) {
      if (rank[ru] < rank[rv]) {
        parent[ru] = rv;
        ++rank[ru];
      } else {
        parent[rv] = ru;
        ++rank[rv];
      }
    }
  }

  private int size;
  private int[] parent;
  private int[] rank;
}
