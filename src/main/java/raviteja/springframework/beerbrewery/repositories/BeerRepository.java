package raviteja.springframework.beerbrewery.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import raviteja.springframework.beerbrewery.domain.Beer;
import raviteja.springframework.beerbrewery.web.model.BeerDto;
import raviteja.springframework.beerbrewery.web.model.BeerStyleEnum;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerStyle( BeerStyleEnum beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

    Beer getBeerByUpc(String upc);
}
