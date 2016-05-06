# Aufgabenstellung
Es soll ein Chatsystem in Java implementiert werden, mit dem es möglich ist, mit mehreren Clients zu chatten und Dateien zu teilen. Das Verhalten der Clients soll am Server mitgeloggt werden inklusive der Datenmenge,
die jeder Client verbraucht. Auf Wunsch eines Clients soll es möglich sein, eine Statistik zu der von diesem Client verbrauchten Datenmenge an diesen Client zu schicken (hier empfielt sich die Verwendung eines POJOs).

# Benutzung
server.Server starten
client.Client starten

Nun ist es möglich auf der Console des Clients, Nachrichten zu schreiben. Diese werden an alle Clients weitergeleitet und bei deren Console ausgegeben.