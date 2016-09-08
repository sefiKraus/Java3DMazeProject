package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */
public class Dfs<T> extends CommonStackSearcher<T> {
/**
 * This class solves the problem with DFS algorithm
 */
	
	@Override
	public Solution<T> search(Searchable<T> searchable) {
		
		State<T>curPosition;
		curPosition=searchable.getStartState();
		ArrayList<State<T>>neighbors=new ArrayList<State<T>>();	
		do
		{
			if(!this.getVisited().contains(curPosition))
			{
				this.pushToVisited(curPosition);
				
			}
			neighbors=searchable.getAllPossibleMoves(curPosition);
			for (State<T> state : neighbors) {
				if(!this.getVisited().contains(state))
				{
					state.setCameFrom(curPosition);
					this.pushToUnvisited(state);
				}
			}
			if(!this.getUnVisited().isEmpty())
			{
				Collections.shuffle(getUnVisited());
				curPosition=this.popFromUnvisited();
				this.clearUnvisitedStack();
				
			}
			else
			{
				if(!curPosition.equals(searchable.getStartState()))
				{
					
					curPosition=curPosition.getCameFrom();
					this.removeFromVisited(curPosition);

				}
				else
				{
					curPosition=this.popFromVisited();
				}
			}
		}while(!curPosition.equals(searchable.getGoalState()));
		
		return this.backTrace(curPosition, searchable.getStartState());
		
	}

}
