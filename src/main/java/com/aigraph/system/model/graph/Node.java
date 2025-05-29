package com.aigraph.system.model.graph;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a node in the knowledge graph visualization.
 */
@Data
@NoArgsConstructor
public class Node {
    private String id;
    private String name;
    private String type;
    private String description;
    private int difficulty;
    private String chapter;
    private int group; // Used for node grouping in visualization
    
    public Node(String id, String name, String type, String description, int difficulty, String chapter) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.difficulty = difficulty;
        this.chapter = chapter;
        
        // Determine group based on chapter
        if (chapter.contains("第一单元")) {
            this.group = 1;
        } else if (chapter.contains("第二单元")) {
            this.group = 2;
        } else if (chapter.contains("第三单元")) {
            this.group = 3;
        } else if (chapter.contains("第四单元")) {
            this.group = 4;
        } else if (chapter.contains("第五单元")) {
            this.group = 5;
        } else if (chapter.contains("第六单元")) {
            this.group = 6;
        } else if (chapter.contains("第七单元")) {
            this.group = 7;
        } else if (chapter.contains("第八单元")) {
            this.group = 8;
        } else {
            this.group = 0;
        }
    }
} 