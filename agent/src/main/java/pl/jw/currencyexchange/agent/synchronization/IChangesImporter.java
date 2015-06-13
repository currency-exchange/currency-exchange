package pl.jw.currencyexchange.agent.synchronization;

import pl.jw.currencyexchange.agent.data.SynchronizedDataState;

public interface IChangesImporter {

	public abstract SynchronizedDataState importCurrentState();

}