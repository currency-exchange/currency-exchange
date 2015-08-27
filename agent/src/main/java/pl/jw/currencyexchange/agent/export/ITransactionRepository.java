package pl.jw.currencyexchange.agent.export;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.jw.currencyexchange.agent.export.data.TransactionsSummaryDaily;

public interface ITransactionRepository extends MongoRepository<TransactionsSummaryDaily, String> {

	Long deleteByLocationAndDate(String location, LocalDate date);
}
