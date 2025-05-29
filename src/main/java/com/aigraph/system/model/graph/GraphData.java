package com.aigraph.system.model.graph;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object representing the knowledge graph visualization data.
 */
@Data
@NoArgsConstructor
public class GraphData {
    private List<Node> nodes = new ArrayList<>();
    private List<Link> links = new ArrayList<>();
    
    public void addNode(Node node) {
        this.nodes.add(node);
    }
    
    public void addLink(Link link) {
        this.links.add(link);
    }
} 