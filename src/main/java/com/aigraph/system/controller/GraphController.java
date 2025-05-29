package com.aigraph.system.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aigraph.system.model.KnowledgeNode;
import com.aigraph.system.model.User;
import com.aigraph.system.service.GraphService;
import com.aigraph.system.service.KnowledgeGraphDataService;
import com.aigraph.system.service.LearningPathService;
import com.aigraph.system.service.UserService;

/**
 * Controller for knowledge graph visualization.
 */
@Controller
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private GraphService graphService;
    
    @Autowired
    private KnowledgeGraphDataService dataService;
    
    @Autowired
    private LearningPathService learningPathService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/view")
    public String viewGraph(Model model) {
        model.addAttribute("courses", dataService.getAllCourses());
        model.addAttribute("units", dataService.getAllUnits());
        model.addAttribute("mode", "full");
        return "graph-view";
    }
    
    @GetMapping("/node/{nodeId}")
    public String viewNodeGraph(@PathVariable String nodeId, 
                              @RequestParam(defaultValue = "2") int depth,
                              Model model, Principal principal) {
        KnowledgeNode node = dataService.getKnowledgeNodeById(nodeId);
        if (node == null) {
            return "redirect:/graph/view";
        }
        
        // Mark node as viewed for the current user
        User user = userService.getUserByUsername(principal.getName());
        user.addViewedKnowledgeNode(nodeId);
        userService.updateUser(user);
        
        model.addAttribute("node", node);
        model.addAttribute("depth", depth);
        model.addAttribute("mode", "node");
        return "graph-view";
    }
    
    @GetMapping("/unit/{unitId}")
    public String viewUnitGraph(@PathVariable String unitId, Model model) {
        if (dataService.getUnitById(unitId) == null) {
            return "redirect:/graph/view";
        }
        
        model.addAttribute("unit", dataService.getUnitById(unitId));
        model.addAttribute("mode", "unit");
        return "graph-view";
    }
    
    @GetMapping("/learning-path")
    public String viewLearningPath(
            @RequestParam(required = false) String startNodeId, 
            Model model, Principal principal) {
        
        model.addAttribute("knowledgeNodes", dataService.getAllKnowledgeNodes());
        model.addAttribute("startNodeId", startNodeId);
        model.addAttribute("mode", "learning-path");
        
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("viewedNodes", user.getViewedKnowledgeNodes());
        
        return "graph-view";
    }
} 