package com.aigraph.system.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Model class representing a user of the system.
 */
@Data
@NoArgsConstructor
public class User implements UserDetails {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role; // ROLE_ADMIN, ROLE_TEACHER, ROLE_STUDENT
    private boolean enabled = true;
    private List<String> viewedKnowledgeNodes = new ArrayList<>();
    private List<String> favoriteKnowledgeNodes = new ArrayList<>();
    
    public User(String id, String username, String password, String fullName, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
    
    public void addViewedKnowledgeNode(String knowledgeNodeId) {
        if (!this.viewedKnowledgeNodes.contains(knowledgeNodeId)) {
            this.viewedKnowledgeNodes.add(knowledgeNodeId);
        }
    }
    
    public void addFavoriteKnowledgeNode(String knowledgeNodeId) {
        if (!this.favoriteKnowledgeNodes.contains(knowledgeNodeId)) {
            this.favoriteKnowledgeNodes.add(knowledgeNodeId);
        }
    }
    
    public void removeFavoriteKnowledgeNode(String knowledgeNodeId) {
        this.favoriteKnowledgeNodes.remove(knowledgeNodeId);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
} 