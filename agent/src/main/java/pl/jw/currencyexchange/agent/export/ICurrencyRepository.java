package pl.jw.currencyexchange.agent.export;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICurrencyRepository extends MongoRepository<Currency, Long> {
}