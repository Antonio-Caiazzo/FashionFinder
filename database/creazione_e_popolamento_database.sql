DROP DATABASE IF EXISTS FashionFinder;
CREATE DATABASE FashionFinder;
USE FashionFinder;

DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
    email VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    psw VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN DEFAULT false,
    data_di_nascita DATE NOT NULL
);

DROP TABLE IF EXISTS indirizzo;
CREATE TABLE indirizzo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cap INT NOT NULL,
    citta VARCHAR(255) NOT NULL,
    provincia VARCHAR(255) NOT NULL,
    via VARCHAR(255) NOT NULL,
    civico INT NOT NULL,
    utente_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (utente_email) REFERENCES utente(email) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS carta;
CREATE TABLE carta (
    numero_carta VARCHAR(16) PRIMARY KEY NOT NULL,
    scadenza_carta DATE NOT NULL,
    nome_titolare VARCHAR(255) NOT NULL,
    cognome_titolare VARCHAR(255) NOT NULL,
    utente_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (utente_email) REFERENCES utente(email) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS ordine;
CREATE TABLE ordine (
    codice INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    costo_totale DECIMAL(10, 2) NOT NULL,
    utente_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (utente_email) REFERENCES utente(email) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS prodotto;
CREATE TABLE prodotto (
    codice INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descrizione TEXT,
    costo DECIMAL(10, 2) NOT NULL,
    sesso ENUM('u', 'd') NOT NULL,
    immagine VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS contiene;
CREATE TABLE contiene (
    ordine_codice INT,
    prodotto_codice INT,
    quantita INT,
    PRIMARY KEY (ordine_codice, prodotto_codice),
    FOREIGN KEY (ordine_codice) REFERENCES ordine(codice) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (prodotto_codice) REFERENCES prodotto(codice) ON DELETE CASCADE ON UPDATE CASCADE
);

LOCK TABLES utente WRITE, indirizzo WRITE, carta WRITE, ordine WRITE, prodotto WRITE, contiene WRITE;

INSERT INTO utente(email, username, psw, nome, cognome, isAdmin, data_di_nascita) VALUES
    ('admin@admin.com', 'Elon Musk Mission Mars', SHA2('admin', 512), 'Elon', 'Musk', true, '1990-01-01'),
    ('user@user.com', 'Steve Jobs Think Different', SHA2('user', 512), 'Steve', 'Jobs', false, '1985-05-15'),
    ('markzuckerberg@gmail.com', 'Mark Zuckerberg your data is not safe', SHA2('password', 512), 'Mark', 'Zuckerberg', false, '1988-10-20'),
    ('jeffbezosg@gmail.com', 'Jeff Bezos from A to Z', SHA2('password', 512), 'Jeff', 'Bezos', false, '1995-03-25'),
    ('larrypage@gmail.com', 'Larry Page we only recruit Indians', SHA2('password', 512), 'Larry', 'Page', false, '1992-12-10');

INSERT INTO indirizzo(cap, citta, provincia, via, civico, utente_email) VALUES
    (12345, 'Roma', 'RM', 'Via Roma', 10, 'admin@admin.com'),
    (54321, 'Milano', 'MI', 'Corso Milano', 20, 'user@user.com'),
    (67890, 'Napoli', 'NA', 'Via Napoli', 5, 'markzuckerberg@gmail.com'),
    (45678, 'Torino', 'TO', 'Corso Torino', 15, 'jeffbezosg@gmail.com'),
    (98765, 'Firenze', 'FI', 'Via Firenze', 30, 'larrypage@gmail.com');

INSERT INTO carta(numero_carta, scadenza_carta, nome_titolare, cognome_titolare, utente_email) VALUES
    ('1111222233334444', '2025-12-31', 'Elon', 'Musk', 'admin@admin.com'),
    ('2222333344445555', '2024-10-31', 'Steve', 'Jobs', 'user@user.com'),
    ('3333444455556666', '2026-08-31', 'Mark', 'Zuckerberg', 'markzuckerberg@gmail.com'),
    ('4444555566667777', '2023-11-30', 'Jeff', 'Bezos', 'jeffbezosg@gmail.com'),
    ('5555666677778888', '2025-07-31', 'Larry', 'Page', 'larrypage@gmail.com');

INSERT INTO ordine(data, costo_totale, utente_email) VALUES
    ('2024-06-01', 150.00, 'admin@admin.com'),
    ('2024-06-02', 200.50, 'user@user.com'),
    ('2024-06-03', 300.75, 'markzuckerberg@gmail.com'),
    ('2024-06-04', 180.25, 'jeffbezosg@gmail.com'),
    ('2024-06-05', 250.80, 'larrypage@gmail.com');

INSERT INTO prodotto(nome, descrizione, costo, sesso, immagine, categoria) VALUES
    ('Maglia bianca', 'Maglia di cotone bianca', 29.99, 'u', 'maglia_bianca.jpg', 't-shirt'),
    ('Pantaloni neri', 'Pantaloni aderenti neri', 49.99, 'u', 'pantaloni_neri.jpg', 'pantaloni'),
    ('Camicia a righe', 'Camicia elegante a righe', 39.99, 'u', 'camicia_righe.jpg', 'camicie'),
    ('Abito rosso', 'Abito lungo rosso', 99.99, 'd', 'abito_rosso.jpg', 'vestiti'),
    ('Jeans blu', 'Jeans casual blu', 59.99, 'u', 'jeans_blu.jpg', 'pantaloni'),
    ('Giacca invernale', 'Giacca calda per inverno', 149.99, 'u', 'giacca_invernale.jpg', 'giacche'),
    ('Sciarpa grigia', 'Sciarpa morbida grigia', 19.99, 'd', 'sciarpa_grigia.jpg', 'accessori'),
    ('Calze sportive', 'Calze leggere per lo sport', 9.99, 'u', 'calze_sportive.jpg', 'accessori'),
    ('Cappello di lana', 'Cappello caldo in lana', 29.99, 'u', 'cappello_lana.jpg', 'accessori'),
    ('Gonna nera', 'Gonna elegante nera', 39.99, 'd', 'gonna_nera.jpg', 'gonne'),
    ('Polo blu', 'Polo estiva blu', 34.99, 'u', 'polo_blu.jpg', 't-shirt'),
    ('Stivaletti marroni', 'Stivaletti in pelle marroni', 89.99, 'u', 'stivaletti_marroni.jpg', 'scarpe'),
    ('Occhiali da sole', 'Occhiali da sole alla moda da sole', 49.99, 'u', 'occhiali_sole.jpg', 'accessori'),
    ('Felpa grigia', 'Felpa comoda grigia', 59.99, 'u', 'felpa_grigia.jpg', 'felpe'),
    ('Borsa nera', 'Borsa elegante nera', 79.99, 'd', 'borsa_nera.jpg', 'borse'),
    ('Tuta sportiva', 'Tuta per attività fisica', 69.99, 'u', 'tuta_sportiva.jpg', 'sport'),
    ('Camicia bianca', 'Camicia elegante bianca', 44.99, 'u', 'camicia_bianca.jpg', 'camicie'),
    ('Vestito nero', 'Vestito da sera nero', 129.99, 'd', 'vestito_nero.jpg', 'vestiti'),
    ('Shorts verdi', 'Shorts estivi verdi', 24.99, 'u', 'shorts_verdi.jpg', 'shorts'),
    ('Piumino blu', 'Piumino caldo blu', 109.99, 'u', 'piumino_blu.jpg', 'giacche'),
    ('Borsello grigio', 'Borsello pratico grigio', 39.99, 'u', 'borsello_grigio.jpg', 'accessori'),
    ('Giacca pelle', 'Giacca elegante in pelle', 179.99, 'u', 'giacca_pelle.jpg', 'giacche'),
    ('Scarpa running', 'Scarpe da running', 79.99, 'u', 'scarpe_running.jpg', 'scarpe'),
    ('Orecchini perle', 'Orecchini eleganti perle', 29.99, 'd', 'orecchini_perle.jpg', 'gioielli'),
    ('Polo rosa', 'Polo casual rosa', 34.99, 'u', 'polo_rosa.jpg', 't-shirt'),
    ('Felpa con cappuccio', 'Felpa con cappuccio nera', 69.99, 'd', 'felpa_cappuccio.jpg', 'felpe');

INSERT INTO contiene(ordine_codice, prodotto_codice, quantita) VALUES
    (1, 1, 1),  -- 1x Maglia bianca
    (1, 3, 2),  -- 2x Camicia a righe
    (1, 5, 1);  -- 1x Jeans blu

INSERT INTO contiene(ordine_codice, prodotto_codice, quantita) VALUES
    (2, 2, 1),  -- 1x Pantaloni neri
    (2, 4, 1),  -- 1x Abito rosso
    (2, 7, 1);  -- 1x Sciarpa grigia

INSERT INTO contiene(ordine_codice, prodotto_codice, quantita) VALUES
    (3, 6, 1),  -- 1x Giacca invernale
    (3, 8, 3);  -- 3x Calze sportive

INSERT INTO contiene(ordine_codice, prodotto_codice, quantita) VALUES
    (4, 9, 1),  -- 1x Cappello di lana
    (4, 10, 2); -- 2x Gonna nera

INSERT INTO contiene(ordine_codice, prodotto_codice, quantita) VALUES
    (5, 11, 1),  -- 1x Polo blu
    (5, 12, 1),  -- 1x Stivaletti marroni
    (5, 13, 1);  -- 1x Occhiali da sole

UNLOCK TABLES;