package fr.tp.inf112.projects.robotsim.test;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.traverse.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class TestJgraphT {
    public static void main(String[] args) {
        Graph<URI, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
URI google = null;
        URI wikipedia = null;
        URI jgrapht = null;

        try {
            google = new URI("http://www.google.com");
            wikipedia = new URI("http://www.wikipedia.org");
            jgrapht = new URI("http://www.jgrapht.org");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // add the vertices
        g.addVertex(google);
        g.addVertex(wikipedia);
        g.addVertex(jgrapht);

        // add edges to create linking structure
        g.addEdge(jgrapht, wikipedia);
        g.addEdge(google, jgrapht);
        g.addEdge(google, wikipedia);
        g.addEdge(wikipedia, google);
    }
}
