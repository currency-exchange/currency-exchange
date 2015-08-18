package pl.jw.currencyexchange.agent.export;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.jw.currencyexchange.agent.export.data.CashBox;

public interface ICashBoxRepository extends MongoRepository<CashBox, String> {
}
