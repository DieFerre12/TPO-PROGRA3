    package com.edutec.demoneo4j.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutec.demoneo4j.repository.MovieRepository;
    import com.edutec.demoneo4j.service.Grafo;

    import reactor.core.publisher.Mono;

    @RestController
    @RequestMapping ("/grafo")
    public class grafoContorller {
        @Autowired
        private  MovieRepository movieRepository;
        

    @GetMapping("/grafo")
    public Mono<String> getGrafo() {
        return movieRepository.findAll()
            .collectList() // Convertimos el Flux a un Mono<List<MovieEntity>>
            .map(movies -> {
                Grafo grafo = new Grafo(movies); // Construimos el grafo con la lista de películas
                return grafo.toString(); // Devolvemos la representación en String del grafo
            });
    }

    @GetMapping("/bfs")
    public Mono<String> getBFS() {
        return movieRepository.findAll()
            .collectList() // Convertimos el Flux a un Mono<List<MovieEntity>>
            .map(movies -> {
                Grafo grafo = new Grafo(movies); // Construimos el grafo con la lista de películas
                return grafo.BFS(movies.get(0)); // Devolvemos la representación en String del BFS
            });
    }

    @GetMapping("/dfs")
    public Mono<String> getDFS() {
        return movieRepository.findAll()
            .collectList() // Convertimos el Flux a un Mono<List<MovieEntity>>
            .map(movies -> {
                Grafo grafo = new Grafo(movies); // Construimos el grafo con la lista de películas
                return grafo.DFS(movies.get(0)); // Devolvemos la representación en String del DFS
            });
    }


        
    }
