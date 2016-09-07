package algorithms.search;

import java.util.ArrayList;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */
public class Bfs<T> extends CommonPriorityQueueSearcher<T> {
/**
 * This class solves the problem with Best first search algorithm
 */
	
	public Bfs() {
		super();
	}	
	@Override
	public Solution<T> search(Searchable<T> searchable) {

		this.addToOpenList(searchable.getStartState());
		while(open.size()>0)
		{
			State<T> n= popOpenList();

			closed.add(n);
			if(n.equals(searchable.getGoalState()))
			{
				return backTrace(n,searchable.getStartState());
			}
			ArrayList<State<T>> nodes=searchable.getAllPossibleMoves(n);
			if(nodes.size()==0)
			{
				addToOpenList(n.getCameFrom());
				closed.remove(n.getCameFrom());
			}
			else
			{

			for (State<T> s : nodes) {
				if(!closed.contains(s) && !open.contains(s))
				{
					s.setCameFrom(n);
					s.setCost(s.getCost()+n.getCost());
					addToOpenList(s);
				}
				else
				{
					if(s.getCameFrom()!=null && n.getCost()< s.getCameFrom().getCost() )
					{
						s.setCameFrom(n);
						s.setCost(n.getCost()+s.getCost());
						if(!open.contains(s))
						{
							addToOpenList(s);
						}
						else
						{
							removeFromOpenList(s);
							addToOpenList(s);
						}
					}
				}
			}
			}
		}
		return null;
	}
	
}
