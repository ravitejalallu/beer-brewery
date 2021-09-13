package raviteja.springframework.beerbrewery.web.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raviteja.springframework.beerbrewery.web.model.BeerDto;
import raviteja.springframework.beerbrewery.web.model.BeerPagedList;
import raviteja.springframework.beerbrewery.web.model.BeerStyleEnum;
import raviteja.springframework.beerbrewery.services.BeerService;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RequestMapping("/api/v1")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    public static final Integer PAGE_NUMBER_DEFAULT = 0;
    public static final Integer PAGE_SIZE_DEFAULT = 25;



    @GetMapping("/beer/beers")
    public ResponseEntity<BeerPagedList>getAllBeers(@RequestParam(value = "pageNumber",required = false)  Integer pageNumber,
                                                    @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                                    @RequestParam(value = "beerName",required = false) String beerName,
                                                    @RequestParam(value = "beerStyle",required = false) BeerStyleEnum beerStyle,
                                                    @RequestParam(value = "showInventoryOnHand",required = false,defaultValue = "false") Boolean showInventoryOnHand
    ){
        if(pageNumber == null || pageNumber < 0){
            pageNumber =PAGE_NUMBER_DEFAULT;
        }
        if(pageSize == null || pageSize < 1){
            pageSize =PAGE_SIZE_DEFAULT;
        }

        return new ResponseEntity<BeerPagedList>(beerService.getAllBeers(beerName,beerStyle,showInventoryOnHand,PageRequest.of(pageNumber,pageSize)),HttpStatus.OK);
    }


    @GetMapping("/beer/{beerId}")
    public ResponseEntity<BeerDto>getBeerById(@PathVariable("beerId")UUID beerId,
                                              @RequestParam(value = "showInventoryOnHand",
            required = false,defaultValue = "false")Boolean showInventoryOnHand){
        return new ResponseEntity<BeerDto>(beerService.getBeerById(beerId,showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("beerUpc/{upc}")
    public ResponseEntity<BeerDto>getBeerByUpc(@PathVariable("upc")String upc){
        return new ResponseEntity<BeerDto>(beerService.getBeerByUpc(upc), HttpStatus.OK);
    }

    @PostMapping("/beer")
    public ResponseEntity<BeerDto>saveBeer( @Valid @RequestBody BeerDto beerDto){

        return new ResponseEntity<BeerDto>(beerService.saveBeer(beerDto),HttpStatus.CREATED);
    }

    @PutMapping("/beer/{beerId}")
    public ResponseEntity<BeerDto>updateBeer(@PathVariable("beerId") UUID beerId,@Valid @RequestBody BeerDto beerDto){
        return new ResponseEntity<BeerDto>(beerService.updateBeer(beerId,beerDto),HttpStatus.NO_CONTENT);
    }



}
