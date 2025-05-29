package com.aigraph.system.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a course.
 */
@Data
@NoArgsConstructor
public class Course {
    private String id;
    private String name;
    private String description;
    private String level;
    private List<Unit> units = new ArrayList<>();
    
    public Course(String id, String name, String description, String level) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
    }
    
    public void addUnit(Unit unit) {
        this.units.add(unit);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 