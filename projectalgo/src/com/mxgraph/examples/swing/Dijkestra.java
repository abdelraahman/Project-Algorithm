package com.mxgraph.examples.swing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Dijkestra {
	static Vertex minDistance(HashMap<Vertex,Integer> dist, ArrayList<Vertex> graph, ArrayList<Vertex> sptSet)
    {
        int min = Integer.MAX_VALUE;
        Vertex minVertex = null;
        for (int v = 0; v < graph.size(); v++)
            if (sptSet.indexOf(graph.get(v)) == -1 && dist.get(graph.get(v)) <= min) {
                min = dist.get(graph.get(v));
                minVertex = graph.get(v);
            }

        return minVertex;
    }
    static ArrayList<Edge> dijkstra(ArrayList<Vertex> graph, Vertex src,int V)
    {
        ArrayList<Edge> result = new ArrayList<>();
        HashMap<Vertex,Integer> dist = new HashMap<>();
        ArrayList<Vertex> sptSet = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if(graph.get(i) != src)
                dist.put(graph.get(i),Integer.MAX_VALUE);
            else
                dist.put(src,0);
        }
        

        for (int count = 0; count < V - 1; count++) {

            Vertex u = minDistance(dist, graph,sptSet);
            // Mark the picked vertex as processed
            sptSet.add(u);

            // picked vertex.
            for(int v = 0; v < u.edges.size();v++)
                if (sptSet.indexOf(u.edges.get(v).endVertex) == -1 &&
                        dist.get(u) != Integer.MAX_VALUE &&
                        (dist.get(u) + u.edges.get(v).weight) < dist.get(u.edges.get(v).endVertex)) {
                    dist.put(u.edges.get(v).endVertex, dist.get(u) + u.edges.get(v).weight);
                    result.add(u.edges.get(v));
                }


        }

        return result;
    }

}