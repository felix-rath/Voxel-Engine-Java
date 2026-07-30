# Java Voxel Engine

Dieses Repository enthält eine eigene Voxel-Engine, die mit Java und OpenGL entwickelt wurde.

## Über das Projekt

Bei dem Projekt handelt es sich um eine eigene 3D-Voxel-Engine mit prozedural generierten Welten.

Der Fokus liegt auf der Umsetzung eigener Systeme für Rendering, Weltgenerierung, Physik und Spielmechaniken.

## Technologien

- Java
- OpenGL
- LWJGL
- GLSL Shader
- Multithreading
- Prozedurale Generierung
- Physikbibliothek

## Features

### Voxel World

System zur Verwaltung und Generierung einer blockbasierten 3D-Welt.

Funktionen:
- Prozedurale Weltgenerierung mit Noise-Funktionen
- Generierung verschiedener Landschaften wie Meere und Berge
- Verschiedene Biome mit unterschiedlichen Eigenschaften
- Chunk-basierte Weltstruktur

### Chunk System

System zur Verwaltung und Generierung von Weltabschnitten.

Funktionen:
- Chunk-basierte Weltverwaltung
- Multithreaded Chunk-Generierung
- Hintergrundgenerierung von Chunk-Daten zur Verbesserung der Performance

### Game Modes

Verschiedene Spielmodi zur Interaktion mit der Welt.

Funktionen:
- Survival-Modus
- Fly-Modus zur freien Bewegung und Erkundung der Welt

### Rendering System

Eigene Rendering-Struktur zur Darstellung der Voxel-Welt.

Funktionen:
- Eigener Voxel-Renderer mit OpenGL
- Textur-System mit eigenen Texturen
- Verarbeitung von Shadern
- Einfache Schattenberechnung über GLSL Shader

### Physics System

Integration eines Physiksystems für Interaktionen innerhalb der Spielwelt.

Funktionen:
- Nutzung einer Physikbibliothek für Kollisionen und Bewegungen
- Verarbeitung physikalischer Interaktionen
- Integration des Systems in die Voxel-Welt

### Projektstatus

Das Projekt befindet sich aktuell in Entwicklung. Neue Systeme werden hinzugefügt und bestehende Funktionen kontinuierlich erweitert und verbessert.
