package raviteja.springframework.beerbrewery.services.inventory.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raviteja.springframework.beerbrewery.web.model.BeerStyleEnum;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BeerInventoryDto extends BaseItem{

    @Builder
    public BeerInventoryDto(UUID id,
                            Long version,
                            OffsetDateTime createdDate,
                            OffsetDateTime lastModifiedDate,
                            String upc,
                            String beerName,
                            BeerStyleEnum beerStyle,
                            UUID beerId,
                            Integer quantityOnHand) {
        super(id, version, createdDate, lastModifiedDate);
        this.upc = upc;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.beerId = beerId;
        this.quantityOnHand = quantityOnHand;
    }


    private String upc;
    private String beerName;
    private BeerStyleEnum beerStyle;
    private UUID beerId;
    private Integer quantityOnHand;
}