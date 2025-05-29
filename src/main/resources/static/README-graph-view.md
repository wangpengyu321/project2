# Knowledge Graph Visualization

## Overview

The graph-view template provides an interactive visualization of the knowledge graph, allowing users to explore the relationships between knowledge nodes, units, and learning paths.

## Features

- **Multiple View Modes**:
  - Full Knowledge Graph View
  - Node-Specific Graph View (with customizable depth)
  - Unit-Specific Graph View
  - Learning Path Visualization

- **Interactive Controls**:
  - Zoom in/out and pan
  - Node selection and details display
  - Relationship exploration

- **Node Interaction**:
  - Click on nodes to view detailed information
  - Add nodes to favorites
  - View related nodes

## Technical Implementation

The visualization is built using:
- D3.js for force-directed graph rendering
- Bootstrap for responsive layout
- Thymeleaf for server-side template processing

## Usage

### Controller Endpoints

1. **Full Graph View**
   ```java
   @GetMapping("/graph/view")
   public String viewGraph(Model model) {
       model.addAttribute("mode", "full");
       return "graph-view";
   }
   ```

2. **Node-Specific Graph**
   ```java
   @GetMapping("/graph/node/{nodeId}")
   public String viewNodeGraph(@PathVariable String nodeId, 
                             @RequestParam(defaultValue = "2") int depth,
                             Model model) {
       model.addAttribute("node", node);
       model.addAttribute("depth", depth);
       model.addAttribute("mode", "node");
       return "graph-view";
   }
   ```

3. **Unit-Specific Graph**
   ```java
   @GetMapping("/graph/unit/{unitId}")
   public String viewUnitGraph(@PathVariable String unitId, Model model) {
       model.addAttribute("unit", unit);
       model.addAttribute("mode", "unit");
       return "graph-view";
   }
   ```

4. **Learning Path Visualization**
   ```java
   @GetMapping("/graph/learning-path")
   public String viewLearningPath(
           @RequestParam(required = false) String startNodeId, 
           Model model) {
       model.addAttribute("startNodeId", startNodeId);
       model.addAttribute("mode", "learning-path");
       return "graph-view";
   }
   ```

### API Endpoints

The graph data is loaded asynchronously via the following API endpoints:

1. `/api/graph/full` - Returns the full knowledge graph
2. `/api/graph/node/{nodeId}?depth={depth}` - Returns a node-centered subgraph
3. `/api/graph/unit/{unitId}` - Returns a unit-specific subgraph
4. `/api/graph/learning-path?startNodeId={startNodeId}` - Returns a learning path

## Testing

You can test the graph visualization by accessing `/aigraph/graph-test` which renders a full graph view.

## Customization

The visualization can be customized by modifying:
- The graph layout parameters in the D3.js force simulation
- The color schemes for nodes and links
- The styling of the graph container and controls 