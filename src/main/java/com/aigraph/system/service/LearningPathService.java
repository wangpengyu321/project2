package com.aigraph.system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aigraph.system.model.KnowledgeNode;
import com.aigraph.system.model.KnowledgeNode.Relationship;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for generating and managing learning paths.
 */
@Service
@Slf4j
public class LearningPathService {

    @Autowired
    private KnowledgeGraphDataService dataService;
    
    /**
     * Generates a recommended learning path based on difficulty.
     */
    public List<String> generateBasicLearningPath() {
        List<KnowledgeNode> allNodes = dataService.getAllKnowledgeNodes();
        
        // Sort nodes by difficulty and chapter order
        Collections.sort(allNodes, Comparator.comparing(KnowledgeNode::getDifficulty)
                .thenComparing(node -> {
                    String chapter = node.getChapter();
                    if (chapter.startsWith("第一单元")) return 1;
                    if (chapter.startsWith("第二单元")) return 2;
                    if (chapter.startsWith("第三单元")) return 3;
                    if (chapter.startsWith("第四单元")) return 4;
                    if (chapter.startsWith("第五单元")) return 5;
                    if (chapter.startsWith("第六单元")) return 6;
                    if (chapter.startsWith("第七单元")) return 7;
                    if (chapter.startsWith("第八单元")) return 8;
                    return 9;
                }));
        
        List<String> path = new ArrayList<>();
        for (KnowledgeNode node : allNodes) {
            path.add(node.getId());
        }
        
        return path;
    }
    
    /**
     * Generates a learning path starting from a specific node.
     */
    public List<String> generatePathFromNode(String startNodeId) {
        KnowledgeNode startNode = dataService.getKnowledgeNodeById(startNodeId);
        if (startNode == null) {
            log.warn("Start node not found: {}", startNodeId);
            return new ArrayList<>();
        }
        
        // Use priority queue for Dijkstra's algorithm
        PriorityQueue<PathNode> queue = new PriorityQueue<>(
            Comparator.comparingInt(PathNode::getCost)
        );
        
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        Map<String, Integer> costs = new HashMap<>();
        
        // Initialize
        for (KnowledgeNode node : dataService.getAllKnowledgeNodes()) {
            costs.put(node.getId(), Integer.MAX_VALUE);
        }
        costs.put(startNodeId, 0);
        queue.add(new PathNode(startNodeId, 0));
        
        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            String currentId = current.getNodeId();
            
            if (visited.contains(currentId)) {
                continue;
            }
            
            visited.add(currentId);
            
            KnowledgeNode currentNode = dataService.getKnowledgeNodeById(currentId);
            
            // Process outgoing relationships
            for (Relationship relationship : currentNode.getRelationships()) {
                String targetId = relationship.getTargetId();
                
                // Skip if already visited
                if (visited.contains(targetId)) {
                    continue;
                }
                
                KnowledgeNode targetNode = dataService.getKnowledgeNodeById(targetId);
                if (targetNode == null) {
                    continue;
                }
                
                // Calculate cost (based on difficulty and whether it's a prerequisite)
                int edgeCost = targetNode.getDifficulty();
                if (relationship.getType().equals("PREREQUISITE")) {
                    edgeCost = 1; // Lower cost for prerequisite relationships
                }
                
                int newCost = costs.get(currentId) + edgeCost;
                
                if (newCost < costs.get(targetId)) {
                    costs.put(targetId, newCost);
                    previous.put(targetId, currentId);
                    queue.add(new PathNode(targetId, newCost));
                }
            }
        }
        
        // Build learning path based on remaining unvisited nodes
        List<KnowledgeNode> remainingNodes = new ArrayList<>();
        for (KnowledgeNode node : dataService.getAllKnowledgeNodes()) {
            if (!visited.contains(node.getId())) {
                remainingNodes.add(node);
            }
        }
        
        // Sort remaining nodes by difficulty and chapter
        Collections.sort(remainingNodes, Comparator.comparing(KnowledgeNode::getDifficulty)
                .thenComparing(node -> {
                    String chapter = node.getChapter();
                    if (chapter.startsWith("第一单元")) return 1;
                    if (chapter.startsWith("第二单元")) return 2;
                    if (chapter.startsWith("第三单元")) return 3;
                    if (chapter.startsWith("第四单元")) return 4;
                    if (chapter.startsWith("第五单元")) return 5;
                    if (chapter.startsWith("第六单元")) return 6;
                    if (chapter.startsWith("第七单元")) return 7;
                    if (chapter.startsWith("第八单元")) return 8;
                    return 9;
                }));
        
        // Create final path
        List<String> path = new ArrayList<>();
        path.add(startNodeId);
        
        // Add visited nodes based on shortest path
        Set<String> addedNodes = new HashSet<>();
        addedNodes.add(startNodeId);
        
        for (String nodeId : costs.keySet()) {
            if (costs.get(nodeId) < Integer.MAX_VALUE && !nodeId.equals(startNodeId)) {
                // Reconstruct path to this node
                List<String> nodePath = new ArrayList<>();
                String current = nodeId;
                
                while (current != null && !current.equals(startNodeId)) {
                    nodePath.add(current);
                    current = previous.get(current);
                }
                
                // Add nodes in reverse order (from start to end)
                Collections.reverse(nodePath);
                
                for (String pathNodeId : nodePath) {
                    if (!addedNodes.contains(pathNodeId)) {
                        path.add(pathNodeId);
                        addedNodes.add(pathNodeId);
                    }
                }
            }
        }
        
        // Add remaining nodes
        for (KnowledgeNode node : remainingNodes) {
            if (!addedNodes.contains(node.getId())) {
                path.add(node.getId());
            }
        }
        
        return path;
    }
    
    /**
     * Helper class for Dijkstra's algorithm.
     */
    private static class PathNode {
        private String nodeId;
        private int cost;
        
        public PathNode(String nodeId, int cost) {
            this.nodeId = nodeId;
            this.cost = cost;
        }
        
        public String getNodeId() {
            return nodeId;
        }
        
        public int getCost() {
            return cost;
        }
    }
} 