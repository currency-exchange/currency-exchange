package pl.jw.currency.exchange.dao.api;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyDAO {

	List<CurrencyData> get();

}
