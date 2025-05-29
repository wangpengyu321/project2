package com.aigraph.system.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aigraph.system.model.User;
import com.aigraph.system.service.KnowledgeGraphDataService;
import com.aigraph.system.service.UserService;

/**
 * Controller for home page and authentication.
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private KnowledgeGraphDataService dataService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Check if username already exists
        if (userService.getUserByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error", "用户名已存在");
            return "redirect:/register";
        }
        
        // Set default role
        user.setRole("ROLE_STUDENT");
        
        // Create user
        User createdUser = userService.createUser(
            user.getUsername(), 
            user.getPassword(), 
            user.getFullName(), 
            user.getEmail(), 
            user.getRole()
        );
        
        if (createdUser != null) {
            redirectAttributes.addFlashAttribute("success", "注册成功，请登录");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "注册失败，请重试");
            return "redirect:/register";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 仪表盘");
        model.addAttribute("courseCount", dataService.getAllCourses().size());
        model.addAttribute("unitCount", dataService.getAllUnits().size());
        model.addAttribute("knowledgeNodeCount", dataService.getAllKnowledgeNodes().size());
        return "dashboard";
    }
    
    @GetMapping("/graph-view")
    public String graphView(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 知识图谱");
        model.addAttribute("mode", "full");
        return "graph-view";
    }
    
    @GetMapping("/search")
    public String search(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 知识检索");
        // 添加搜索相关的数据
        model.addAttribute("recentSearches", dataService.getRecentSearches(user.getId()));
        model.addAttribute("popularKeywords", dataService.getPopularKeywords());
        return "search";
    }
    
    @GetMapping("/analysis")
    public String analysis(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 关系分析");
        // 添加分析相关的数据
        model.addAttribute("knowledgeNodeCount", dataService.getAllKnowledgeNodes().size());
        model.addAttribute("relationshipCount", dataService.getRelationshipCount());
        return "analysis";
    }
    
    @GetMapping("/courses")
    public String courses(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 课程内容");
        model.addAttribute("courses", dataService.getAllCourses());
        return "courses";
    }
    
    @GetMapping("/settings")
    public String settings(Model model) {
        // Add any necessary data for the settings page
        String username = "zhang_san"; // Normally fetched from authentication
        
        // Set model attributes
        model.addAttribute("pageTitle", "系统设置");
        model.addAttribute("username", username);
        
        return "settings";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 个人中心");
        // 添加用户统计数据
        model.addAttribute("learningProgress", userService.getUserLearningProgress(user.getId()));
        model.addAttribute("completedCourses", userService.getCompletedCourses(user.getId()));
        return "profile";
    }
    
    @GetMapping("/help")
    public String help(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 帮助中心");
        model.addAttribute("faqCategories", dataService.getFaqCategories());
        return "help";
    }
    
    @GetMapping("/qa")
    public String qa(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - AI问答");
        return "qa";
    }
    
    @GetMapping("/visualization")
    public String visualization(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageTitle", "知识图谱系统 - 数据可视化");
        
        // 添加可视化所需的数据
        model.addAttribute("nodeCount", dataService.getAllKnowledgeNodes().size());
        model.addAttribute("relationshipCount", dataService.getRelationshipCount());
        model.addAttribute("courseCount", dataService.getAllCourses().size());
        model.addAttribute("unitCount", dataService.getAllUnits().size());
        
        return "visualization";
    }
    
    @GetMapping("/graph-test")
    public String graphTest(Model model) {
        // Set mode to full graph view as an example
        model.addAttribute("mode", "full");
        return "graph-view";
    }
    
    @GetMapping("/admin")
    public String admin(Model model) {
        // Add necessary data for the admin page
        model.addAttribute("pageTitle", "系统管理");
        
        // In a real application, you would fetch these from services
        model.addAttribute("userCount", 120);
        model.addAttribute("knowledgePointCount", 356);
        model.addAttribute("relationshipCount", 892);
        model.addAttribute("courseCount", 15);
        model.addAttribute("resourceCount", 208);
        
        return "admin";
    }
}