package org.buet.sky.airrecalculator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HashGraph {
    public HashMap<Integer, Set<Integer>> requireGraph;

    HashGraph(){
        requireGraph = new HashMap<>();
    }

    public void addEdge(int u, int v){
        if(requireGraph.get(u) == null) requireGraph.put(u, new HashSet<>());
        if(requireGraph.get(v) == null) requireGraph.put(v, new HashSet<>());
        requireGraph.get(u).add(v);
        requireGraph.get(v).add(u);
        System.out.println("addEdge "+u+" "+v);
    }

    public void removeEdge(int u, int v){

        System.out.println("removeEdge "+u+" "+v);
        if(requireGraph.get(u) != null){
            requireGraph.get(u).remove(v);
            if(requireGraph.get(u).isEmpty()) requireGraph.remove(u);
        }
        if(requireGraph.get(v) != null){
            requireGraph.get(v).remove(u);
            if(requireGraph.get(v).isEmpty()) requireGraph.remove(v);
        }
    }



    public Set<Integer> get(int u){
        return  requireGraph.get(u);
    }
}
