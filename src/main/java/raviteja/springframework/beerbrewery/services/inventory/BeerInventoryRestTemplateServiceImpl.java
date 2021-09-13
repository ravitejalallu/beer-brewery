package raviteja.springframework.beerbrewery.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raviteja.springframework.beerbrewery.services.inventory.model.BeerInventoryDto;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j

public class BeerInventoryRestTemplateServiceImpl  implements  BeerInventory {

    public final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    private final RestTemplate restTemplate;
    @Value("${ravi.brewery.apihost}")
    private String apiHost;


    public BeerInventoryRestTemplateServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public Integer getOnHandQuantity(UUID beerId) {
        ResponseEntity<BeerInventoryDto> exchange = restTemplate.exchange(apiHost + INVENTORY_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<BeerInventoryDto>() {
                }, beerId);
        Integer quantityOnHand = Objects.requireNonNull(exchange.getBody()).getQuantityOnHand();
        return quantityOnHand;

    }
}