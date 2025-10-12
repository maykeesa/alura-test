INSERT INTO User (id, name, email, role, password)
VALUES (1, 'John Doe', 'john.doe@example.com', 'INSTRUCTOR', 'securePass123');

INSERT INTO Course (id, title, description, instructor_id, status, publishedAt)
VALUES (
    1,
    'Introduction to Java',
    'Learn the basics of Java programming, syntax, and object-oriented concepts.',
    1,
    'BUILDING',
    NOW()
);

INSERT INTO Task (course_id, statement, order_index, type)
VALUES
(
    1,
    'What is object-oriented programming (OOP)? Describe its main principles.',
    1,
    'OPEN_TEXT'
);

INSERT INTO Course (id, title, description, instructor_id, status, publishedAt)
VALUES (
    2,
    'Advanced Java',
    'Deep dive into advanced Java topics, including streams, concurrency, and design patterns.',
    1,
    'PUBLISHED',
    NOW()
);
