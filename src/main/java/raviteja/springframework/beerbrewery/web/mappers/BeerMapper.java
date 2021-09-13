package raviteja.springframework.beerbrewery.web.mappers;

import raviteja.springframework.beerbrewery.domain.Beer;
import raviteja.springframework.beerbrewery.web.model.BeerDto;
import raviteja.springframework.beerbrewery.web.model.BeerStyleEnum;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class BeerMapper {

    public static BeerDto beerToBeerDto(Beer obj){
        return BeerDto.builder()
                .id(obj.getId())
                .beerName(obj.getBeerName())
                .beerStyle(obj.getBeerStyle().name())
                .quantityOnHand(obj.getQuantityOnHand())
                .price(obj.getPrice())
                .createdDate(asOffsetDateTime(obj.getCreatedDate()))
                .lastModifiedDate(asOffsetDateTime(obj.getModifiedDate()))
                .upc(obj.getUpc())
                .version(obj.getVersion())
                .build();


    }

    public static Beer beerDtoToBeer(BeerDto beerDto){
      return Beer.builder()
                .beerName(beerDto.getBeerName())
                .beerStyle(BeerStyleEnum.valueOf(beerDto.getBeerStyle()))
                .quantityOnHand(beerDto.getQuantityOnHand())
                .price(beerDto.getPrice())
                .upc(beerDto.getUpc())
                .createdDate(asTimestamp(beerDto.getCreatedDate()))
                .modifiedDate(asTimestamp(beerDto.getLastModifiedDate()))
                .version(beerDto.getVersion())
                .id(beerDto.getId())
                .build();

    }

    public static OffsetDateTime asOffsetDateTime(Timestamp ts){
        if (ts != null){
            return OffsetDateTime.of(ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
                    ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
                    ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    public static Timestamp asTimestamp(OffsetDateTime offsetDateTime){
        if(offsetDateTime != null) {
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        } else {
            return null;
        }
    }
}
