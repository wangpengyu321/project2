package com.aigraph.system.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.aigraph.system.model.Course;
import com.aigraph.system.model.KnowledgeNode;
import com.aigraph.system.model.Unit;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for loading and parsing knowledge graph data from the Cypher file.
 */
@Service
@Slf4j
public class KnowledgeGraphDataService {

    @Value("${app.data.file}")
    private String dataFilePath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Getter
    private final Map<String, KnowledgeNode> knowledgeNodes = new HashMap<>();
    
    @Getter
    private final Map<String, Course> courses = new HashMap<>();
    
    @Getter
    private final Map<String, Unit> units = new HashMap<>();

    @PostConstruct
    public void init() {
        loadData();
    }

    public void loadData() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resourceLoader.getResource(dataFilePath).getInputStream()))) {
            String line;
            StringBuilder currentNode = new StringBuilder();
            boolean isNodeDefinition = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }

                if (line.startsWith("CREATE (") && !line.contains(")-[")) {
                    if (isNodeDefinition) {
                        // Process previous node
                        parseNodeDefinition(currentNode.toString());
                    }
                    isNodeDefinition = true;
                    currentNode = new StringBuilder(line);
                } else if (isNodeDefinition && line.endsWith(")") || line.endsWith(");")) {
                    currentNode.append(" ").append(line);
                    parseNodeDefinition(currentNode.toString());
                    isNodeDefinition = false;
                } else if (isNodeDefinition) {
                    currentNode.append(" ").append(line);
                } else if (line.contains("CREATE (") && line.contains(")-[")) {
                    // This is a relationship line
                    parseRelationship(line);
                }
            }
        } catch (IOException e) {
            log.error("Error loading knowledge graph data: {}", e.getMessage(), e);
        }
    }

    private void parseNodeDefinition(String nodeStr) {
        if (nodeStr.contains(":Course")) {
            parseCourse(nodeStr);
        } else if (nodeStr.contains(":Unit")) {
            parseUnit(nodeStr);
        } else if (nodeStr.contains(":KnowledgeNode")) {
            parseKnowledgeNode(nodeStr);
        }
    }

    private void parseCourse(String courseStr) {
        Pattern idPattern = Pattern.compile("id:\\s*\"([^\"]+)\"");
        Pattern namePattern = Pattern.compile("name:\\s*\"([^\"]+)\"");
        Pattern descPattern = Pattern.compile("description:\\s*\"([^\"]+)\"");
        Pattern levelPattern = Pattern.compile("level:\\s*\"([^\"]+)\"");

        Matcher idMatcher = idPattern.matcher(courseStr);
        Matcher nameMatcher = namePattern.matcher(courseStr);
        Matcher descMatcher = descPattern.matcher(courseStr);
        Matcher levelMatcher = levelPattern.matcher(courseStr);

        if (idMatcher.find() && nameMatcher.find() && descMatcher.find() && levelMatcher.find()) {
            String id = idMatcher.group(1);
            String name = nameMatcher.group(1);
            String description = descMatcher.group(1);
            String level = levelMatcher.group(1);

            Course course = new Course(id, name, description, level);
            courses.put(id, course);
        }
    }

    private void parseUnit(String unitStr) {
        Pattern idPattern = Pattern.compile("id:\\s*\"([^\"]+)\"");
        Pattern namePattern = Pattern.compile("name:\\s*\"([^\"]+)\"");
        Pattern descPattern = Pattern.compile("description:\\s*\"([^\"]+)\"");
        Pattern orderPattern = Pattern.compile("order:\\s*(\\d+)");

        Matcher idMatcher = idPattern.matcher(unitStr);
        Matcher nameMatcher = namePattern.matcher(unitStr);
        Matcher descMatcher = descPattern.matcher(unitStr);
        Matcher orderMatcher = orderPattern.matcher(unitStr);

        if (idMatcher.find() && nameMatcher.find() && descMatcher.find() && orderMatcher.find()) {
            String id = idMatcher.group(1);
            String name = nameMatcher.group(1);
            String description = descMatcher.group(1);
            int order = Integer.parseInt(orderMatcher.group(1));

            Unit unit = new Unit(id, name, description, order);
            units.put(id, unit);
        }
    }

    private void parseKnowledgeNode(String nodeStr) {
        Pattern idPattern = Pattern.compile("id:\\s*\"([^\"]+)\"");
        Pattern namePattern = Pattern.compile("name:\\s*\"([^\"]+)\"");
        Pattern typePattern = Pattern.compile("type:\\s*\"([^\"]+)\"");
        Pattern descPattern = Pattern.compile("description:\\s*\"([^\"]+)\"");
        Pattern difficultyPattern = Pattern.compile("difficulty:\\s*(\\d+)");
        Pattern chapterPattern = Pattern.compile("chapter:\\s*\"([^\"]+)\"");
        Pattern keywordsPattern = Pattern.compile("keywords:\\s*\\[([^\\]]+)\\]");

        Matcher idMatcher = idPattern.matcher(nodeStr);
        Matcher nameMatcher = namePattern.matcher(nodeStr);
        Matcher typeMatcher = typePattern.matcher(nodeStr);
        Matcher descMatcher = descPattern.matcher(nodeStr);
        Matcher difficultyMatcher = difficultyPattern.matcher(nodeStr);
        Matcher chapterMatcher = chapterPattern.matcher(nodeStr);
        Matcher keywordsMatcher = keywordsPattern.matcher(nodeStr);

        if (idMatcher.find() && nameMatcher.find() && typeMatcher.find() && descMatcher.find() && 
            difficultyMatcher.find() && chapterMatcher.find()) {
            
            String id = idMatcher.group(1);
            String name = nameMatcher.group(1);
            String type = typeMatcher.group(1);
            String description = descMatcher.group(1);
            int difficulty = Integer.parseInt(difficultyMatcher.group(1));
            String chapter = chapterMatcher.group(1);
            
            List<String> keywords = new ArrayList<>();
            if (keywordsMatcher.find()) {
                String keywordsStr = keywordsMatcher.group(1);
                String[] keywordArray = keywordsStr.split(",");
                for (String keyword : keywordArray) {
                    keyword = keyword.trim().replaceAll("\"", "");
                    keywords.add(keyword);
                }
            }

            KnowledgeNode node = new KnowledgeNode(id, name, type, description, difficulty, chapter, keywords);
            knowledgeNodes.put(id, node);
        }
    }

    private void parseRelationship(String relationshipStr) {
        Pattern relationPattern = Pattern.compile("\\(([^:]+).*\\)-\\[:([^\\s{]+).*?\\]->\\(([^:]+).*\\)");
        Matcher relationMatcher = relationPattern.matcher(relationshipStr);
        
        if (relationMatcher.find()) {
            String sourceLabel = relationMatcher.group(1).trim();
            String relationType = relationMatcher.group(2).trim();
            String targetLabel = relationMatcher.group(3).trim();
            
            // Extract IDs from labels and relationships
            Pattern idPattern = Pattern.compile("\\{id:\\s*'([^']+)'\\}");
            Matcher sourceMatcher = idPattern.matcher(relationshipStr);
            Matcher targetMatcher = idPattern.matcher(relationshipStr);
            
            if (sourceMatcher.find() && targetMatcher.find(sourceMatcher.end())) {
                String sourceId = sourceMatcher.group(1);
                String targetId = targetMatcher.group(1);
                
                // Handle Course to Unit relationship
                if (relationType.equals("CONTAINS") && courses.containsKey(sourceId) && units.containsKey(targetId)) {
                    Course course = courses.get(sourceId);
                    Unit unit = units.get(targetId);
                    course.addUnit(unit);
                } 
                // Handle Unit to KnowledgeNode relationship
                else if (relationType.equals("CONTAINS") && units.containsKey(sourceId) && knowledgeNodes.containsKey(targetId)) {
                    Unit unit = units.get(sourceId);
                    KnowledgeNode knowledgeNode = knowledgeNodes.get(targetId);
                    unit.addKnowledgeNode(knowledgeNode);
                }
                // Handle KnowledgeNode to KnowledgeNode relationships
                else if (knowledgeNodes.containsKey(sourceId) && knowledgeNodes.containsKey(targetId)) {
                    KnowledgeNode sourceNode = knowledgeNodes.get(sourceId);
                    String relationDescription = "";
                    
                    // Extract relation description if available
                    Pattern descPattern = Pattern.compile("description:\\s*\"([^\"]+)\"");
                    Matcher descMatcher = descPattern.matcher(relationshipStr);
                    if (descMatcher.find()) {
                        relationDescription = descMatcher.group(1);
                    }
                    
                    sourceNode.addRelationship(targetId, relationType, relationDescription);
                }
            }
        }
    }

    public List<KnowledgeNode> getAllKnowledgeNodes() {
        return new ArrayList<>(knowledgeNodes.values());
    }

    public KnowledgeNode getKnowledgeNodeById(String id) {
        return knowledgeNodes.get(id);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public Course getCourseById(String id) {
        return courses.get(id);
    }

    public List<Unit> getAllUnits() {
        return new ArrayList<>(units.values());
    }

    public Unit getUnitById(String id) {
        return units.get(id);
    }
    
    /**
     * 获取用户的最近搜索记录
     */
    public List<String> getRecentSearches(String userId) {
        // 模拟数据 - 实际应用中应从数据库获取
        return Arrays.asList("Knowledge Graph", "Machine Learning", "Deep Neural Network", "AI Introduction");
    }
    
    /**
     * 获取热门搜索关键词
     */
    public List<String> getPopularKeywords() {
        // 模拟数据 - 实际应用中应基于所有用户的搜索统计
        return Arrays.asList("Knowledge Graph", "Neural Network", "Machine Learning", "Deep Learning", "NLP");
    }
    
    /**
     * 获取图谱中的关系数量
     */
    public int getRelationshipCount() {
        // 计算所有知识点之间的关系总数
        int count = 0;
        for (KnowledgeNode node : knowledgeNodes.values()) {
            count += node.getRelationships().size();
        }
        return count;
    }
    
    /**
     * 获取学习资源数据
     */
    public List<Map<String, Object>> getLearningResources() {
        // 模拟数据 - 实际应用中应从数据库获取
        List<Map<String, Object>> resources = new ArrayList<>();
        
        Map<String, Object> resource1 = new HashMap<>();
        resource1.put("id", "res001");
        resource1.put("title", "Knowledge Graph Construction Techniques");
        resource1.put("type", "pdf");
        resource1.put("author", "Prof. Wang");
        resource1.put("date", "2023-09-10");
        resource1.put("downloads", 125);
        resources.add(resource1);
        
        Map<String, Object> resource2 = new HashMap<>();
        resource2.put("id", "res002");
        resource2.put("title", "Graph Database Practical Tutorial");
        resource2.put("type", "video");
        resource2.put("author", "Prof. Zhao");
        resource2.put("date", "2023-08-15");
        resource2.put("views", 342);
        resources.add(resource2);
        
        // 添加更多学习资源
        Map<String, Object> resource3 = new HashMap<>();
        resource3.put("id", "res003");
        resource3.put("title", "深度学习基础教程");
        resource3.put("type", "pdf");
        resource3.put("author", "Prof. Li");
        resource3.put("date", "2023-10-20");
        resource3.put("downloads", 198);
        resources.add(resource3);
        
        Map<String, Object> resource4 = new HashMap<>();
        resource4.put("id", "res004");
        resource4.put("title", "神经网络算法实现代码示例");
        resource4.put("type", "code");
        resource4.put("author", "Prof. Zhang");
        resource4.put("date", "2023-11-05");
        resource4.put("downloads", 156);
        resources.add(resource4);
        
        Map<String, Object> resource5 = new HashMap<>();
        resource5.put("id", "res005");
        resource5.put("title", "计算机视觉与卷积神经网络");
        resource5.put("type", "video");
        resource5.put("author", "Prof. Chen");
        resource5.put("date", "2023-12-01");
        resource5.put("views", 285);
        resources.add(resource5);
        
        return resources;
    }
    
    /**
     * 获取常见问题分类
     */
    public List<Map<String, Object>> getFaqCategories() {
        // 模拟数据 - 实际应用中应从数据库获取
        List<Map<String, Object>> categories = new ArrayList<>();
        
        Map<String, Object> category1 = new HashMap<>();
        category1.put("id", "faq001");
        category1.put("name", "System Usage");
        List<Map<String, String>> questions1 = new ArrayList<>();
        Map<String, String> q1 = new HashMap<>();
        q1.put("question", "How to view the complete knowledge graph?");
        q1.put("answer", "Click the \"Knowledge Graph\" option in the navigation bar to view the complete knowledge point relationship network.");
        questions1.add(q1);
        
        Map<String, String> q2 = new HashMap<>();
        q2.put("question", "How to search for specific knowledge points?");
        q2.put("answer", "Click the \"Knowledge Search\" option in the navigation bar and enter keywords in the search box to find relevant knowledge points.");
        questions1.add(q2);
        category1.put("questions", questions1);
        categories.add(category1);
        
        Map<String, Object> category2 = new HashMap<>();
        category2.put("id", "faq002");
        category2.put("name", "Account Management");
        List<Map<String, String>> questions2 = new ArrayList<>();
        Map<String, String> q3 = new HashMap<>();
        q3.put("question", "How to modify personal information?");
        q3.put("answer", "Click the user avatar in the upper right corner, select \"Personal Center\", and then click the \"Edit Profile\" button to make modifications.");
        questions2.add(q3);
        category2.put("questions", questions2);
        categories.add(category2);
        
        return categories;
    }
} 