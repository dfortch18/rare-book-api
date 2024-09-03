CREATE TABLE t_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    slug VARCHAR(100) NOT NULL UNIQUE,
    is_initial BOOLEAN DEFAULT FALSE
);

CREATE TABLE t_rare_books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    category_id BIGINT NOT NULL,
    publication_year INT NOT NULL,
    isbn VARCHAR(255) NOT NULL UNIQUE,
    book_condition VARCHAR(10) NOT NULL,
    rarity VARCHAR(10) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_initial BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES t_categories(id)
        ON DELETE CASCADE
);

CREATE TABLE t_editions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    edition_number INT NOT NULL,
    rare_book_id BIGINT NOT NULL,
    publication_year INT NOT NULL,
    notes TEXT,

    CONSTRAINT fk_rare_book
        FOREIGN KEY (rare_book_id)
        REFERENCES t_rare_books(id)
        ON DELETE CASCADE
);
