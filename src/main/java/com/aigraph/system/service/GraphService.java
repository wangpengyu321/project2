package com.aigraph.system.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aigraph.system.model.KnowledgeNode;
import com.aigraph.system.model.KnowledgeNode.Relationship;
import com.aigraph.system.model.graph.GraphData;
import com.aigraph.system.model.graph.Link;
import com.aigraph.system.model.graph.Node;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for generating graph visualization data.
 */
@Service
@Slf4j
public class GraphService {

    @Autowired
    private KnowledgeGraphDataService dataService;
    
    /**
     * Generates full graph data for visualization.
     */
    public GraphData generateFullGraph() {
        GraphData graphData = new GraphData();
        Map<String, KnowledgeNode> knowledgeNodes = dataService.getKnowledgeNodes();
        
        // Add all nodes
        for (KnowledgeNode knowledgeNode : knowledgeNodes.values()) {
            Node node = new Node(
                knowledgeNode.getId(),
                knowledgeNode.getName(),
                knowledgeNode.getType(),
                knowledgeNode.getDescription(),
                knowledgeNode.getDifficulty(),
                knowledgeNode.getChapter()
            );
            
            graphData.addNode(node);
            
            // Add links
            for (Relationship relationship : knowledgeNode.getRelationships()) {
                Link link = new Link(
                    knowledgeNode.getId(),
                    relationship.getTargetId(),
                    relationship.getType(),
                    relationship.getDescription()
                );
                
                graphData.addLink(link);
            }
        }
        
        return graphData;
    }
    
    /**
     * Generates graph data for a specific unit.
     */
    public GraphData generateUnitGraph(String unitId) {
        GraphData graphData = new GraphData();
        
        if (dataService.getUnitById(unitId) == null) {
            log.warn("Unit not found: {}", unitId);
            return graphData;
        }
        
        Set<String> unitNodeIds = new HashSet<>();
        for (KnowledgeNode node : dataService.getUnitById(unitId).getKnowledgeNodes()) {
            unitNodeIds.add(node.getId());
            
            Node graphNode = new Node(
                node.getId(),
                node.getName(),
                node.getType(),
                node.getDescription(),
                node.getDifficulty(),
                node.getChapter()
            );
            
            graphData.addNode(graphNode);
        }
        
        // Add links between nodes in this unit
        for (String nodeId : unitNodeIds) {
            KnowledgeNode knowledgeNode = dataService.getKnowledgeNodeById(nodeId);
            
            for (Relationship relationship : knowledgeNode.getRelationships()) {
                if (unitNodeIds.contains(relationship.getTargetId())) {
                    Link link = new Link(
                        knowledgeNode.getId(),
                        relationship.getTargetId(),
                        relationship.getType(),
                        relationship.getDescription()
                    );
                    
                    graphData.addLink(link);
                }
            }
        }
        
        return graphData;
    }
    
    /**
     * Generates graph data for a specific knowledge node and its direct relationships.
     */
    public GraphData generateNodeGraph(String nodeId, int depth) {
        GraphData graphData = new GraphData();
        
        KnowledgeNode startNode = dataService.getKnowledgeNodeById(nodeId);
        if (startNode == null) {
            log.warn("Knowledge node not found: {}", nodeId);
            return graphData;
        }
        
        Set<String> processedNodes = new HashSet<>();
        expandNodeGraph(startNode, graphData, processedNodes, depth);
        
        return graphData;
    }
    
    private void expandNodeGraph(KnowledgeNode knowledgeNode, GraphData graphData, Set<String> processedNodes, int depth) {
        if (depth <= 0 || processedNodes.contains(knowledgeNode.getId())) {
            return;
        }
        
        processedNodes.add(knowledgeNode.getId());
        
        // Add the current node
        Node node = new Node(
            knowledgeNode.getId(),
            knowledgeNode.getName(),
            knowledgeNode.getType(),
            knowledgeNode.getDescription(),
            knowledgeNode.getDifficulty(),
            knowledgeNode.getChapter()
        );
        
        graphData.addNode(node);
        
        // Add relationships and related nodes
        for (Relationship relationship : knowledgeNode.getRelationships()) {
            KnowledgeNode targetNode = dataService.getKnowledgeNodeById(relationship.getTargetId());
            if (targetNode != null) {
                Link link = new Link(
                    knowledgeNode.getId(),
                    targetNode.getId(),
                    relationship.getType(),
                    relationship.getDescription()
                );
                
                graphData.addLink(link);
                
                // Recursively expand target node if depth allows
                expandNodeGraph(targetNode, graphData, processedNodes, depth - 1);
            }
        }
        
        // Find incoming relationships (nodes that relate to this one)
        for (KnowledgeNode otherNode : dataService.getAllKnowledgeNodes()) {
            if (otherNode.getId().equals(knowledgeNode.getId())) {
                continue;
            }
            
            for (Relationship otherRelationship : otherNode.getRelationships()) {
                if (otherRelationship.getTargetId().equals(knowledgeNode.getId())) {
                    // Add this incoming relationship
                    Link link = new Link(
                        otherNode.getId(),
                        knowledgeNode.getId(),
                        otherRelationship.getType(),
                        otherRelationship.getDescription()
                    );
                    
                    graphData.addLink(link);
                    
                    // Add the source node if not already processed
                    if (!processedNodes.contains(otherNode.getId())) {
                        Node otherGraphNode = new Node(
                            otherNode.getId(),
                            otherNode.getName(),
                            otherNode.getType(),
                            otherNode.getDescription(),
                            otherNode.getDifficulty(),
                            otherNode.getChapter()
                        );
                        
                        graphData.addNode(otherGraphNode);
                        
                        // Recursively expand source node if depth allows
                        expandNodeGraph(otherNode, graphData, processedNodes, depth - 1);
                    }
                }
            }
        }
    }
    
    /**
     * Generates recommended learning path graph.
     */
    public GraphData generateLearningPathGraph(List<String> nodeIds) {
        GraphData graphData = new GraphData();
        
        if (nodeIds == null || nodeIds.isEmpty()) {
            return graphData;
        }
        
        // Add all nodes in the path
        for (int i = 0; i < nodeIds.size(); i++) {
            String nodeId = nodeIds.get(i);
            KnowledgeNode knowledgeNode = dataService.getKnowledgeNodeById(nodeId);
            
            if (knowledgeNode != null) {
                Node node = new Node(
                    knowledgeNode.getId(),
                    knowledgeNode.getName(),
                    knowledgeNode.getType(),
                    knowledgeNode.getDescription(),
                    knowledgeNode.getDifficulty(),
                    knowledgeNode.getChapter()
                );
                
                graphData.addNode(node);
                
                // Add links to create the path
                if (i < nodeIds.size() - 1) {
                    String nextNodeId = nodeIds.get(i + 1);
                    
                    Link link = new Link(
                        nodeId,
                        nextNodeId,
                        "LEARNING_PATH",
                        "推荐学习路径"
                    );
                    
                    graphData.addLink(link);
                }
            }
        }
        
        return graphData;
    }
} 