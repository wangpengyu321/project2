// Course nodes
CREATE (c1:Course {id: 'AI101', name: 'Introduction to AI', description: 'Fundamental concepts of Artificial Intelligence', level: 'Beginner'});

// Unit nodes
CREATE (u1:Unit {id: 'U1', name: 'AI Basics', description: 'Basic concepts and terminology in AI', order: 1});
CREATE (u2:Unit {id: 'U2', name: 'Machine Learning Fundamentals', description: 'Introduction to machine learning concepts', order: 2});

// Knowledge nodes
CREATE (k1:KnowledgeNode {
    id: 'K1',
    name: 'What is AI',
    type: 'Concept',
    description: 'Introduction to artificial intelligence and its basic concepts',
    difficulty: 1,
    chapter: '1.1',
    keywords: ['AI', 'artificial intelligence', 'introduction']
});

CREATE (k2:KnowledgeNode {
    id: 'K2',
    name: 'Types of AI',
    type: 'Concept',
    description: 'Different types and categories of AI systems',
    difficulty: 2,
    chapter: '1.2',
    keywords: ['weak AI', 'strong AI', 'narrow AI', 'general AI']
});

CREATE (k3:KnowledgeNode {
    id: 'K3',
    name: 'Machine Learning Basics',
    type: 'Concept',
    description: 'Basic concepts of machine learning',
    difficulty: 2,
    chapter: '2.1',
    keywords: ['machine learning', 'ML', 'training', 'inference']
});

// Relationships
CREATE (c1)-[:CONTAINS {description: 'Course contains unit'}]->(u1);
CREATE (c1)-[:CONTAINS {description: 'Course contains unit'}]->(u2);

CREATE (u1)-[:CONTAINS {description: 'Unit contains knowledge node'}]->(k1);
CREATE (u1)-[:CONTAINS {description: 'Unit contains knowledge node'}]->(k2);
CREATE (u2)-[:CONTAINS {description: 'Unit contains knowledge node'}]->(k3);

CREATE (k1)-[:PREREQUISITE_FOR {description: 'Basic AI concepts are required for understanding AI types'}]->(k2);
CREATE (k2)-[:RELATED_TO {description: 'AI types are related to machine learning concepts'}]->(k3); 