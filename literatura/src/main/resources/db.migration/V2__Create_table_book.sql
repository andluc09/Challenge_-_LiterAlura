
CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(1024) NOT NULL,
    language VARCHAR(50),
    download_count INT,
    author_id INT,
    FOREIGN KEY (author_id) REFERENCES author(id)
);