package pl.jw.currencyexchange.model.mongo.dao;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.jw.currencyexchange.model.mongo.data.TransactionsSummaryDaily;

public interface ITransactionRepository extends MongoRepository<TransactionsSummaryDaily, String> {

	Long deleteByLocationAndDate(String location, LocalDate date);
}
