package pl.jw.currencyexchange.agent.export;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.jw.currencyexchange.agent.export.data.Currency;

public interface ICurrencyRepository extends MongoRepository<Currency, String> {
}
