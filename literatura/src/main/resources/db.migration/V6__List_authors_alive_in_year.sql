SELECT * FROM authors
WHERE birth_year <= ? AND (death_year >= ? OR death_year IS NULL);
