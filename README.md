# Percolation_Fall2020
School project used to learn and apply the union-find data structure. 
This project uses an improved version of union-find (weighted quick union-find), whose code is found in Princeton's online textbook Algorithms, 4th Edition. 
Percolation is displayed as an NxN grid of squares. The grid is defaulted to stay in an off state and squares are opened (i.e. turned into an "on" state) one at a time based on user input. 
By "percolates," I mean that theres a clear path of open squares from the top of the grid to the bottom.
Percolation.java sets up the grid and its basic methods.
PercolationStats.java runs N Monte Carlo simulations (creates a grid and then opens random squares until the grid percolates) based on user input. 
PercolationVisualizer.java and InterativePercolcationVisualizer.java are used to visualize the grid and are also found in Algorithims, 4th edition.
