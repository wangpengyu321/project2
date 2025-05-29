package com.aigraph.system.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a unit in a course.
 */
@Data
@NoArgsConstructor
public class Unit {
    private String id;
    private String name;
    private String description;
    private int order;
    private List<Lecture> lectures = new ArrayList<>();
    private List<KnowledgeNode> knowledgeNodes = new ArrayList<>();
    
    public Unit(String id, String name, String description, int order) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.order = order;
    }
    
    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }
    
    public void addKnowledgeNode(KnowledgeNode knowledgeNode) {
        this.knowledgeNodes.add(knowledgeNode);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(id, unit.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 