package raviteja.springframework.beerbrewery.services.inventory;

import java.util.UUID;

public interface BeerInventory {

    Integer getOnHandQuantity(UUID beerId);
}
