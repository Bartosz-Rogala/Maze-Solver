public class UnionFind {

    private int size; //number of elements in union find
    private int[] sz; //size of each of the component
    private int[] id; // id points to parent of i, if id[i] = i then i is a root node
    private int numComponents; //number of components in union find (we start with all and go down to 1)


    public UnionFind(int size) {

        //check if given size is more than 0
        if (size <= 0) {
            throw new IllegalArgumentException("Size has to be bigger than 0");
        }

        //give start values to variables
        this.size = size;
        this.numComponents = size;
        this.sz = new int[size];
        this.id = new int[size];

        for(int i = 0; i < size; i++) {
            id[i] = i; // first, every component links to itself (it is it's own root)
            sz[i] = 1; // first, every element is of size 1, later they'll be expanding, ending with one component of size = size
        }
    }

    //find which component 'p' belongs to
    public int find(int p) {

        //find root of component
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }

        //compress the path leading back to the root.
        //doing this operation is called path compression
        //and is what gives us amortized constant time complexity
        while(p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }
        return root;
    }

    // return true/false if 'p' and 'q' are in the same components
    public boolean ifConnected(int p, int q) {
        return find(p) == find(q);
    }

    //return size of components 'p' belongs to
    public int getComponentSize(int p) {
        return sz[find(p)];
    }

    //return number of elements in this UnionFind
    public int getSize() {
        return size;
    }

    //return number of remaining components
    public int getNumComponents() {
        return numComponents;
    }

    //Unify components containing elements 'p' and 'q'
    public void unify(int p, int q) {
        int root1 = find(p);
        int root2 = find(q);

        //check if elements are in the same group
        if (root1 == root2) {
            return;
        }

        //if not, merge two components - smaller to larger
        if (sz[root1] < sz[root2]) {
            sz[root2] += sz[root1];
            id[root1] = root2;
        } else {
            sz[root1] += sz[root2];
            id[root2] = root1;
        }

        //after merging we decrease numComponents by one
        numComponents--;
    }
}