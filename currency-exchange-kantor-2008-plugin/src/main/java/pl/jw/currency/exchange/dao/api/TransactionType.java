package pl.jw.currency.exchange.dao.api;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Stream;

public enum TransactionType {

	// PLUS
	SELL,

	SELL_TO_BANK,

	IN,

	IN_BANK,

	INPUT,

	// MINUS
	BUY,

	OUT,

	OUT_BANK,

	OUTPUT,

	// ZERO
	UNKNOWN,

	CANCELATION;

	public static TransactionType byName(String name) {
		Objects.requireNonNull(name);

		return Stream.of(values()).filter(e -> e.name().equalsIgnoreCase(name.trim())).findFirst().orElseThrow(
				() -> new RuntimeException(MessageFormat.format("No enum value for name {0} definied.", name)));
	}

	/**
	 * Cancelation or unknown - interpreted as cash box state not changed.
	 *
	 * @return
	 */
	public boolean isZero() {
		return this == SELL || this == SELL_TO_BANK || this == IN || this == IN_BANK || this == INPUT;
	}

	/**
	 * Cash box state increased
	 *
	 * @return
	 */
	public boolean isPlus() {
		return this == SELL || this == SELL_TO_BANK || this == IN || this == IN_BANK || this == INPUT;
	}

}
