package algorithms.search;

import java.util.Stack;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */
public abstract class CommonStackSearcher<T> extends CommonSearcher<T> {

	private Stack<State<T>> visited;
	private Stack<State<T>> unVisited;
	
	public CommonStackSearcher() {
	this.visited=new Stack<State<T>>();
	this.unVisited=new Stack<State<T>>();
	this.setNumberNodesEvaluated(0);
	}
	
	public State<T> popFromUnvisited()
	{
		this.setNumberNodesEvaluated(1);
		return this.unVisited.pop();
	}
	
	public void removeFromUnvisited(State<T> state)
	{
		if(this.unVisited.contains(state))
		{
			this.unVisited.remove(state); 
		}
	}
	
	public State<T> popFromVisited()
	{
		//TODO: check if there is a need to decree evaluated node by 1
		return this.visited.pop();
	}
	
	public void removeFromVisited(State<T> state)
	{
		if(this.visited.contains(state))
		{
			this.visited.remove(state);
		}
	}
	public void pushToUnvisited(State<T> state)
	{
		if(state!=null)
		{
			this.unVisited.push(state);
		}
	}
	public void pushToVisited(State<T> state)
	{
		if(state!=null)
		{
			this.visited.push(state);
		}
	}
	@Override
	public abstract Solution<T> search(Searchable<T> searchable);

	public Stack<State<T>> getVisited() {
		return visited;
	}

	public void setVisited(Stack<State<T>> visited) {
		this.visited = visited;
	}

	public Stack<State<T>> getUnVisited() {
		return unVisited;
	}

	public void setUnVisited(Stack<State<T>> unVisited) {
		this.unVisited = unVisited;
	}
	 public void clearUnvisitedStack()
	 {
		 this.unVisited.clear();
	 }
	 public void clearVisitedStack()
	 {
		 this.visited.clear();
	 }
}
