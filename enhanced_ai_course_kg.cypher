// 清空数据库
MATCH (n) DETACH DELETE n;

// 创建约束
CREATE CONSTRAINT IF NOT EXISTS FOR (node:KnowledgeNode) REQUIRE node.id IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (node:Course) REQUIRE node.id IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (node:Unit) REQUIRE node.id IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (node:Lecture) REQUIRE node.id IS UNIQUE;

// 创建课程
CREATE (course:Course {
    id: "big-data-tech",
    name: "大数据技术",
    description: "河南省本科高校研究性教学示范课程，探讨大数据技术的基础理论和应用",
    level: "本科"
})

// 创建单元
CREATE (unit1:Unit {
    id: "unit-1",
    name: "第一单元：大数据概述",
    description: "介绍大数据的基本概念、特征和应用场景",
    order: 1
})

CREATE (unit2:Unit {
    id: "unit-2",
    name: "第二单元：数据采集与预处理",
    description: "介绍数据采集、清洗和转换的基本方法和技术",
    order: 2
})

CREATE (unit3:Unit {
    id: "unit-3",
    name: "第三单元：分布式存储系统",
    description: "介绍HDFS、HBase等分布式存储系统的架构和原理",
    order: 3
})

CREATE (unit4:Unit {
    id: "unit-4",
    name: "第四单元：分布式计算框架",
    description: "介绍MapReduce、Spark等分布式计算框架的原理和应用",
    order: 4
})

CREATE (unit5:Unit {
    id: "unit-5",
    name: "第五单元：数据仓库与数据湖",
    description: "介绍数据仓库、数据湖的概念、架构和实现技术",
    order: 5
})

CREATE (unit6:Unit {
    id: "unit-6",
    name: "第六单元：数据挖掘与分析",
    description: "介绍常用数据挖掘算法和分析方法在大数据场景中的应用",
    order: 6
})

CREATE (unit7:Unit {
    id: "unit-7",
    name: "第七单元：数据可视化技术",
    description: "介绍大数据可视化的基本概念、方法和工具",
    order: 7
})

CREATE (unit8:Unit {
    id: "unit-8",
    name: "第八单元：大数据应用实践",
    description: "介绍大数据在各领域的实际应用案例和实践",
    order: 8
});

// 将单元添加到课程
MATCH (course:Course {id: 'big-data-tech'})
MATCH (unit1:Unit {id: 'unit-1'})
MATCH (unit2:Unit {id: 'unit-2'})
MATCH (unit3:Unit {id: 'unit-3'})
MATCH (unit4:Unit {id: 'unit-4'})
MATCH (unit5:Unit {id: 'unit-5'})
MATCH (unit6:Unit {id: 'unit-6'})
MATCH (unit7:Unit {id: 'unit-7'})
MATCH (unit8:Unit {id: 'unit-8'})
CREATE (course)-[:CONTAINS {order: 1}]->(unit1)
CREATE (course)-[:CONTAINS {order: 2}]->(unit2)
CREATE (course)-[:CONTAINS {order: 3}]->(unit3)
CREATE (course)-[:CONTAINS {order: 4}]->(unit4)
CREATE (course)-[:CONTAINS {order: 5}]->(unit5)
CREATE (course)-[:CONTAINS {order: 6}]->(unit6)
CREATE (course)-[:CONTAINS {order: 7}]->(unit7)
CREATE (course)-[:CONTAINS {order: 8}]->(unit8);

// 创建基础知识点节点
// 第一单元：大数据概述
CREATE (big_data_def:KnowledgeNode {
    id: "big-data-definition",
    name: "大数据的定义",
    type: "基础概念",
    description: "大数据指无法在一定时间范围内用常规软件工具进行捕捉、管理和处理的数据集合。2011年麦肯锡全球研究所首次提出并定义。",
    difficulty: 1,
    chapter: "第一单元",
    keywords: ["大数据", "定义", "数据集合", "麦肯锡"]
})

