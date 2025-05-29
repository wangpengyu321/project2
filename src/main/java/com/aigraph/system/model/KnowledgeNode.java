package com.aigraph.system.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a knowledge node in the knowledge graph.
 */
@Data
@NoArgsConstructor
public class KnowledgeNode {
    private String id;
    private String name;
    private String type;
    private String description;
    private int difficulty;
    private String chapter;
    private List<String> keywords = new ArrayList<>();
    private List<Relationship> relationships = new ArrayList<>();
    
    /**
     * Nested class representing a relationship between knowledge nodes.
     */
    @Data
    @NoArgsConstructor
    public static class Relationship {
        private String targetId;
        private String type;
        private String description;
        
        public Relationship(String targetId, String type, String description) {
            this.targetId = targetId;
            this.type = type;
            this.description = description;
        }
    }
    
    public KnowledgeNode(String id, String name, String type, String description, int difficulty, 
                         String chapter, List<String> keywords) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.difficulty = difficulty;
        this.chapter = chapter;
        this.keywords = keywords;
    }
    
    public void addRelationship(String targetId, String type, String description) {
        this.relationships.add(new Relationship(targetId, type, description));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnowledgeNode that = (KnowledgeNode) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 