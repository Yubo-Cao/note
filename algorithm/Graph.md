[[Algorithms.pdf]]
# Directed Graph
## Definition
- Edges are one way, same as those in undirected graphs
	- A *directed graph* is a set of vertices and a collection of *directed edges*. Each directed edge connects an ordered pair of vertices
## Terminology
- *Directed edge*: Points from first vertex to the second vertex in the tuple
	- *Head* for first vertex
	- *Tail* for second vertex
	- `v -> w`
- *Outdegree*: The number of edges from vertex that going from it
- *Indgree*: The number of edges from vertex that going to it
- Vertices relationship
	- no edge
	- `v -> w`
	- `w -> v`
	- `v -> w && w -> v
- *Directed Path*, a sequence of vertices, where for every vertex and it successor, there exists a directed edge pointing from its successsor to itself.
- *Directed Cycle*,  a directed path
	- with the same first and last vertices
	- at least one edge
- *Simple Cycle*, a cycle with no repeated edges or vertices.
- *Length,* the number of edges in a path. `
## Application
- Food web
- Internet content
- Packages and dependencies etc. 


