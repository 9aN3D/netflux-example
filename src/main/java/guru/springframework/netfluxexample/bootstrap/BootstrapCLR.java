package guru.springframework.netfluxexample.bootstrap;

import guru.springframework.netfluxexample.domain.Movie;
import guru.springframework.netfluxexample.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static reactor.core.publisher.Flux.just;

@Component
@AllArgsConstructor
public class BootstrapCLR implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {

        movieRepository.deleteAll()
                .thenMany(
                        just("Catch Me If You Can", "Seven", "Home alone", "Guardians of the Galaxy", "Saving Private Ryan",
                                "Titanic", "The matrix", "The Godfather", "The Shawshank Redemption", "The Dark Knight")
                                .map(title -> new Movie(UUID.randomUUID().toString(), title))
                                .flatMap(movieRepository::save))
                .subscribe(null, null, () -> {
                    movieRepository.findAll().subscribe(System.out::println);
                });
    }
}