CREATE (big_data_chars:KnowledgeNode {
    id: "big-data-characteristics",
    name: "大数据特征",
    type: "基础概念",
    description: "大数据通常具有4V特征：Volume（数据量大）、Variety（数据类型多样）、Velocity（处理速度快）、Value（价值密度低）。有些学者还增加了Veracity（数据真实性）作为第五个特征。",
    difficulty: 1,
    chapter: "第一单元",
    keywords: ["4V特征", "数据量", "多样性", "速度", "价值"]
})

CREATE (big_data_history:KnowledgeNode {
    id: "big-data-history",
    name: "大数据发展历史",
    type: "历史背景",
    description: "大数据技术从2000年代初期开始发展，经历了从传统数据库到分布式计算、云计算再到当前人工智能赋能的几个发展阶段。",
    difficulty: 1,
    chapter: "第一单元",
    keywords: ["发展历史", "演进", "数据库", "分布式计算", "云计算"]
})

CREATE (big_data_pioneers:KnowledgeNode {
    id: "big-data-pioneers",
    name: "大数据先驱",
    type: "历史人物",
    description: "大数据领域的重要先驱包括Doug Cutting（Hadoop创始人）、Jeffrey Dean（MapReduce发明者之一）、Matei Zaharia（Spark创始人）等。",
    difficulty: 1,
    chapter: "第一单元",
    keywords: ["Doug Cutting", "Jeffrey Dean", "Matei Zaharia", "先驱"]
})

CREATE (data_lifecycle:KnowledgeNode {
    id: "data-lifecycle",
    name: "数据生命周期",
    type: "理论概念",
    description: "数据生命周期包括数据的产生、采集、存储、处理、分析和应用六个阶段，构成大数据处理的完整流程。",
    difficulty: 2,
    chapter: "第一单元",
    keywords: ["数据生命周期", "数据流程", "数据管理", "阶段"]
})

CREATE (big_data_types:KnowledgeNode {
    id: "big-data-types",
    name: "大数据分类",
    type: "基础概念",
    description: "根据数据结构可将大数据分为结构化数据、半结构化数据和非结构化数据三种类型。",
    difficulty: 2,
    chapter: "第一单元",
    keywords: ["结构化数据", "半结构化数据", "非结构化数据", "数据分类"]
})

CREATE (big_data_factors:KnowledgeNode {
    id: "big-data-growth-factors",
    name: "大数据发展因素",
    type: "发展背景",
    description: "影响大数据发展的四大关键因素：互联网普及、物联网发展、存储成本下降和计算能力提升。",
    difficulty: 2,
    chapter: "第一单元",
    keywords: ["互联网", "物联网", "存储成本", "计算能力"]
})

CREATE (big_data_dev:KnowledgeNode {
    id: "big-data-development",
    name: "大数据发展与应用",
    type: "发展历程",
    description: "大数据技术的发展历程和现代应用场景，包括各行业应用和国家政策支持。",
    difficulty: 2,
    chapter: "第一单元",
    keywords: ["发展", "行业应用", "政策支持", "技术演进"]
});

// 第二单元：数据采集与预处理
CREATE (data_collection:KnowledgeNode {
    id: "data-collection",
    name: "数据采集基础",
    type: "基础理论",
    description: "数据采集是指从各种数据源获取原始数据的过程，是大数据处理的第一步。",
    difficulty: 2,
    chapter: "第二单元",
    keywords: ["数据采集", "数据源", "数据获取", "原始数据"]
})

CREATE (collection_methods:KnowledgeNode {
    id: "collection-methods",
    name: "数据采集方法",
    type: "基础概念",
    description: "数据采集的主要方法包括日志收集、爬虫采集、数据库导出、API接口、传感器采集等。",
    difficulty: 2,
    chapter: "第二单元",
    keywords: ["日志收集", "爬虫", "数据库导出", "API", "传感器"]
})

