INSERT INTO t_categories (name, description, slug, is_initial)
VALUES
    ('Classic Fiction', 'Classic and modern fiction literature', 'classic-fiction', TRUE),
    ('Academic Non-Fiction', 'Research and academic studies', 'academic-non-fiction', TRUE),
    ('Science Fiction', 'Books about science fiction and alternate futures', 'science-fiction', TRUE),
    ('World History', 'Texts about global historical events', 'world-history', TRUE),
    ('Inspirational Biographies', 'Life stories of influential figures', 'inspirational-biographies', TRUE),
    ('Epic Fantasy', 'Fantasy novels with epic imaginary worlds', 'epic-fantasy', TRUE),
    ('Thrillers and Mystery', 'Novels of mystery and suspense', 'thrillers-mystery', TRUE),
    ('Self-Help and Personal Development', 'Books on self-help and personal growth', 'self-help-personal-development', TRUE);

INSERT INTO t_rare_books (title, author, category_id, publication_year, isbn, book_condition, rarity, description, price, is_initial)
VALUES
    ('The Great Gatsby', 'F. Scott Fitzgerald', 1, 1925, '9780743273565', 'NEW', 'VERY_RARE', 'A classic novel of the Jazz Age.', 29.99, TRUE),
    ('Pride and Prejudice', 'Jane Austen', 1, 1813, '9780141439518', 'USED', 'RARE', 'A novel about love and social class.', 19.99, TRUE),
    ('The Structure of Scientific Revolutions', 'Thomas S. Kuhn', 2, 1962, '9780226458083', 'NEW', 'VERY_RARE', 'A fundamental study on the history of science.', 34.99, TRUE),
    ('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 2, 2011, '9780062316110', 'USED', 'COMMON', 'An analysis of the history of humanity.', 24.99, TRUE),
    ('Dune', 'Frank Herbert', 3, 1965, '9780441013593', 'NEW', 'RARE', 'An epic science fiction novel.', 39.99, TRUE),
    ('Neuromancer', 'William Gibson', 3, 1984, '9780441569595', 'USED', 'VERY_RARE', 'A classic in cyberspace and science fiction.', 27.99, TRUE),
    ('The Pillars of the Earth', 'Ken Follett', 4, 1989, '9780451166890', 'NEW', 'RARE', 'A historical novel set in 12th-century England.', 32.99, TRUE),
    ('A People\'s History of the United States', 'Howard Zinn', 4, 1980, '9780062397348', 'USED', 'COMMON', 'A history of the U.S. from the perspective of marginalized groups.', 19.99, TRUE),
    ('Steve Jobs', 'Walter Isaacson', 5, 2011, '9781451648539', 'NEW', 'RARE', 'A biography of Apple co-founder Steve Jobs.', 29.99, TRUE),
    ('The Diary of a Young Girl', 'Anne Frank', 5, 1947, '9780553296983', 'USED', 'VERY_RARE', 'The diary of Anne Frank, a Jewish girl in hiding.', 21.99, TRUE),
    ('Harry Potter and the Sorcerer\'s Stone', 'J.K. Rowling', 6, 1997, '9780590353427', 'NEW', 'COMMON', 'The first book in the Harry Potter series.', 25.99, TRUE),
    ('The Hobbit', 'J.R.R. Tolkien', 6, 1937, '9780547928227', 'USED', 'RARE', 'A fantasy novel by J.R.R. Tolkien.', 22.99, TRUE),
    ('Gone Girl', 'Gillian Flynn', 7, 2012, '9780307588371', 'NEW', 'VERY_RARE', 'A thriller about a woman who goes missing.', 18.99, TRUE),
    ('The Girl with the Dragon Tattoo', 'Stieg Larsson', 7, 2005, '9780307454546', 'USED', 'RARE', 'A mystery novel involving a journalist and a hacker.', 15.99, TRUE),
    ('Atomic Habits', 'James Clear', 8, 2018, '9780735211292', 'NEW', 'COMMON', 'A guide to building good habits and breaking bad ones.', 17.99, TRUE),
    ('The Power of Habit', 'Charles Duhigg', 8, 2012, '9780812981605', 'USED', 'VERY_RARE', 'A look at the science of habits and how to change them.', 19.99, TRUE);

INSERT INTO t_editions (edition_number, rare_book_id, publication_year, notes)
VALUES
    (1, 1, 2000, 'First edition of The Great Gatsby'),
    (2, 1, 2001, 'Second edition of The Great Gatsby'),
    (3, 1, 2002, 'Third edition of The Great Gatsby'),
    (1, 2, 2002, 'First edition of Pride and Prejudice'),
    (1, 3, 2007, 'First edition of The Structure of Scientific Revolutions'),
    (2, 3, 2008, 'Second edition of The Structure of Scientific Revolutions'),
    (1, 4, 2012, 'First edition of Sapiens: A Brief History of Humankind'),
    (1, 5, 1996, 'First edition of Dune'),
    (1, 6, 2001, 'First edition of Neuromancer'),
    (2, 6, 2002, 'Second edition of Neuromancer'),
    (3, 6, 2003, 'Third edition of Neuromancer'),
    (1, 7, 1999, 'First edition of The Pillars of the Earth'),
    (1, 8, 2003, 'First edition of A People\'s History of the United States'),
    (1, 9, 1991, 'First edition of Steve Jobs'),
    (1, 10, 1994, 'First edition of The Diary of a Young Girl'),
    (2, 10, 1995, 'Second edition of The Diary of a Young Girl'),
    (1, 11, 2006, 'First edition of Harry Potter and the Sorcerer\'s Stone'),
    (2, 11, 2007, 'Second edition of Harry Potter and the Sorcerer\'s Stone'),
    (1, 12, 2011, 'First edition of The Hobbit'),
    (1, 13, 1997, 'First edition of Gone Girl'),
    (2, 13, 1998, 'Second edition of Gone Girl'),
    (1, 14, 1998, 'First edition of The Girl with the Dragon Tattoo'),
    (1, 15, 2004, 'First edition of Atomic Habits'),
    (2, 15, 2005, 'Second edition of Atomic Habits'),
    (1, 16, 2009, 'First edition of The Power of Habit'),
    (2, 16, 2010, 'Second edition of The Power of Habit');
