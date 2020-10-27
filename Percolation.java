import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    private boolean[][] grid; //grid of site statuses (true=open, false=blocked)
    private WeightedQuickUnionUF uf, uf2; //2 optimized union-find instance
    private int gridSize, top, bottom, openSites; //gridSize stores number of objects for class-wide use,
    // top stores int ID of virtual top site, bottom stores int ID of virtual bottom site, openSItes store the number of openSites

    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        if(N <= 0)
            throw new IllegalArgumentException();
        grid = new boolean[N][N];//grid is NxN size
        gridSize = N;
        top = N+2;//top virtual site's ID = N+2
        bottom = N+3;// bottom virtual site"s ID = N+3
        uf = new WeightedQuickUnionUF(N*N);
        uf2 = new WeightedQuickUnionUF(N*N);//used for isFull() to prevent backwash
        openSites = 0;
        for(int r=0;r<N;r++) {
           for (int c = 0; c < N; c++)
               grid[r][c] = false;//defaults all sites to blocked status
       }
    }
    // Open site (row, col) if it is not open already.
    public void open(int row, int col) {
        if((row < 0 || row>gridSize-1) || (col < 0 || col>gridSize-1))
            throw new IndexOutOfBoundsException();
        if(!isOpen(row, col)) {
            grid[row][col] = true;
            openSites++;
        }
        linkLeft(row, col);
        linkRight(row, col);
        linkUp(row, col);
        linkDown(row, col);
    }

    //links open site to its open left neighbor
    public void linkLeft(int row, int col) {
        if(col-1>=0 && isOpen(row,col-1)){
            uf.union(encode(row,col), encode(row, col-1));
            uf2.union(encode(row,col), encode(row, col-1));
        }
    }

    //links open site to its open right neighbor
    public void linkRight(int row, int col){
        if(col+1<gridSize && isOpen(row, col+1)) {
            uf.union(encode(row, col), encode(row, col + 1));
            uf2.union(encode(row, col), encode(row,col+1));
        }
    }

    //links open site to its open neighbor above it
    //if the open site is in the top row, it will be connected to the virtual top
    public void linkUp(int row, int col){
        if(row == 0) {//top row
            uf.union(encode(row, col), top);//link top row to virtual top site (represented by N+2)
            uf2.union(encode(row, col), top);
        }
        if(row + 1 <gridSize && isOpen(row+1, col)) {
            uf.union(encode(row, col), encode(row + 1, col));
            uf2.union(encode(row, col), encode(row + 1, col));
        }
    }

    //links open site to its open neighbor below it
    //if the open site is in bottom row, it will be connected to virtual bottom in uf
    //uf2 won't be connected to virtual bottom to prevent backwash
    public void linkDown(int row, int col){
        if(row == gridSize-1)//bottom row
            uf.union(encode(row,col), bottom);//link bottom row to virtual bottom site (represented by N+3)
        if(row-1>=0 && isOpen(row-1, col)) {
            uf.union(encode(row, col), encode(row - 1, col));
            uf2.union(encode(row, col), encode(row - 1, col));
        }
    }
    // Is site (row, col) open?
    public boolean isOpen(int row, int col) {
      if(row < 0 || row>gridSize-1 || col < 0 || col>gridSize-1)
            throw new IndexOutOfBoundsException();
        return grid[row][col];
    }

    // Is site (row, col) full?
    public boolean isFull(int row, int col) {
        if((row < 0 || row>gridSize-1) || (col < 0 || col>gridSize-1))
            throw new IndexOutOfBoundsException();
        if(isOpen(row,col))
            return uf2.connected(encode(row,col),top);//check if given site is connected to top row in uf2
        else
            return false;
    }

    // Number of open sites.
    public int numberOfOpenSites() {
         return openSites;
    }

    // Does the system percolate?
    public boolean percolates() {
         return uf.connected(top,bottom);
    }

    // An integer ID (1...N) for site (row, col).
    private int encode(int row, int col) {
        return (row)*gridSize+(col);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }

        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
