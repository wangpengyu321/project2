package com.aigraph.system.model.graph;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a link (edge) in the knowledge graph visualization.
 */
@Data
@NoArgsConstructor
public class Link {
    private String source;
    private String target;
    private String type;
    private String description;
    private int value = 1; // Link strength, can be used for visualization
    
    public Link(String source, String target, String type, String description) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.description = description;
    }
    
    public Link(String source, String target, String type, String description, int value) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.description = description;
        this.value = value;
    }
} 