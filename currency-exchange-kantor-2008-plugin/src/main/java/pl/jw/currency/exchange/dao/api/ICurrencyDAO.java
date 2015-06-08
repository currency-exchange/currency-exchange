package pl.jw.currency.exchange.dao.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyDAO {

	List<CurrencyState> get();

	BigDecimal getCashboxState();

	List<Transaction> getTransactions(LocalDate date);

}
