package com.aigraph.system.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aigraph.system.model.KnowledgeNode;
import com.aigraph.system.model.User;
import com.aigraph.system.model.graph.GraphData;
import com.aigraph.system.service.AIService;
import com.aigraph.system.service.GraphService;
import com.aigraph.system.service.KnowledgeGraphDataService;
import com.aigraph.system.service.LearningPathService;
import com.aigraph.system.service.UserService;

/**
 * REST controller for knowledge graph API.
 */
@RestController
@RequestMapping("/aigraph/api")
public class ApiController {

    @Autowired
    private GraphService graphService;
    
    @Autowired
    private KnowledgeGraphDataService dataService;
    
    @Autowired
    private LearningPathService learningPathService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AIService aiService;
    
    @GetMapping("/graph/full")
    public ResponseEntity<GraphData> getFullGraph() {
        return ResponseEntity.ok(graphService.generateFullGraph());
    }
    
    @GetMapping("/graph/node/{nodeId}")
    public ResponseEntity<GraphData> getNodeGraph(
            @PathVariable String nodeId,
            @RequestParam(defaultValue = "2") int depth) {
        
        return ResponseEntity.ok(graphService.generateNodeGraph(nodeId, depth));
    }
    
    @GetMapping("/graph/unit/{unitId}")
    public ResponseEntity<GraphData> getUnitGraph(@PathVariable String unitId) {
        return ResponseEntity.ok(graphService.generateUnitGraph(unitId));
    }
    
    @GetMapping("/graph/learning-path")
    public ResponseEntity<GraphData> getLearningPathGraph(
            @RequestParam(required = false) String startNodeId) {
        
        List<String> path;
        if (startNodeId != null && !startNodeId.isEmpty()) {
            path = learningPathService.generatePathFromNode(startNodeId);
        } else {
            path = learningPathService.generateBasicLearningPath();
        }
        
        return ResponseEntity.ok(graphService.generateLearningPathGraph(path));
    }
    
    @GetMapping("/node/{nodeId}")
    public ResponseEntity<KnowledgeNode> getNodeDetails(@PathVariable String nodeId) {
        KnowledgeNode node = dataService.getKnowledgeNodeById(nodeId);
        if (node != null) {
            return ResponseEntity.ok(node);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/user/favorite/add/{nodeId}")
    public ResponseEntity<Map<String, Object>> addFavorite(
            @PathVariable String nodeId, Principal principal) {
        
        User user = userService.getUserByUsername(principal.getName());
        user.addFavoriteKnowledgeNode(nodeId);
        userService.updateUser(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("favorites", user.getFavoriteKnowledgeNodes());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/user/favorite/remove/{nodeId}")
    public ResponseEntity<Map<String, Object>> removeFavorite(
            @PathVariable String nodeId, Principal principal) {
        
        User user = userService.getUserByUsername(principal.getName());
        user.removeFavoriteKnowledgeNode(nodeId);
        userService.updateUser(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("favorites", user.getFavoriteKnowledgeNodes());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/ai/chat")
    public ResponseEntity<Map<String, Object>> chatWithAI(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String response = aiService.getResponse(message);
        
        Map<String, Object> result = new HashMap<>();
        result.put("response", response);
        
        return ResponseEntity.ok(result);
    }
} 