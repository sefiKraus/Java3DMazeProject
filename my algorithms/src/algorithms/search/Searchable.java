package algorithms.search;

import java.util.ArrayList;

public interface Searchable<T> {

	public State<T> getStartState();
	
	public State<T> getGoalState();
	
	public ArrayList<State<T>> getAllPossibleMoves(State<T> state);
	
}
