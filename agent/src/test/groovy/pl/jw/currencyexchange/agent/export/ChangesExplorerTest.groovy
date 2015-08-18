package pl.jw.currencyexchange.agent.export

import java.time.LocalDate

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

import pl.jw.currency.exchange.dao.api.Transaction
import pl.jw.currency.exchange.dao.api.TransactionType
import pl.jw.currencyexchange.agent.export.ChangesExporter.TransactionKey
import pl.jw.currencyexchange.agent.export.data.DailyCurrencyTransaction
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ChangesExplorerTest extends Specification {

	def mapByTransactionDirection(){

		when:
		def Map<TransactionKey, BigDecimal> data = new ChangesExporter().mapByTransactionDirection(transactions)

		then:
		MatcherAssert.assertThat( data.entrySet(), Matchers.equalTo(result.entrySet()))

		where:
		transactions || result
		null || Collections.emptyMap()
		[]|| Collections.emptyMap()
		//ignore expected of transaction of unknown or canceled type
		[new Transaction(currencySymbol: 'PLN', number: '1', price: 12.22g, quantity: 23.1g, type: TransactionType.UNKNOWN, value: 1.6g), new Transaction(currencySymbol: 'PLN', number: '1', price: 12.22g, quantity: 23.1g, type: TransactionType.CANCELATION, value: 1.6g)]|| Collections.emptyMap()
		[new Transaction(currencySymbol: 'PLN', number: '1', price: 12.22g, quantity: 23.1g, type: TransactionType.BUY, value: 1.6g)]|| [   (new TransactionKey('PLN', false)): 23.1g  ]
		//both directions, same currency
		[new Transaction(type: TransactionType.BUY, quantity: 12.45g, currencySymbol: 'PLN', number: '1'), new Transaction(type: TransactionType.SELL, quantity: 86.19g, currencySymbol: 'PLN', number: '2')]||
		[(new TransactionKey('PLN', false)): 12.45g, (new TransactionKey('PLN', true)): 86.19g]
		//both directions, multiple currencies
		[
			new Transaction(type: TransactionType.BUY, quantity: 12.45g, currencySymbol: 'PLN', number: '1'),
			new Transaction(type: TransactionType.SELL, quantity: 86.19g, currencySymbol: 'PLN', number: '2') ,
			new Transaction(type: TransactionType.BUY, quantity: 85.38g, currencySymbol: 'GBP', number: '3'),
			new Transaction(type: TransactionType.SELL, quantity: 1.75g, currencySymbol: 'GBP', number: '4')
		]||
		[(new TransactionKey('PLN', false)): 12.45g, (new TransactionKey('PLN', true)): 86.19g, (new TransactionKey('GBP', false)): 85.38g, (new TransactionKey('GBP', true)): 1.75g]
		//both directions, multiple currencies, multiple transactions in the same currencies
		[
			new Transaction(type: TransactionType.BUY, quantity: 12.45g, currencySymbol: 'PLN', number: '1'),
			new Transaction(type: TransactionType.SELL, quantity: 86.19g, currencySymbol: 'PLN', number: '2') ,
			new Transaction(type: TransactionType.BUY, quantity: 2.85g, currencySymbol: 'PLN', number: '3'),
			new Transaction(type: TransactionType.BUY, quantity: 0.7g, currencySymbol: 'PLN', number: '4'),
			new Transaction(type: TransactionType.SELL, quantity: 4.81g, currencySymbol: 'PLN', number: '5') ,
			new Transaction(type: TransactionType.BUY, quantity: 85.38g, currencySymbol: 'GBP', number: '7'),
			new Transaction(type: TransactionType.SELL, quantity: 1.75g, currencySymbol: 'GBP', number: '8'),
			new Transaction(type: TransactionType.BUY, quantity: 15.62g, currencySymbol: 'GBP', number: '9'),
			new Transaction(type: TransactionType.SELL, quantity: 2.25g, currencySymbol: 'GBP', number: '10')
		]||
		[(new TransactionKey('PLN', false)): 16.00g, (new TransactionKey('PLN', true)): 91.00g, (new TransactionKey('GBP', false)): 101.00g, (new TransactionKey('GBP', true)): 4.00g]
	}

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