CREATE (data_quality:KnowledgeNode {
    id: "data-quality",
    name: "数据质量管理",
    type: "基础理论",
    description: "数据质量管理是保证采集数据准确性、完整性、一致性和时效性的过程，直接影响后续分析结果。",
    difficulty: 3,
    chapter: "第二单元",
    keywords: ["数据质量", "准确性", "完整性", "一致性", "时效性"]
})

CREATE (data_cleaning:KnowledgeNode {
    id: "data-cleaning",
    name: "数据清洗",
    type: "基础理论",
    description: "数据清洗是识别并纠正数据集中错误、不一致和缺失值等问题的过程，是数据预处理的重要环节。",
    difficulty: 3,
    chapter: "第二单元",
    keywords: ["数据清洗", "错误处理", "缺失值", "异常值", "数据修正"]
})

// 第三单元：分布式存储系统
CREATE (dist_storage:KnowledgeNode {
    id: "distributed-storage",
    name: "分布式存储基础",
    type: "基础理论",
    description: "分布式存储系统是将数据分散存储在多个物理节点上的存储架构，解决了大数据存储的容量和可靠性问题。",
    difficulty: 3,
    chapter: "第三单元",
    keywords: ["分布式存储", "存储架构", "数据分布", "可靠性"]
})

CREATE (hdfs:KnowledgeNode {
    id: "hdfs",
    name: "HDFS架构与原理",
    type: "技术原理",
    description: "Hadoop分布式文件系统(HDFS)是一个高容错性、高吞吐量的分布式文件系统，设计用于在普通硬件上运行，并提供可靠的数据存储。",
    difficulty: 4,
    chapter: "第三单元",
    keywords: ["HDFS", "分布式文件系统", "NameNode", "DataNode", "数据块"]    
})

CREATE (hbase:KnowledgeNode {
    id: "hbase",
    name: "HBase存储系统",
    type: "技术方法",
    description: "HBase是一种分布式、面向列的开源数据库，建立在HDFS之上，适用于大规模结构化和半结构化数据存储。",
    difficulty: 4,
    chapter: "第三单元",
    keywords: ["HBase", "列存储", "键值对", "有序映射", "分布式数据库"]
})

CREATE (dist_storage_case:KnowledgeNode {
    id: "distributed-storage-case",
    name: "分布式存储案例",
    type: "技术原理",
    description: "常见分布式存储系统案例分析，包括Amazon S3、MinIO、Ceph等系统的架构和应用场景。",
    difficulty: 3,
    chapter: "第三单元",
    keywords: ["S3", "MinIO", "Ceph", "存储系统", "案例分析"]
});

// 第四单元：分布式计算框架
CREATE (dist_computing:KnowledgeNode {
    id: "distributed-computing",
    name: "分布式计算基础",
    type: "技术方法",
    description: "分布式计算是利用多台计算机共同处理任务的计算模式，通过任务分解和并行处理提高计算效率。",
    difficulty: 3,
    chapter: "第四单元",
    keywords: ["分布式计算", "并行计算", "计算模式", "任务分解"]
})

CREATE (mapreduce:KnowledgeNode {
    id: "mapreduce",
    name: "MapReduce模型",
    type: "技术模型",
    description: "MapReduce是一种编程模型，用于大规模数据集的并行运算。核心思想是"分而治之"，将任务拆分为Map和Reduce两个阶段。",
    difficulty: 3,
    chapter: "第四单元",
    keywords: ["MapReduce", "Map", "Reduce", "并行计算", "编程模型"]
})

CREATE (spark:KnowledgeNode {
    id: "spark-framework",
    name: "Spark框架",
    type: "技术组件",
    description: "Apache Spark是一个快速、通用的分布式计算系统，提供了比MapReduce更高效的内存计算方式，支持批处理、流处理、机器学习等多种计算场景。",
    difficulty: 3,
    chapter: "第四单元",
    keywords: ["Spark", "RDD", "内存计算", "DAG", "弹性分布式数据集"]
})

