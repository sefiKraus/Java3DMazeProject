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
		
		this.pushToUnvisited(searchable.getStartState());
		
		while(this.getUnVisited().size()>0)
		{
			State<T> n= this.popFromUnvisited();
			if(!this.getVisited().contains(n))
			{
				this.pushToVisited(n);
				
			}
	
			if(n.equals(searchable.getGoalState()))
			{
			  return this.backTrack(n, searchable.getStartState());
			}
			
			ArrayList<State<T>> nodes=searchable.getAllPossibleMoves(n);
			if(nodes.size()==0)
			{
				pushToUnvisited(n.getCameFrom());
			}
			else
			{
				
		
			for (State<T> state : nodes) {
				if(!this.getUnVisited().contains(state)&& !this.getVisited().contains(state)&& state.getCameFrom()==null)
				{
					state.setCameFrom(n);
					state.setCost(state.getCost()+n.getCost());
					this.pushToUnvisited(state);
				}
				
				}
			}
			Collections.shuffle(this.getUnVisited());
			if(this.getUnVisited().isEmpty())
			{
				State<T> tempState=n.getCameFrom();
				this.pushToUnvisited(tempState);

			
			}
			else
			{
				State<T> popedFromUnvisited=this.popFromUnvisited();
				this.clearUnvisitedStack();
				this.pushToUnvisited(popedFromUnvisited);
				
			}
		
			
		}
		
		return null;
	}

}
