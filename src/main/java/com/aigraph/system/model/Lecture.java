package com.aigraph.system.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a lecture in a course unit.
 */
@Data
@NoArgsConstructor
public class Lecture {
    private String id;
    private String name;
    private String content;
    private int order;
    private List<String> relatedKnowledgeNodeIds = new ArrayList<>();
    
    public Lecture(String id, String name, String content, int order) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.order = order;
    }
    
    public void addRelatedKnowledgeNodeId(String knowledgeNodeId) {
        this.relatedKnowledgeNodeIds.add(knowledgeNodeId);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(id, lecture.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 