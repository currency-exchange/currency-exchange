package pl.jw.currencyexchange.agent.data;

import java.util.Comparator;

import org.springframework.stereotype.Service;

@Service
public class DataStateComparators {

	private static <T> boolean isChanged(Comparator<T> comparator, T before, T after) {
		return comparator.compare(before, after) != 0;
	}

	public static boolean isChanged(SynchronizedDataState state, SynchronizedDataState actualState) {
		return changedTransactions(state, actualState) || changedCurrencyState(state, actualState)
				|| changedCashboxState(state, actualState);
	}

	public static boolean changedCashboxState(SynchronizedDataState state, SynchronizedDataState actualState) {
		return state.getCashboxState().compareTo(actualState.getCashboxState()) != 0;
	}

	public static boolean changedCurrencyState(SynchronizedDataState state, SynchronizedDataState actualState) {
		return isChanged((before, after) -> before.getCurrencyState().equals(after.getCurrencyState()) ? 0 : 1, state,
				actualState);
	}

	public static boolean changedTransactions(SynchronizedDataState state, SynchronizedDataState actualState) {
		return isChanged((before, after) -> before.getTransactionsCount() == after.getTransactionsCount() ? 0 : 1,
				state, actualState);
	}
}
