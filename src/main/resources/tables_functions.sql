CREATE TABLE IF NOT EXISTS resources (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    type ENUM('crop', 'ore') NOT NULL
);

CREATE TABLE IF NOT EXISTS locations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    x FLOAT NOT NULL,
    y FLOAT NOT NULL,
    z FLOAT NOT NULL,
    yaw FLOAT NOT NULL,
    pitch FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS nodes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    location_id INT NOT NULL,
    resource_id INT NOT NULL,
    output_rate INT NOT NULL,
    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (resource_id) REFERENCES resources(id)
);

CREATE TABLE IF NOT EXISTS vaults (
    id INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE IF NOT EXISTS vault_resources (
    vault_id INT NOT NULL,
    resource_id INT NOT NULL,
    amount INT NOT NULL,
    PRIMARY KEY (vault_id, resource_id),
    FOREIGN KEY (vault_id) REFERENCES vaults(id),
    FOREIGN KEY (resource_id) REFERENCES resources(id)
);

CREATE TABLE IF NOT EXISTS towns (
    name VARCHAR(32) PRIMARY KEY,
    vault_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS chunks (
    x INT NOT NULL,
    z INT NOT NULL,
    owner VARCHAR(32) NOT NULL,
    PRIMARY KEY (x, z),
    FOREIGN KEY (owner) REFERENCES towns(name)
);

CREATE TABLE IF NOT EXISTS players (
    uid BINARY(16) PRIMARY KEY,
    username VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS player_towns (
    uid BINARY(16),
    town_name VARCHAR(32),
    PRIMARY KEY (uid, town_name),
    FOREIGN KEY (uid) REFERENCES players(uid),
    FOREIGN KEY (town_name) REFERENCES towns(name)
);