package guru.springframework.netfluxexample.service;

import guru.springframework.netfluxexample.domain.Movie;
import guru.springframework.netfluxexample.domain.MovieEvent;
import guru.springframework.netfluxexample.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
@AllArgsConstructor
public class RepositoryMovieService implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Flux<MovieEvent> events(String movieId) {
        return Flux.<MovieEvent>generate(movieEventSynchronousSink -> movieEventSynchronousSink
                .next(new MovieEvent(movieId, new Date())))
                .delayElements(Duration.ofSeconds(1));
    }

    @Override
    public Mono<Movie> getById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Flux<Movie> getAll() {
        return movieRepository.findAll();
    }

}