CREATE (word_count:KnowledgeNode {
    id: "word-count",
    name: "单词计数案例",
    type: "实践案例",
    description: "使用MapReduce和Spark实现的经典单词计数案例，演示分布式计算的基本原理和编程实现。",
    difficulty: 3,
    chapter: "第四单元",
    keywords: ["单词计数", "WordCount", "MapReduce应用", "Spark应用"]
})

CREATE (yarn:KnowledgeNode {
    id: "yarn",
    name: "YARN资源管理器",
    type: "技术组件",
    description: "YARN是Hadoop生态系统中的资源管理器，负责集群资源的分配和任务调度，支持多种计算框架在同一集群上运行。",
    difficulty: 3,
    chapter: "第四单元",
    keywords: ["YARN", "资源管理", "任务调度", "ResourceManager", "NodeManager"]
})

// 第五单元：数据仓库与数据湖
CREATE (data_warehouse:KnowledgeNode {
    id: "data-warehouse",
    name: "数据仓库基础",
    type: "技术方法",
    description: "数据仓库是面向主题的、集成的、相对稳定的、反映历史变化的数据集合，用于支持企业的决策分析处理。",
    difficulty: 3,
    chapter: "第五单元",
    keywords: ["数据仓库", "决策支持", "OLAP", "数据集成", "主题导向"]
})

CREATE (data_modeling:KnowledgeNode {
    id: "data-modeling",
    name: "数据建模技术",
    type: "技术方法",
    description: "数据建模是构建数据仓库的核心工作，包括维度建模、星型模型、雪花模型等建模方法。",
    difficulty: 3,
    chapter: "第五单元",
    keywords: ["数据建模", "维度建模", "星型模型", "雪花模型", "事实表"]
})

CREATE (etl:KnowledgeNode {
    id: "etl-process",
    name: "ETL过程",
    type: "技术方法",
    description: "ETL是Extract(提取)、Transform(转换)和Load(加载)的缩写，是将数据从源系统抽取、清洗转换并加载到目标系统的过程。",
    difficulty: 3,
    chapter: "第五单元",
    keywords: ["ETL", "数据抽取", "数据转换", "数据加载", "数据集成"]
})

CREATE (data_lake:KnowledgeNode {
    id: "data-lake",
    name: "数据湖架构",
    type: "技术组件",
    description: "数据湖是一个存储企业各种原始数据的大型仓库，可以存储结构化、半结构化和非结构化数据，强调数据的原始性和多样性。",
    difficulty: 4,
    chapter: "第五单元",
    keywords: ["数据湖", "原始数据", "多样性", "智能分析", "大规模存储"]
})

CREATE (warehouse_vs_lake:KnowledgeNode {
    id: "warehouse-vs-lake",
    name: "数据仓库与数据湖的比较",
    type: "技术挑战",
    description: "数据仓库与数据湖在数据结构、处理模式、扩展性和应用场景等方面的比较，及各自优缺点分析。",
    difficulty: 3,
    chapter: "第五单元",
    keywords: ["对比分析", "使用场景", "架构差异", "性能特点"]
});

// 第六单元：数据挖掘与分析
CREATE (data_mining:KnowledgeNode {
    id: "data-mining",
    name: "数据挖掘基础",
    type: "技术方法",
    description: "数据挖掘是从大量数据中提取隐含信息、模式和知识的过程，涉及统计学、机器学习、数据库技术等多学科方法。",
    difficulty: 3,
    chapter: "第六单元",
    keywords: ["数据挖掘", "模式发现", "知识提取", "数据分析"]
})

CREATE (association_analysis:KnowledgeNode {
    id: "association-analysis",
    name: "关联分析",
    type: "技术方法",
    description: "关联分析是发现数据集中项目间关联关系的过程，常用的算法包括Apriori和FP-Growth等。",
    difficulty: 3,
    chapter: "第六单元",
    keywords: ["关联规则", "支持度", "置信度", "Apriori", "FP-Growth"]
})

