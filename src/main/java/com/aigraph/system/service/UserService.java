package com.aigraph.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aigraph.system.model.User;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for user authentication and management.
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Getter
    private final Map<String, User> users = new HashMap<>();
    
    @PostConstruct
    public void init() {
        // Add some demo users
        createUser("admin", "admin123", "管理员", "admin@example.com", "ROLE_ADMIN");
        createUser("teacher", "teacher123", "张老师", "teacher@example.com", "ROLE_TEACHER");
        createUser("student", "student123", "刘同学", "student@example.com", "ROLE_STUDENT");
    }
    
    public User createUser(String username, String password, String fullName, String email, String role) {
        if (getUserByUsername(username) != null) {
            log.warn("User with username {} already exists", username);
            return null;
        }
        
        String id = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);
        
        User user = new User(id, username, encodedPassword, fullName, email, role);
        users.put(username, user);
        log.info("Created user: {}", username);
        
        return user;
    }
    
    public User getUserByUsername(String username) {
        return users.get(username);
    }
    
    public User updateUser(User user) {
        if (users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            log.info("Updated user: {}", user.getUsername());
            return user;
        }
        return null;
    }
    
    public boolean deleteUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            log.info("Deleted user: {}", username);
            return true;
        }
        return false;
    }
    
    public boolean validateCredentials(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }
    
    /**
     * 获取用户的学习进度
     */
    public Map<String, Object> getUserLearningProgress(String userId) {
        // 模拟数据 - 实际应用中应从数据库获取
        Map<String, Object> progress = new HashMap<>();
        progress.put("completedPercentage", 65);
        progress.put("totalNodes", 256);
        progress.put("completedNodes", 167);
        progress.put("remainingNodes", 89);
        progress.put("lastAccessDate", "2023-03-15");
        return progress;
    }
    
    /**
     * 获取用户已完成的课程
     */
    public List<Map<String, Object>> getCompletedCourses(String userId) {
        // 模拟数据 - 实际应用中应从数据库获取
        List<Map<String, Object>> completedCourses = new ArrayList<>();
        
        Map<String, Object> course1 = new HashMap<>();
        course1.put("id", "course001");
        course1.put("name", "人工智能导论");
        course1.put("completionDate", "2023-02-20");
        course1.put("score", 92);
        completedCourses.add(course1);
        
        Map<String, Object> course2 = new HashMap<>();
        course2.put("id", "course002");
        course2.put("name", "机器学习基础");
        course2.put("completionDate", "2023-03-10");
        course2.put("score", 88);
        completedCourses.add(course2);
        
        return completedCourses;
    }
} 