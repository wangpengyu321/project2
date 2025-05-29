# 基于知识图谱的大数据技术教学系统

![Version](https://img.shields.io/badge/Version-1.0.0-blue)
![License](https://img.shields.io/badge/License-MIT-green)

一个智能化知识图谱系统，用于大数据技术的教学和学习辅助。该系统结合了知识图谱、图神经网络和教育教学相关技术，为教学过程提供智能化支持。

## 功能特点

- **知识图谱构建**：自动从教材、PPT和教学大纲中提取知识点，构建结构化知识图谱
- **多源数据集成**：支持从文本、PPT、视频等多种数据源提取知识点
- **知识关联分析**：使用图神经网络技术分析知识点间的关联关系
- **知识点可视化**：提供直观的知识图谱可视化界面
- **智能教学辅助**：基于知识图谱提供学习路径推荐和知识点解析
- **用户友好界面**：基于Web的现代化用户界面

## 系统架构

系统采用前后端分离架构:

- **前端**：使用Bootstrap、D3.js构建的响应式Web界面
- **后端**：基于Spring Boot的RESTful API服务
- **数据存储**：Neo4j图数据库
- **知识提取**：基于Python的自然语言处理模块
- **推理引擎**：基于图神经网络（GraphSAGE）的知识关联分析模块

## 技术栈

### 后端
- Java 8
- Spring Boot 2.7.x
- Spring Security
- Neo4j图数据库
- Python 3.7+
- PyTorch & PyTorch Geometric
- jieba分词

### 前端
- Bootstrap 5.3
- D3.js 5.16
- Thymeleaf模板引擎
- jQuery

### 工具/库
- Maven
- Lombok
- Jackson
- pdfminer.six
- python-pptx
- py2neo

## 快速开始

### 环境准备

1. 安装Java 8+和Python 3.7+
2. 安装Neo4j数据库
3. 安装必要的Python依赖：
   ```bash
   pip install pandas numpy torch torch-geometric py2neo jieba pdfminer.six python-pptx scikit-learn
   ```
4. 安装FFmpeg（用于视频处理，可选）

### 系统配置

1. 克隆本仓库：
   ```bash
   git clone https://github.com/yourusername/knowledge-graph-system.git
   cd knowledge-graph-system
   ```

2. 配置Neo4j：
   - 启动Neo4j服务
   - 设置默认用户名和密码（默认为neo4j/password）

3. 准备数据：
   - 在`data/`目录下放置教材PDF、PPT课件和教学大纲

4. 构建并运行Spring Boot应用：
   ```bash
   mvn clean package
   java -jar target/aigraph-system-1.0.0.jar
   ```

5. 运行Python知识提取模块：
   ```bash
   python knowledge_graph_system.py
   ```

### 访问系统

访问 `http://localhost:8080` 进入系统主页。

## 系统使用

1. **知识图谱构建**：
   - 上传教材、PPT和教学大纲
   - 系统自动提取知识点并构建图谱

2. **知识图谱浏览**：
   - 可视化浏览知识点及其关联
   - 支持缩放、拖拽和搜索

3. **知识点查询**：
   - 输入关键词查询相关知识点
   - 展示知识点详细信息和相关资源

4. **学习路径推荐**：
   - 基于知识图谱推荐个性化学习路径
   - 提供知识依赖关系分析

## 项目结构

```
knowledge-graph-system/
├── data/                  # 数据目录
├── src/                   # 源代码
│   ├── main/
│   │   ├── java/         # Java后端代码
│   │   └── resources/    # 资源文件
├── target/                # 编译输出
├── knowledge_graph_system.py  # Python知识提取模块
├── enhanced_ai_course_kg.cypher  # Neo4j Cypher脚本
├── system_startup_guide.md  # 系统启动指南
├── pom.xml                # Maven配置
└── README.md              # 项目说明
```

## 开发指南

### 扩展知识点提取规则

修改`knowledge_graph_system.py`中的`extract_from_textbook`和`extract_from_ppt`方法，调整正则表达式和过滤条件。

### 优化图神经网络模型

可优化`GraphSAGE`模型的实现，包括：
- 文本特征向量化（如使用Word2Vec或BERT）
- 模型参数调优
- 交叉验证

## 常见问题

1. **Neo4j连接错误**：确保Neo4j服务已启动，且连接参数正确
2. **依赖包安装失败**：尝试使用`pip install --upgrade pip`更新pip后重新安装
3. **FFmpeg命令未找到**：确保FFmpeg已正确安装并添加到系统PATH
4. **内存不足**：处理大量数据时，可能需要增加系统内存或减少批处理大小

## 贡献指南

我们欢迎各种形式的贡献，包括但不限于：
- 报告问题和提出建议
- 提交代码改进
- 完善文档

## 许可证

本项目采用MIT许可证。详见 [LICENSE](LICENSE) 文件。

## 联系方式

如有问题或建议，请通过以下方式联系我们：
- Email: your.email@example.com
- GitHub Issues: https://github.com/yourusername/knowledge-graph-system/issues
