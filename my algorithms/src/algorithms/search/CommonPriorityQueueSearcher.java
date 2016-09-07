package algorithms.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */
public abstract class CommonPriorityQueueSearcher<T> extends CommonSearcher<T> {
	protected PriorityQueue<State<T>> open;
	protected HashSet<State<T>> closed;
	
	public CommonPriorityQueueSearcher() {
		this.open=new PriorityQueue<State<T>>(new Comparator<State<T>>() {

			@Override
			public int compare(State<T> o1, State<T> o2) {
				
				return (int)(o1.getCost()-o2.getCost());
			}
			
		});	
		closed= new HashSet<State<T>>();
		this.setNumberNodesEvaluated(0);
	}
	
	protected  void addToOpenList(State<T> state)
	{
		open.offer(state);
	}

	protected State<T> popOpenList()
	{
		this.addEvaluatedNodes(1);
		return open.poll();
	}
	protected void removeFromOpenList(State<T> state)
	{
		open.remove(state);
	}
	
/*	public PriorityQueue<State<T>> getOpen() {
		return open;
	}

	public void setOpen(PriorityQueue<State<T>> open) {
		this.open = open;
	}

	public HashSet<State<T>> getClosed() {
		return closed;
	}

	public void setClosed(HashSet<State<T>> closed) {
		this.closed = closed;
	}*/

	@Override
	public abstract Solution<T> search(Searchable<T> searchable);
	


}
