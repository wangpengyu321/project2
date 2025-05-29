# 知识图谱系统启动指南

本指南将帮助您启动基于知识图谱的大数据技术教学系统。

## 1. 环境准备

### 1.1 安装依赖包

首先，确保您已安装Python 3.7或更高版本，然后安装所需的依赖包：

```bash
pip install pandas numpy torch torch-geometric py2neo jieba pdfminer.six python-pptx scikit-learn
```

### 1.2 安装Neo4j数据库

1. 从[Neo4j官网](https://neo4j.com/download/)下载并安装Neo4j Desktop
2. 创建一个新的数据库实例，设置用户名为"neo4j"，密码为"password"（或修改代码中的连接参数）
3. 启动Neo4j数据库服务

### 1.3 安装FFmpeg（用于视频处理）

- Windows: 从[FFmpeg官网](https://ffmpeg.org/download.html)下载，并将其添加到系统PATH
- Linux: `sudo apt-get install ffmpeg`
- macOS: `brew install ffmpeg`

## 2. 数据准备

在项目根目录下创建以下文件夹结构：

```
data/
  ├── textbook.pdf     # 课程教材PDF文件
  ├── ppt/             # 存放课件的文件夹
  │   ├── lecture1.pptx
  │   ├── lecture2.pptx
  │   └── ...
  ├── syllabus.xlsx    # 教学大纲Excel文件
  └── videos/          # 可选：教学视频文件夹
      ├── lecture1.mp4
      └── ...
```

## 3. 系统配置

如需修改系统配置，请在`knowledge_graph_system.py`文件中调整以下参数：

- Neo4j连接参数：修改`KnowledgeGraphBuilder`类的`__init__`方法中的URI、用户名和密码
- 文本长度过滤阈值：修改`DataCleaner`类中的`min_length`和`max_length`
- 知识点相似度阈值：修改`_build_relationships`方法中的相似度阈值（默认0.5）

## 4. 启动系统

### 4.1 基本启动

直接运行Python脚本即可启动系统：

```bash
python knowledge_graph_system.py
```

系统将按照以下流程执行：
1. 从教材、PPT和教学大纲中提取知识点
2. 清洗和规范化数据
3. 构建知识图谱
4. 训练GraphSAGE模型
5. 评估系统性能

### 4.2 视频数据处理（可选）

如需处理视频数据，请在代码中调用`extract_from_video`方法：

```python
# 在main函数中添加
video_path = "data/videos/lecture1.mp4"
api_key = "您的科大讯飞API密钥"
video_data = collector.extract_from_video(video_path, api_key)
all_data.extend([item["text"] for item in video_data])
```

## 5. 查看结果

### 5.1 控制台输出

系统运行过程中会在控制台输出各阶段的处理结果，包括：
- 收集到的原始知识点数量
- 清洗后的知识点数量
- 知识图谱构建进度
- 模型评估结果

### 5.2 访问Neo4j数据库

启动系统后，可以通过Neo4j Browser查看构建的知识图谱：

1. 打开Neo4j Browser（通常是http://localhost:7474）
2. 使用配置的用户名和密码登录
3. 执行Cypher查询语句查看数据：
   ```
   MATCH (c:Course)-[:CONTAINS]->(k:KnowledgeNode) RETURN c, k LIMIT 25
   ```
   或
   ```
   MATCH (k1:KnowledgeNode)-[r:RELATED_TO]->(k2:KnowledgeNode) RETURN k1, r, k2 LIMIT 25
   ```

## 6. 常见问题

1. **Neo4j连接错误**：确保Neo4j服务已启动，且连接参数正确
2. **依赖包安装失败**：尝试使用`pip install --upgrade pip`更新pip后重新安装
3. **FFmpeg命令未找到**：确保FFmpeg已正确安装并添加到系统PATH
4. **内存不足**：处理大量数据时，可能需要增加系统内存或减少批处理大小

## 7. 进阶使用

### 7.1 自定义知识点提取规则

修改`extract_from_textbook`和`extract_from_ppt`方法中的正则表达式和过滤条件，以适应不同格式的教材和课件。

### 7.2 优化图神经网络模型

在实际应用中，您可能需要实现更完整的GraphSAGE模型训练流程，包括：
- 文本特征向量化（如使用Word2Vec或BERT）
- 模型参数调优
- 交叉验证

### 7.3 开发Web界面

可以基于此系统开发Web界面，使用Flask或Django作为后端，使用D3.js或ECharts实现知识图谱的可视化展示。 