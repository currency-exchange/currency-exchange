package pl.jw.currencyexchange.agent.export

import java.time.LocalDate

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

import pl.jw.currency.exchange.dao.api.Transaction
import pl.jw.currency.exchange.dao.api.TransactionType
import pl.jw.currencyexchange.agent.export.data.DailyCurrencyTransaction
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ChangesExplorerTest extends Specification {

	def conversionTransaction() {

		when:
		def List<DailyCurrencyTransaction> data = new ChangesExporter().conversionTransaction(transactions)

		then:
		MatcherAssert.assertThat data, Matchers.hasItems(result)

		where:
		transactions || result
		null || []
		[]|| []
		[new Transaction(currencySymbol: 'PLN', number: '1', price: 12.22g, quantity: 23.1g, type: TransactionType.BUY, value: 1.6g)]|| [new DailyCurrencyTransaction(currencySymbol: 'PLN', bought: 23.1g, sold: null, location: null, date: LocalDate.now())]
		//both directions
		[new Transaction(type: TransactionType.BUY, quantity: 12.45g, currencySymbol: 'PLN', number: '1'), new Transaction(type: TransactionType.SELL, quantity: 86.19g, currencySymbol: 'PLN', number: '2')]|| [new DailyCurrencyTransaction(currencySymbol: 'PLN', bought: 12.45g, sold: 86.19g, location: null, date: LocalDate.now())]
	}
}
