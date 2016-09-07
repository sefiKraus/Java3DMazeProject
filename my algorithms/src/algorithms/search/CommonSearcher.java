package algorithms.search;

import java.util.ArrayList;

import java.util.Collections;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */

public abstract class CommonSearcher<T> implements Searcher<T> {

	private int nodesEvaluated;

	

	@Override
	public abstract Solution<T> search(Searchable<T> searchable);

	@Override
	public int getNumberOfNodesEvaluated() {
		
		return this.nodesEvaluated;
	}
	public void setNumberNodesEvaluated(int value)
	{
		this.nodesEvaluated+=value;
	}
	public void addEvaluatedNodes(int value)
	{
		this.nodesEvaluated+=value;
	}
/**
 * 
 * @param start state 
 * @param goal	state
 * @return	ArrayList<state<T>> object that contains the solution for the problem
 */
	public Solution<T> backTrack(State<T>start,State<T>goal)
	{
		ArrayList<T> path=new ArrayList<T>();
		
		
		while(!start.equals(goal))
		{
			path.add(start.getState());
			start=start.getCameFrom();
		}
		path.add(goal.getState());
		Collections.reverse(path);
		for (T t : path) {
			System.out.print("("+t.toString()+") ->");
		}
		System.out.println();
		return new Solution<T>(path);
	}

}
