package com.alexanderthelen.applicationkit.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Die {@code Connection}-Klasse vereinfacht den Umgang mit SQLite-Datenbanken.
 */
public class Connection {
    /**
     * Pfad zur Datenbank.
     */
    private final Path path;
    /**
     * Von Java implementierte Verbindung zur Datenbank (siehe
     * {@link java.sql.Connection} der Java-Dokumentation).
     */
    private final java.sql.Connection rawConnection;

    /**
     * Erstellt eine neue {@code Connection}-Instanz mit einem Pfad. Eine neue
     * Instanz wird mit den gegebenen Parametern erstellt und anschließend
     * initialisiert (siehe {@link #initialize()}).
     *
     * @param path
     *            Pfad zur Datenbank
     * @throws IOException
     *             wenn die Validierung fehlschlägt.
     * @throws SQLException
     *             wenn die Verbindung nicht aufgebaut werden kann.
     */
    public Connection(Path path) throws IOException, SQLException {
        this.path = path.toAbsolutePath();
        validatePath();
        rawConnection = DriverManager.getConnection("jdbc:sqlite:" + this.path);
        initialize();
    }

    /**
     * Prüft, ob der angegebene Pfad gültig ist
     * 
     * @throws IOException
     *             wenn der Pfad nicht gültig ist.
     */
    private void validatePath() throws IOException {
        if (!Files.exists(path) || Files.notExists(path)) {
            throw new IOException("Pfad zur Datenbank existiert nicht.");
        } else if (Files.isDirectory(path)) {
            throw new IOException("Pfad zur Datenbank ist ein Ordner.");
        } else if (!Files.isRegularFile(path)) {
            throw new IOException("Pfad zur Datenbank ist keine gültige Datei.");
        } else if (!Files.isReadable(path)) {
            throw new IOException("Pfad zur Datenbank ist nicht lesbar.");
        }
    }

    /**
     * Schließt die Verbindung zur Datenbank.
     *
     * @throws SQLException
     *             wenn die Verbindung nicht geschlossen werden kann.
     */
    public void close() throws SQLException {
        rawConnection.close();
    }

    /**
     * Gibt den Status der Verbindung zurück.
     *
     * @return {@code true}, wenn die Verbindung geschlossen ist. Ansonsten
     *         {@code false}.
     * @throws SQLException
     *             wenn der Status nicht geprüft werden kann.
     */
    public boolean isClosed() throws SQLException {
        return rawConnection.isClosed();
    }

    /**
     * Initialisiert die Datenbank. Diese Methode wird automatisch aufgerufen,
     * sobald eine Instanz erstellt wird. Sie sorgt dafür, dass UTF-8 und
     * Fremdschlüssel verwendet werden.
     *
     * @throws SQLException
     *             wenn die Initialisierung fehlschlägt.
     */
    public void initialize() throws SQLException {
        Statement statement = createStatement();
        statement.addBatch("PRAGMA encoding = 'UTF-8'");
        statement.addBatch("PRAGMA foreign_keys = ON");
        statement.addBatch("PRAGMA auto_vacuum = 1");
        statement.addBatch("PRAGMA automatic_index = ON");
        statement.executeBatch();
    }

    /**
     * Erzeugt ein {@link Statement}. Die Methode erstellt ein {@link Statement}
     * und setzt den Timeout auf 5 Sekunden. Außerdem soll die Anweisung
     * geschlossen werden, sobald sie ausgeführt wurde.
     *
     * @return {@code Statement}.
     * @throws SQLException
     *             wenn kein {@code Statement} erstellt werden kann.
     */
    public Statement createStatement() throws SQLException {
        Statement statement = rawConnection.createStatement();
        statement.setQueryTimeout(5);
        statement.closeOnCompletion();
        return statement;
    }

    /**
     * Führt eine SELECT-Anfrage aus.
     *
     * Für mehr Informationen siehe {@link Statement#executeQuery(String)} der
     * Java-Dokumentation.
     *
     * @param sql
     *            Anfrage, die ausgeführt werden soll.
     * @return Ergebnismenge der Anfrage.
     * @throws SQLException
     *             wenn die Anfrage fehlerhaft ist.
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return createStatement().executeQuery(sql);
    }

    /**
     * Führt eine INSERT/UPDATE/DELETE-Anfrage aus.
     *
     * Für mehr Informationen siehe {@link Statement#executeUpdate(String)} der
     * Java-Dokumentation.
     *
     * @param sql
     *            Anfrage, die ausgeführt werden soll.
     * @return Anzahl der betroffenen Zeilen.
     * @throws SQLException
     *             wenn die Anfrage fehlerhaft ist.
     */
    public int executeUpdate(String sql) throws SQLException {
        return createStatement().executeUpdate(sql);
    }

    /**
     * Bereitet eine Anfrage mit Platzhaltern vor.
     *
     * Für mehr Informationen siehe
     * {@link java.sql.Connection#prepareStatement(String)} der
     * Java-Dokumentation.
     *
     * @param sql
     *            Anfrage, die Platzhalter beinhaltet.
     * @return vorbereitete Anfrage.
     * @throws SQLException
     *             wenn die Anfrage fehlerhaft ist.
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement statement = rawConnection.prepareStatement(sql);
        statement.setQueryTimeout(5);
        statement.closeOnCompletion();
        return statement;
    }

    /**
     * Getter für {@link #rawConnection}.
     *
     * @return von Java implementierte Verbindung zur Datenbank.
     */
    public java.sql.Connection getRawConnection() {
        return rawConnection;
    }

    /**
     * Getter für {@link #path}.
     *
     * @return Pfad zur Datenbank.
     */
    public Path getPath() {
        return path;
    }
}
