package raviteja.springframework.beerbrewery.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import raviteja.springframework.beerbrewery.domain.Beer;
import raviteja.springframework.beerbrewery.repositories.BeerRepository;
import raviteja.springframework.beerbrewery.web.model.BeerStyleEnum;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BeerLoader implements CommandLineRunner {


    public static final String  Beer_1_UPC = "0631234200036";
    public static final String Beer_2_UPC = "0631234300019";
    public static final String Beer_3_UPC = "0083783375213";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");



    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeer();
    }

    private void loadBeer() {
        if(beerRepository.count()==0){
            beerRepository.save(Beer.builder().beerStyle(BeerStyleEnum.ALE)
                    .beerName("Mango bobs")
                    .id(BEER_1_UUID)
                    .upc(Beer_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());
            beerRepository.save(Beer.builder().beerStyle(BeerStyleEnum.PALE_ALE)
                    .beerName("banana bobs")
                    .id(BEER_2_UUID)
                    .upc(Beer_2_UPC)
                    .price(new BigDecimal("10.95"))
                    .build());
            beerRepository.save(Beer.builder().beerStyle(BeerStyleEnum.GOSE)
                    .beerName("Apple bobs")
                    .id(BEER_3_UUID)
                    .upc(Beer_3_UPC)
                    .price(new BigDecimal("110.95"))
                    .build());
            System.out.println(beerRepository.count());
        }
    }
}
