package pl.jw.currency.exchange.dao.api;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Stream;

public enum TransactionType {

	BUY,

	SELL,

	SELL_TO_BANK,

	CANCELATION,

	IN,

	OUT,

	IN_BANK,

	OUT_BANK,

	INPUT,

	OUTPUT,

	OTHER;

	public static TransactionType byName(String name) {
		Objects.requireNonNull(name);

		return Stream
				.of(values())
				.filter(e -> e.name().equalsIgnoreCase(name.trim()))
				.findFirst()
				.orElseThrow(
						() -> new RuntimeException(MessageFormat.format(
								"No enum value for name {0} definied.", name)));
	}
}
