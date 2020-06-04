package com.mxgraph.examples.swing;

import java.util.ArrayList;

public class Vertex {
    String name;
    ArrayList<Edge> edges = new ArrayList<>();
    Vertex(String name)
    {
        this.name = name;
    }
    void addEdge(Edge edge)
    {
        edges.add(edge);
    }
}