CREATE (clustering:KnowledgeNode {
    id: "clustering",
    name: "聚类分析",
    type: "技术方法",
    description: "聚类分析是将相似对象分组形成"簇"的无监督学习方法，常用算法包括K-means、DBSCAN等。",
    difficulty: 3,
    chapter: "第六单元",
    keywords: ["聚类", "K-means", "DBSCAN", "相似性度量", "无监督学习"]
})

CREATE (classification:KnowledgeNode {
    id: "classification",
    name: "分类分析",
    type: "技术方法",
    description: "分类是预测数据所属类别的监督学习方法，常用算法包括决策树、支持向量机、随机森林等。",
    difficulty: 3,
    chapter: "第六单元",
    keywords: ["分类", "决策树", "支持向量机", "随机森林", "监督学习"]
})

CREATE (anomaly_detection:KnowledgeNode {
    id: "anomaly-detection",
    name: "异常检测",
    type: "技术方法",
    description: "异常检测是识别数据中异常模式、离群点或规则违反的过程，广泛应用于欺诈检测、网络安全等领域。",
    difficulty: 4,
    chapter: "第六单元",
    keywords: ["异常检测", "离群点分析", "欺诈检测", "异常模式", "网络安全"]
})

CREATE (market_basket:KnowledgeNode {
    id: "market-basket",
    name: "实战案例-购物篮分析",
    type: "实践案例",
    description: "购物篮分析是通过挖掘消费者购买行为中的关联规则，发现商品间的关联关系，用于商品推荐和营销策略制定。",
    difficulty: 3,
    chapter: "第六单元",
    keywords: ["购物篮分析", "商品关联", "频繁项集", "关联规则挖掘"]
});

// 第七单元：数据可视化技术
CREATE (data_viz:KnowledgeNode {
    id: "data-visualization",
    name: "数据可视化基础",
    type: "技术模型",
    description: "数据可视化是将数据以图形化方式呈现，帮助人们理解数据含义、发现模式和获取洞察的技术。",
    difficulty: 2,
    chapter: "第七单元",
    keywords: ["数据可视化", "图形化表示", "视觉编码", "信息传达"]
})

CREATE (viz_types:KnowledgeNode {
    id: "visualization-types",
    name: "可视化类型与选择",
    type: "技术原理",
    description: "不同类型的可视化图表适用于不同数据和分析目的，包括条形图、折线图、饼图、散点图、热力图等。",
    difficulty: 2,
    chapter: "第七单元",
    keywords: ["可视化图表", "数据图表", "图表选择", "视觉元素"]
})

CREATE (viz_principles:KnowledgeNode {
    id: "visualization-principles",
    name: "可视化设计原则",
    type: "技术原理",
    description: "有效的数据可视化需遵循的设计原则，包括简洁性、准确性、清晰性、目的性和上下文关联等。",
    difficulty: 3,
    chapter: "第七单元",
    keywords: ["设计原则", "视觉感知", "信息设计", "图表效果", "用户体验"]
})

CREATE (viz_tools:KnowledgeNode {
    id: "visualization-tools",
    name: "可视化工具与库",
    type: "实践案例",
    description: "常用的数据可视化工具和库，包括Tableau、PowerBI、ECharts、D3.js等，及其特点和适用场景。",
    difficulty: 3,
    chapter: "第七单元",
    keywords: ["可视化工具", "Tableau", "PowerBI", "ECharts", "D3.js"]
})

// 第八单元：大数据应用实践
CREATE (big_data_platform:KnowledgeNode {
    id: "big-data-platform",
    name: "大数据平台架构",
    type: "技术模型",
    description: "现代大数据平台的架构设计，包括数据采集、存储、处理、分析、可视化等各层组件的集成。",
    difficulty: 4,
    chapter: "第八单元",
    keywords: ["平台架构", "技术栈", "组件集成", "系统设计"]
})

