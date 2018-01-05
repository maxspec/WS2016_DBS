-- PRAGMA Anweisungen

PRAGMA encoding = 'UTF-8';
PRAGMA foreign_keys = ON;
PRAGMA auto_vacuum = 1;
PRAGMA automatic_index = ON;



-- CREATE Anweisungen

CREATE TABLE IF NOT EXISTS Nutzer (
    Benutzername VARCHAR NOT NULL PRIMARY KEY ASC COLLATE NOCASE,
    EMail        VARCHAR NOT NULL,
    Passwort     VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS Premiumnutzer (
    Benutzername VARCHAR NOT NULL PRIMARY KEY ASC REFERENCES Nutzer ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Schauspieler (
    Benutzername VARCHAR NOT NULL PRIMARY KEY ASC REFERENCES Premiumnutzer ON UPDATE CASCADE ON DELETE CASCADE,
    Künstlername VARCHAR,
    Vorname VARCHAR NOT NULL,
    Nachname VARCHAR NOT NULL,
    Geburtsdatum VARCHAR NOT NULL,
    Geburtsort VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS Genre (
    Bezeichnung VARCHAR NOT NULL PRIMARY KEY ASC
);

CREATE TABLE IF NOT EXISTS Video (
    ID    INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
    Bezeichnung VARCHAR NOT NULL,
    Spieldauer DOUBLE NOT NULL,
    Erscheinungsjahr INTEGER NOT NULL,
    Information    TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Watchlist (
    ID          INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
    Bezeichnung VARCHAR NOT NULL,
    Privat      BOOLEAN NOT NULL CHECK (Privat IN (0, 1)),
    Premiumnutzer      VARCHAR NOT NULL REFERENCES Premiumnutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Film (
    ID         INTEGER NOT NULL PRIMARY KEY ASC REFERENCES Video ON UPDATE CASCADE ON DELETE CASCADE,
    Produktionsbudget       DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS Folge (
    ID INTEGER NOT NULL PRIMARY KEY ASC REFERENCES Video ON UPDATE CASCADE ON DELETE CASCADE,
    Bezeichnung VARCHAR NOT NULL,
    Staffel INTEGER NOT NULL REFERENCES Staffel (ID)
);

CREATE TABLE IF NOT EXISTS Serie (
    ID INTEGER NOT NULL PRIMARY KEY ASC,
    Bezeichnung VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS Staffel (
    ID INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
    Nummer INTEGER NOT NULL,
    Serie INTEGER NOT NULL REFERENCES Serie (ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Trailer (
    ID               INTEGER NOT NULL PRIMARY KEY ASC REFERENCES Video ON UPDATE CASCADE ON DELETE CASCADE,
    Bezeichnung             VARCHAR NOT NULL,
    LQ VARCHAR NOT NULL,
    HQ VARCHAR NOT NULL,
    Film INTEGER NOT NULL REFERENCES Film (ID)
);

CREATE TABLE IF NOT EXISTS Medienkonzern (
    Bezeichnung VARCHAR NOT NULL PRIMARY KEY ASC
);

CREATE TABLE IF NOT EXISTS BewertungZuFilm (
    Bewertung INTEGER NOT NULL CHECK (Bewertung BETWEEN 1 AND 10),
    Nutzer VARCHAR NOT NULL REFERENCES Nutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE,
    Film INTEGER NOT NULL REFERENCES Film (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Nutzer ASC, Film ASC)
);

CREATE TABLE IF NOT EXISTS KommentarZuFilm (
    ID INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
    Kommentar TEXT NOT NULL,
    Premiumnutzer VARCHAR NOT NULL REFERENCES Premiumnutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE,
    Film INTEGER NOT NULL REFERENCES Film (ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS KommentarZuStaffel (
    ID INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT,
    Kommentar TEXT NOT NULL,
    Premiumnutzer VARCHAR NOT NULL REFERENCES Premiumnutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE,
    Staffel INTEGER NOT NULL REFERENCES Staffel (ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PremiumnutzerZuPremiumnutzer (
    Premiumnutzer1 VARCHAR NOT NULL REFERENCES Premiumnutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE,
    Premiumnutzer2 VARCHAR NOT NULL REFERENCES Premiumnutzer (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Premiumnutzer1 ASC, Premiumnutzer2 ASC)
);

CREATE TABLE IF NOT EXISTS VideoZuWatchlist (
    Video INTEGER NOT NULL REFERENCES Video (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    Watchlist INTEGER NOT NULL REFERENCES Watchlist (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Video ASC, Watchlist ASC)
);

CREATE TABLE IF NOT EXISTS FilmZuMedienkonzern (
    Film INTEGER NOT NULL REFERENCES Film (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    Medienkonzern VARCHAR NOT NULL REFERENCES Medienkonzern (Bezeichnung) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Film ASC, Medienkonzern ASC)
);

CREATE TABLE IF NOT EXISTS SchauspielerZuFilm (
    Schauspieler VARCHAR NOT NULL REFERENCES Schauspieler (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE,
    Film INTEGER NOT NULL REFERENCES Film (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Schauspieler ASC, Film ASC)
);

CREATE TABLE IF NOT EXISTS SchauspielerZuSerie (
    Schauspieler VARCHAR NOT NULL REFERENCES Schauspieler (Benutzername) ON UPDATE CASCADE ON DELETE CASCADE,
    Serie INTEGER NOT NULL REFERENCES Serie (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Schauspieler ASC, Serie ASC)
);

CREATE TABLE IF NOT EXISTS SchauspielerZuMedienkonzern (
    Schauspieler VARCHAR NOT NULL REFERENCES Schauspieler (Premiumnutzer) ON UPDATE CASCADE ON DELETE CASCADE,
    Medienkonzern VARCHAR NOT NULL REFERENCES Medienkonzern (Bezeichnung) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Schauspieler ASC, Medienkonzern ASC)
);

CREATE TABLE IF NOT EXISTS VideoZuMedienkonzern (
    Video INTEGER NOT NULL REFERENCES Video (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    Medienkonzern VARCHAR NOT NULL REFERENCES Medienkonzern (Bezeichnung) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Video ASC, Medienkonzern ASC)
);

CREATE TABLE IF NOT EXISTS VideoZuGenre (
    Video INTEGER NOT NULL REFERENCES Video (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    Genre VARCHAR NOT NULL REFERENCES Genre (Bezeichnung) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (Video ASC, Genre ASC)
);