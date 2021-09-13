package raviteja.springframework.beerbrewery.services;

import org.springframework.data.domain.Pageable;
import raviteja.springframework.beerbrewery.web.model.BeerDto;
import raviteja.springframework.beerbrewery.web.model.BeerPagedList;
import raviteja.springframework.beerbrewery.web.model.BeerStyleEnum;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId,Boolean showInventoryOnHand);

    BeerDto saveBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList getAllBeers(String beerName, BeerStyleEnum beerStyle,Boolean showInventoryOnHand,Pageable pageable);

    BeerDto getBeerByUpc(String upc);
}