CREATE (industry_applications:KnowledgeNode {
    id: "industry-applications",
    name: "行业应用案例",
    type: "应用领域",
    description: "大数据技术在金融、零售、医疗、交通、制造业等不同行业的应用案例分析。",
    difficulty: 3,
    chapter: "第八单元",
    keywords: ["行业应用", "场景分析", "解决方案", "价值创造"]
})

CREATE (stock_analysis:KnowledgeNode {
    id: "stock-analysis",
    name: "股票数据分析案例",
    type: "实践案例",
    description: "使用大数据技术对股票市场数据进行采集、存储、分析和可视化的综合案例。",
    difficulty: 4,
    chapter: "第八单元",
    keywords: ["股票分析", "时间序列", "预测模型", "技术指标", "市场洞察"]
});

// 创建基础概念之间的关系
// 大数据相关概念层次关系
MATCH (big_data_def:KnowledgeNode {id: 'big-data-definition'})
MATCH (data_collection:KnowledgeNode {id: 'data-collection'})
MATCH (dist_storage:KnowledgeNode {id: 'distributed-storage'})
MATCH (dist_computing:KnowledgeNode {id: 'distributed-computing'})
CREATE (data_collection)-[:BELONGS_TO {description: "数据采集是大数据处理的第一步"}]->(big_data_def)
CREATE (dist_storage)-[:SUPPORTS {description: "分布式存储为大数据提供基础支撑"}]->(big_data_def)
CREATE (dist_computing)-[:PROCESSES {description: "分布式计算是处理大数据的主要方式"}]->(big_data_def);

// 大数据历史和发展关系
MATCH (big_data_history:KnowledgeNode {id: 'big-data-history'})
MATCH (big_data_factors:KnowledgeNode {id: 'big-data-growth-factors'})
MATCH (big_data_dev:KnowledgeNode {id: 'big-data-development'})
MATCH (big_data_def:KnowledgeNode {id: 'big-data-definition'})
MATCH (big_data_types:KnowledgeNode {id: 'big-data-types'})
MATCH (data_lifecycle:KnowledgeNode {id: 'data-lifecycle'})
CREATE (big_data_history)-[:RELATES_TO {description: "历史发展与定义相关"}]->(big_data_def)
CREATE (big_data_factors)-[:CONTRIBUTES_TO {description: "发展因素促进大数据发展"}]->(big_data_dev)
CREATE (big_data_history)-[:LEADS_TO {description: "历史进程导致现代大数据发展"}]->(big_data_dev)
CREATE (big_data_dev)-[:FOLLOWS {description: "大数据发展遵循历史进程"}]->(big_data_history)
CREATE (big_data_types)-[:CATEGORIZES {description: "对大数据进行分类"}]->(big_data_def)
CREATE (data_lifecycle)-[:DESCRIBES {description: "描述大数据处理流程"}]->(big_data_def);

// 大数据先驱与特征
MATCH (big_data_pioneers:KnowledgeNode {id: 'big-data-pioneers'})
MATCH (big_data_chars:KnowledgeNode {id: 'big-data-characteristics'})
MATCH (big_data_history:KnowledgeNode {id: 'big-data-history'})
CREATE (big_data_pioneers)-[:CONTRIBUTES_TO {description: "先驱贡献于大数据发展历史"}]->(big_data_history)
CREATE (big_data_chars)-[:DEFINES {description: "特征定义了大数据本质"}]->(big_data_def);

// 数据采集与预处理概念关系
MATCH (data_collection:KnowledgeNode {id: 'data-collection'})
MATCH (collection_methods:KnowledgeNode {id: 'collection-methods'})
MATCH (data_quality:KnowledgeNode {id: 'data-quality'})
MATCH (data_cleaning:KnowledgeNode {id: 'data-cleaning'})
CREATE (collection_methods)-[:IS_TYPE_OF {description: "是数据采集的方法类型"}]->(data_collection)
CREATE (data_quality)-[:ENSURES {description: "保证数据采集质量"}]->(data_collection)
CREATE (data_cleaning)-[:FOLLOWS {description: "数据清洗是采集后的步骤"}]->(data_collection);

