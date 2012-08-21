package pl.jw.currencyexchange.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import pl.jw.currency.exchange.api.CurrencyData;
import pl.jw.currency.exchange.api.ICurrencyDAO;

public class DataSynchronizer implements IDataSynchronizer {

	private static Logger log = Logger.getLogger(DataSynchronizer.class);

	private ICurrencyDAO currencyDAO;

	@Autowired
	public void setCurrencyDAO(ICurrencyDAO currencyDAO) {
		this.currencyDAO = currencyDAO;
	}

	@Override
	public Set<CurrencyData> synchronize(Set<CurrencyData> setAcctualData) {
		log.debug("Data synchronization - acctual data " + setAcctualData.size());

		List<CurrencyData> listNewData = currencyDAO.get();
		log.debug("Data synchronization - new data " + setAcctualData.size());

		Map<String, CurrencyData> mapNewData = new HashMap<>();
		for (CurrencyData currencyData : listNewData) {

			mapNewData.put(currencyData.getSymbol(), currencyData);
		}

		for (CurrencyData currencyData : setAcctualData) {

			if (mapNewData.containsKey(currencyData.getSymbol())) {
				log.debug("Data synchronization - synchronized " + currencyData.getSymbol());

				CurrencyData currencyDataSyn = mapNewData.get(currencyData.getSymbol());
				BeanUtils.copyProperties(currencyDataSyn, currencyData);
			} else {
				log.debug("Data synchronization - checked " + currencyData.getSymbol());

				currencyData.setBuyPrice(BigDecimal.ZERO);
				currencyData.setSellPrice(BigDecimal.ZERO);
			}
		}

		return setAcctualData;
	}

}
