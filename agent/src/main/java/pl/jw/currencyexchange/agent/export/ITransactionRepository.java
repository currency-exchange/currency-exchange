package pl.jw.currencyexchange.agent.export;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.jw.currencyexchange.agent.export.data.DailyCurrencyTransaction;

public interface ITransactionRepository extends MongoRepository<DailyCurrencyTransaction, LocalDate> {
}