// 分布式存储系统关系
MATCH (dist_storage:KnowledgeNode {id: 'distributed-storage'})
MATCH (hdfs:KnowledgeNode {id: 'hdfs'})
MATCH (hbase:KnowledgeNode {id: 'hbase'})
MATCH (dist_storage_case:KnowledgeNode {id: 'distributed-storage-case'})
CREATE (hdfs)-[:IS_TYPE_OF {description: "HDFS是分布式存储系统的一种"}]->(dist_storage)
CREATE (hbase)-[:BUILT_ON {description: "HBase构建在分布式文件系统之上"}]->(hdfs)
CREATE (dist_storage_case)-[:ILLUSTRATES {description: "案例说明分布式存储系统"}]->(dist_storage);

// 分布式计算框架关系
MATCH (dist_computing:KnowledgeNode {id: 'distributed-computing'})
MATCH (mapreduce:KnowledgeNode {id: 'mapreduce'})
MATCH (spark:KnowledgeNode {id: 'spark-framework'})
MATCH (word_count:KnowledgeNode {id: 'word-count'})
MATCH (yarn:KnowledgeNode {id: 'yarn'})
CREATE (mapreduce)-[:IS_TYPE_OF {description: "MapReduce是分布式计算模型"}]->(dist_computing)
CREATE (spark)-[:IS_TYPE_OF {description: "Spark是分布式计算框架"}]->(dist_computing)
CREATE (word_count)-[:DEMONSTRATES {description: "单词计数演示分布式计算原理"}]->(mapreduce)
CREATE (word_count)-[:DEMONSTRATES {description: "单词计数也可用Spark实现"}]->(spark)
CREATE (yarn)-[:MANAGES {description: "YARN管理分布式计算资源"}]->(dist_computing);

// 数据仓库与数据湖关系
MATCH (data_warehouse:KnowledgeNode {id: 'data-warehouse'})
MATCH (data_modeling:KnowledgeNode {id: 'data-modeling'})
MATCH (etl:KnowledgeNode {id: 'etl-process'})
MATCH (data_lake:KnowledgeNode {id: 'data-lake'})
MATCH (warehouse_vs_lake:KnowledgeNode {id: 'warehouse-vs-lake'})
CREATE (data_modeling)-[:USED_IN {description: "数据建模用于数据仓库构建"}]->(data_warehouse)
CREATE (etl)-[:POPULATES {description: "ETL过程填充数据仓库"}]->(data_warehouse)
CREATE (warehouse_vs_lake)-[:COMPARES {description: "比较数据仓库和数据湖"}]->(data_warehouse)
CREATE (warehouse_vs_lake)-[:COMPARES {description: "比较数据湖和数据仓库"}]->(data_lake);

// 数据挖掘与分析关系
MATCH (data_mining:KnowledgeNode {id: 'data-mining'})
MATCH (association_analysis:KnowledgeNode {id: 'association-analysis'})
MATCH (clustering:KnowledgeNode {id: 'clustering'})
MATCH (classification:KnowledgeNode {id: 'classification'})
MATCH (anomaly_detection:KnowledgeNode {id: 'anomaly-detection'})
MATCH (market_basket:KnowledgeNode {id: 'market-basket'})
CREATE (association_analysis)-[:IS_TYPE_OF {description: "关联分析是数据挖掘方法"}]->(data_mining)
CREATE (clustering)-[:IS_TYPE_OF {description: "聚类是数据挖掘方法"}]->(data_mining)
CREATE (classification)-[:IS_TYPE_OF {description: "分类是数据挖掘方法"}]->(data_mining)
CREATE (anomaly_detection)-[:IS_TYPE_OF {description: "异常检测是数据挖掘方法"}]->(data_mining)
CREATE (market_basket)-[:APPLIES {description: "购物篮分析应用关联规则"}]->(association_analysis);

