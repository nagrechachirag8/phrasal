package mt.decoder.recomb;

import java.util.*;

import mt.decoder.util.State;


/**
 * 
 * @author danielcer
 *
 * @param <S>
 */
public class RecombinationHistory<S extends State<S>> {

  public static boolean pruneDiscarded = false;

  private final HashMap<S,List<S>> historyMap = new HashMap<S,List<S>>();
	
	/**
	 * 
	 * @param retained
	 * @param discarded
	 */
	public void log(S retained, S discarded) {
    if (discarded == null || pruneDiscarded) {
			return;
		}
		List<S> discardedList = historyMap.get(discarded);
		List<S> retainedList  = historyMap.get(retained);
		if (retainedList == null) {
			retainedList = new LinkedList<S>();
			historyMap.put(retained, retainedList);
		}
		if (discardedList != null) {
			historyMap.remove(discarded);
			retainedList.addAll(discardedList);
		}
		retainedList.add(discarded);
	}
	
	/**
	 * 
	 * @param pruned
	 */
	public void remove(S pruned) {
		historyMap.remove(pruned);
	}
	
	/**
	 * 
	 * @param retainedState
	 * @return
	 */
	public List<S> recombinations(State<S> retainedState) {
		return historyMap.get(retainedState);
	}
}
