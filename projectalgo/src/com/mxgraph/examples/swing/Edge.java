package com.mxgraph.examples.swing;

public class Edge {
    Vertex startVertex;
    Vertex endVertex;
    int weight;
    Edge(Vertex startVertex, Vertex endVertex, int weight)
    {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.weight = weight;
    }
}