// 数据可视化关系
MATCH (data_viz:KnowledgeNode {id: 'data-visualization'})
MATCH (viz_types:KnowledgeNode {id: 'visualization-types'})
MATCH (viz_principles:KnowledgeNode {id: 'visualization-principles'})
MATCH (viz_tools:KnowledgeNode {id: 'visualization-tools'})
CREATE (viz_types)-[:CATEGORIZES {description: "分类数据可视化方式"}]->(data_viz)
CREATE (viz_principles)-[:GUIDES {description: "指导数据可视化设计"}]->(data_viz)
CREATE (viz_tools)-[:IMPLEMENTS {description: "工具实现数据可视化"}]->(data_viz);

// 大数据应用关系
MATCH (big_data_platform:KnowledgeNode {id: 'big-data-platform'})
MATCH (industry_applications:KnowledgeNode {id: 'industry-applications'})
MATCH (stock_analysis:KnowledgeNode {id: 'stock-analysis'})
MATCH (big_data_def:KnowledgeNode {id: 'big-data-definition'})
CREATE (big_data_platform)-[:IMPLEMENTS {description: "平台实现大数据处理"}]->(big_data_def)
CREATE (industry_applications)-[:DEMONSTRATES {description: "案例展示大数据应用"}]->(big_data_def)
CREATE (stock_analysis)-[:IS_EXAMPLE_OF {description: "是行业应用的具体例子"}]->(industry_applications);

// 数据链路关系
MATCH (data_collection:KnowledgeNode {id: 'data-collection'})
MATCH (dist_storage:KnowledgeNode {id: 'distributed-storage'})
MATCH (dist_computing:KnowledgeNode {id: 'distributed-computing'})
MATCH (data_mining:KnowledgeNode {id: 'data-mining'})
MATCH (data_viz:KnowledgeNode {id: 'data-visualization'})
CREATE (data_collection)-[:FOLLOWED_BY {description: "采集后进行存储"}]->(dist_storage)
CREATE (dist_storage)-[:PROVIDES_DATA_FOR {description: "存储系统为计算提供数据"}]->(dist_computing)
CREATE (dist_computing)-[:ENABLES {description: "计算框架支持数据挖掘"}]->(data_mining)
CREATE (data_mining)-[:RESULTS_IN {description: "挖掘结果通过可视化展示"}]->(data_viz);

// 验证查询
// 1. 统计各种节点数量
MATCH (n:KnowledgeNode) RETURN count(n) AS 知识点数量;
MATCH (c:Course) RETURN count(c) AS 课程数量;
MATCH (u:Unit) RETURN count(u) AS 单元数量;
MATCH (l:Lecture) RETURN count(l) AS 讲座数量;

// 2. 检查关系数量
MATCH ()-[r]-() RETURN type(r) AS 关系类型, count(r) AS 关系数量 ORDER BY 关系数量 DESC;

// 3. 查看课程结构
MATCH (c:Course)-[:CONTAINS]->(u:Unit)
RETURN c.name AS 课程名称, u.name AS 单元名称, u.order AS 单元顺序
ORDER BY u.order;

// 4. 查看知识点难度分布
MATCH (n:KnowledgeNode)
RETURN n.difficulty AS 难度, count(n) AS 数量
ORDER BY n.difficulty;

// 5. 可视化知识点关系网络(以神经网络基础为例)
MATCH (n:KnowledgeNode {id: 'neural-networks-basic'})-[r]-(m)
RETURN n, r, m
LIMIT 50;

// 6. 查看实践案例及其应用领域
MATCH (p:KnowledgeNode)-[:IS_EXAMPLE_OF]->(a:KnowledgeNode {id: 'dl-applications'})
RETURN p.name AS 案例名称, p.description AS 案例描述;

// 7. 查看神经网络搭建流程步骤
MATCH (step:KnowledgeNode)-[:IS_STEP_OF]->(workflow:KnowledgeNode {id: 'nn-workflow'})
RETURN step.name AS 步骤名称, step.description AS 步骤描述
ORDER BY step.name; 