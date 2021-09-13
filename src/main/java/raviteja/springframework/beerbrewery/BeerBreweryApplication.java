package raviteja.springframework.beerbrewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("localmysql")
public class BeerBreweryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerBreweryApplication.class, args);
	}

}
