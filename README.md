# FlottenManager

Eine Java-Anwendung zur Verwaltung von Firmenfahrzeugen, Mitarbeitern und Fahrzeugbuchungen. Die Daten werden dauerhaft in einer MariaDB-Datenbank gespeichert.

Das Projekt enthält sowohl eine Konsolenanwendung als auch eine browserbasierte Oberfläche mit Java Servlets (`javax.servlet`).

## Funktionen

- Fahrzeuge anzeigen und hinzufügen
- Elektrofahrzeuge kennzeichnen
- Mitarbeiter verwalten
- verfügbare Fahrzeuge buchen
- aktive Buchungen anzeigen und stornieren
- Rückgabezeit visuell darstellen
- abgelaufene Buchungen automatisch beenden
- Daten dauerhaft in MariaDB speichern

## Verwendete Technik

- Java
- Java Servlet API (`javax.servlet`)
- MariaDB
- MariaDB JDBC-Treiber
- Apache Tomcat 8.5
- XAMPP
- HTML und CSS innerhalb des Servlets

## Voraussetzungen

Für die Konsolenanwendung werden benötigt:

- Java
- MariaDB oder MySQL
- eine Datenbank namens `flottenmanager`
- MariaDB auf Port `3307`

Für die Weboberfläche wird zusätzlich Apache Tomcat 8.5 benötigt. Das Projekt ist mit dem in XAMPP enthaltenen Tomcat 8.5 und `javax.servlet` kompatibel.

## Datenbankverbindung

Standardmäßig verwendet die Anwendung folgende lokale Einstellungen:

```text
Adresse:  jdbc:mariadb://localhost:3307/flottenmanager
Benutzer: root
Passwort: leer
```

Die Werte können optional über Umgebungsvariablen überschrieben werden:

```text
FLOTTEN_DB_URL
FLOTTEN_DB_USER
FLOTTEN_DB_PASSWORD
```

Dadurch müssen persönliche Zugangsdaten nicht im Quellcode gespeichert werden.

## Datenbank einrichten

Das Datenbankschema befindet sich in:

```text
database/schema.sql
```

Die Datei kann in MariaDB beziehungsweise phpMyAdmin importiert werden, um die benötigten Tabellen anzulegen.

## Konsolenanwendung kompilieren

Vom Projektordner aus:

```powershell
javac -encoding UTF-8 -cp "lib/*" Buchung.java DatenbankManager.java Fahrzeug.java Flottenverwaltung.java Main.java Mitarbeiter.java
```

Anschließend kann die Anwendung gestartet werden:

```powershell
java -cp ".;lib/*" Main
```

## Weboberfläche

Die Weboberfläche befindet sich in `FlottenServlet.java`. Sie bietet eine grafische Übersicht über verfügbare und gebuchte Fahrzeuge sowie Formulare zum Anlegen und Buchen von Fahrzeugen.

Die Servlet-Konfiguration befindet sich in:

```text
webapp/WEB-INF/web.xml
```

Für den lokalen Betrieb wird die Anwendung unter folgendem Tomcat-Verzeichnis bereitgestellt:

```text
C:\xampp\tomcat\webapps\flotten
```

Der MariaDB-JDBC-Treiber muss für Tomcat verfügbar sein, beispielsweise unter:

```text
C:\xampp\tomcat\lib\mariadb-java-client-3.5.9.jar
```

Anschließend werden in XAMPP `MySQL` und `Tomcat` gestartet. Die Weboberfläche ist dann unter folgender Adresse erreichbar:

```text
http://localhost:8080/flotten/fahrzeuge
```

## Projektstruktur

```text
FlottenManager/
├── database/
│   └── schema.sql
├── lib/
├── webapp/
│   └── WEB-INF/
│       └── web.xml
├── Buchung.java
├── DatenbankManager.java
├── Fahrzeug.java
├── FlottenServlet.java
├── Flottenverwaltung.java
├── Main.java
├── Mitarbeiter.java
├── .gitignore
├── LICENSE
└── README.md
```

## Hinweis zur Veröffentlichung

GitHub Pages kann nur statische Webseiten ausliefern. Da dieses Projekt Java, Tomcat und MariaDB benötigt, kann die Webanwendung nicht direkt über GitHub Pages betrieben werden.

Das öffentliche GitHub-Repository dokumentiert den Quellcode und die Entwicklung des Projekts. Die Adresse mit `localhost` ist nur auf dem eigenen Computer erreichbar.

Für eine öffentlich erreichbare Live-Version wird ein externer Java-Server mit einer erreichbaren Datenbank benötigt.

## Screenshots

### Fahrzeugübersicht

![Übersicht des FlottenManagers](uebersicht.png)

### Fahrzeugauswahl

![Auswahl eines Fahrzeugs](fahrzeug_auswahl.png)

### Mitarbeiterauswahl

![Auswahl eines Mitarbeiters](mitarbeiter_auswahl.png)

### Buchungsfortschritt

![Kreisförmige Anzeige der verbleibenden Buchungszeit](kreisbalken.png)

### Eingabevalidierung

![Validierung der Eingaben](validierung.png)