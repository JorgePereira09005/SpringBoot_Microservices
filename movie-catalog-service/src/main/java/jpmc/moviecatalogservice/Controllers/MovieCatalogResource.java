package jpmc.moviecatalogservice.Controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import jpmc.moviecatalogservice.models.CatalogItem;
import jpmc.moviecatalogservice.models.Movie;
import jpmc.moviecatalogservice.models.Rating;
import jpmc.moviecatalogservice.models.UserRating;
import jpmc.moviecatalogservice.services.MovieInfo;
import jpmc.moviecatalogservice.services.UserRatingInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    /*@Autowired
    private DiscoveryClient discoveryClient;*/

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @Autowired
    WebClient.Builder webClientBuilder;

    @RequestMapping(path = "/{userId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = userRatingInfo.getUserRating(userId);

        return ratings.getUserRatings().stream().map(rating -> {
            //for each movie ID, call movie info service to get details
            return movieInfo.getCatalogItem(rating);
        }).collect(Collectors.toList());

        /*Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/
    }

    /*//shouldn't make any calls. Keep it simples, avoid errors.
    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
        return Arrays.asList(new CatalogItem("No movie", "", 0));
    }*/

}
