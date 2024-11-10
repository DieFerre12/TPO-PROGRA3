package com.edutec.demoneo4j.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.edutec.demoneo4j.model.MovieEntity;
import com.edutec.demoneo4j.model.PersonEntity;

public class Grafo {
    private Map<MovieEntity, List<PersonEntity>> adjMovies;
    private Map<PersonEntity, List<MovieEntity>> adjPeople;

    public Grafo() {}

    public Grafo(List<MovieEntity> movies) {
        adjMovies = new HashMap<>();
        adjPeople = new HashMap<>();
        for (MovieEntity movie : movies) {
            adjMovies.putIfAbsent(movie, new ArrayList<>(movie.getActors()));
            adjMovies.get(movie).addAll(movie.getDirectors());
            for (PersonEntity actor : movie.getActors()) {
                adjPeople.putIfAbsent(actor, new ArrayList<>());
                adjPeople.get(actor).add(movie);
            }
            for (PersonEntity director : movie.getDirectors()) {
                adjPeople.putIfAbsent(director, new ArrayList<>());
                adjPeople.get(director).add(movie);
            }
        }
    }

    public String BFS(MovieEntity inicio) {
        StringBuilder resultado = new StringBuilder("Recorrido del Grafo (BFS):\n");
        Set<MovieEntity> visitadoMovies = new HashSet<>();
        Set<PersonEntity> visitadoPeople = new HashSet<>();
        Queue<Object> cola = new LinkedList<>();
        Set<Object> soluciones = new HashSet<>();

        visitadoMovies.add(inicio);
        cola.add(inicio);
        int nivel = 1;

        while (!cola.isEmpty()) {
            Object actual = cola.poll();
            boolean esUltimo = true;
            resultado.append("Nivel ").append(nivel).append(": ");

            if (actual instanceof MovieEntity) {
                MovieEntity movie = (MovieEntity) actual;
                resultado.append("Movie: ").append(movie.getTitle()).append("\n");
                for (PersonEntity persona : adjMovies.get(movie)) {
                    if (!visitadoPeople.contains(persona)) {
                        visitadoPeople.add(persona);
                        cola.add(persona);
                        esUltimo = false;
                        resultado.append(" -> Conectado con Person: ").append(persona.getName()).append("\n");
                    }
                }
            } else if (actual instanceof PersonEntity) {
                PersonEntity persona = (PersonEntity) actual;
                resultado.append("Person: ").append(persona.getName()).append("\n");
                for (MovieEntity movie : adjPeople.get(persona)) {
                    if (!visitadoMovies.contains(movie)) {
                        visitadoMovies.add(movie);
                        cola.add(movie);
                        esUltimo = false;
                        resultado.append(" -> Conectado con Movie: ").append(movie.getTitle()).append("\n");
                    }
                }
            }
            if (esUltimo) soluciones.add(actual);  // Agregar a soluciones si no tiene más conexiones
            nivel++;
        }

        // Agregar las soluciones al final del resultado
        resultado.append("\nSoluciones alcanzadas en el recorrido:\n");
        for (Object nodo : soluciones) {
            if (nodo instanceof MovieEntity) {
                resultado.append("Movie (solución): ").append(((MovieEntity) nodo).getTitle()).append("\n");
            } else if (nodo instanceof PersonEntity) {
                resultado.append("Person (solución): ").append(((PersonEntity) nodo).getName()).append("\n");
            }
        }
        return resultado.toString();
    }

    public String DFS(MovieEntity inicio) {
        StringBuilder resultado = new StringBuilder("Recorrido del Grafo (DFS):\n");
        Set<MovieEntity> visitadoMovies = new HashSet<>();
        Set<PersonEntity> visitadoPeople = new HashSet<>();
        Set<Object> soluciones = new HashSet<>();
        dfsRecursivo(inicio, resultado, visitadoMovies, visitadoPeople, 1, soluciones);

        // Agregar las soluciones al final del resultado
        resultado.append("\nSoluciones alcanzadas en el recorrido:\n");
        for (Object nodo : soluciones) {
            if (nodo instanceof MovieEntity) {
                resultado.append("Movie (solución): ").append(((MovieEntity) nodo).getTitle()).append("\n");
            } else if (nodo instanceof PersonEntity) {
                resultado.append("Person (solución): ").append(((PersonEntity) nodo).getName()).append("\n");
            }
        }
        return resultado.toString();
    }

    // Método recursivo para el recorrido DFS
    private void dfsRecursivo(Object nodo, StringBuilder resultado,
                              Set<MovieEntity> visitadoMovies, Set<PersonEntity> visitadoPeople, int nivel, Set<Object> soluciones) {
        resultado.append("Nivel ").append(nivel).append(": ");
        boolean esUltimo = true;

        if (nodo instanceof MovieEntity) {
            MovieEntity movie = (MovieEntity) nodo;
            if (visitadoMovies.contains(movie)) return; // Evitar ciclos
            visitadoMovies.add(movie);
            resultado.append("Movie: ").append(movie.getTitle()).append("\n");
            for (PersonEntity persona : adjMovies.get(movie)) {
                if (!visitadoPeople.contains(persona)) {
                    esUltimo = false;
                    resultado.append(" -> Conectado con Person: ").append(persona.getName()).append("\n");
                    dfsRecursivo(persona, resultado, visitadoMovies, visitadoPeople, nivel + 1, soluciones);
                }
            }
        } else if (nodo instanceof PersonEntity) {
            PersonEntity persona = (PersonEntity) nodo;
            if (visitadoPeople.contains(persona)) return; // Evitar ciclos
            visitadoPeople.add(persona);
            resultado.append("Person: ").append(persona.getName()).append("\n");
            for (MovieEntity movie : adjPeople.get(persona)) {
                if (!visitadoMovies.contains(movie)) {
                    esUltimo = false;
                    resultado.append(" -> Conectado con Movie: ").append(movie.getTitle()).append("\n");
                    dfsRecursivo(movie, resultado, visitadoMovies, visitadoPeople, nivel + 1, soluciones);
                }
            }
        }
        if (esUltimo) soluciones.add(nodo); // Agregar a soluciones si es un nodo final sin más conexiones
    }

    @Override
    public String toString() {
        return "Grafo{" +
                "adjMovies=" + adjMovies +
                ", adjPeople=" + adjPeople +
                '}';
    }
}
