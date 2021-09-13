package raviteja.springframework.beerbrewery.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import raviteja.springframework.beerbrewery.domain.Beer;
import raviteja.springframework.beerbrewery.repositories.BeerRepository;
import raviteja.springframework.beerbrewery.services.inventory.BeerInventory;
import raviteja.springframework.beerbrewery.services.inventory.BeerInventoryRestTemplateServiceImpl;
import raviteja.springframework.beerbrewery.web.controller.NotFoundException;
import raviteja.springframework.beerbrewery.web.mappers.BeerMapper;
import raviteja.springframework.beerbrewery.web.model.BeerDto;
import raviteja.springframework.beerbrewery.web.model.BeerPagedList;
import raviteja.springframework.beerbrewery.web.model.BeerStyleEnum;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BeerServiceImpl  implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerInventory beerInventory;

    @Override
    @Cacheable(cacheNames = "beerCache",condition = "#showInventoryOnHand == false")
    public BeerDto getBeerById(UUID beerId,Boolean showInventoryOnHand) {
        log.info("I was called");
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        if(showInventoryOnHand) {
            beer.setQuantityOnHand(beerInventory.getOnHandQuantity(beer.getId()));
        }
        return BeerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
    return BeerMapper.beerToBeerDto(beerRepository.save(BeerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
       Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
       beer.setBeerName(beerDto.getBeerName());
       beer.setBeerStyle(BeerStyleEnum.valueOf(beerDto.getBeerStyle()));
       beer.setPrice(beerDto.getPrice());
       beer.setUpc(beerDto.getUpc());
       return BeerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache",condition = "#showInventoryOnHand == false")
    public BeerPagedList getAllBeers(String beerName, BeerStyleEnum beerStyle, Boolean showInventoryOnHand,Pageable pageable) {

        log.info("I was called");
      Page<Beer>beers;

        if (!ObjectUtils.isEmpty(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            beers = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageable);
        } else if (!ObjectUtils.isEmpty(beerName) && ObjectUtils.isEmpty(beerStyle)) {
            beers = beerRepository.findAllByBeerName(beerName, pageable);
        } else if (ObjectUtils.isEmpty(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            beers = beerRepository.findAllByBeerStyle(beerStyle, pageable);
        } else {
            beers = beerRepository.findAll(pageable);
        }
        if(showInventoryOnHand) {
            beers.stream().forEach(beer ->
                    beer.setQuantityOnHand(
                            beerInventory.getOnHandQuantity(beer.getId())));
        }
        return new BeerPagedList(beers.stream()
                .map(BeerMapper::beerToBeerDto)
                .collect(Collectors.toList()),PageRequest
                .of(beers.getPageable().getPageNumber(),beers.getPageable().getPageSize()),beers.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames = "beerUpcCache")
    public BeerDto getBeerByUpc(String upc) {
        log.info("I was called");
        return BeerMapper.beerToBeerDto(beerRepository.getBeerByUpc(upc));
    }

}
