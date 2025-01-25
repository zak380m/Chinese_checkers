CREATE TABLE games (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gamemode VARCHAR(100) NOT NULL,
    board VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE moves (
    game_id INT NOT NULL,
    turn_number INT NOT NULL,
    start_x INT,
    start_y INT,
    end_x INT,
    end_y INT,
    is_pass BOOLEAN DEFAULT FALSE,
    move_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (game_id, turn_number),
    FOREIGN KEY (game_id) REFERENCES games(id)
);

CREATE TRIGGER delete_moves_on_game_delete
    BEFORE DELETE ON games
    FOR EACH ROW
    DELETE FROM moves WHERE moves.game_id = OLD.id;