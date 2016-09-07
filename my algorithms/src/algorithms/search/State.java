package algorithms.search;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 * @category State<T> Class
 *
 */
public class State<T> {

	private T state;
	private int cost;
	private State<T> cameFrom;
	
	public State(T state)
	{
		this.state=state;
		this.cost=0;
		this.cameFrom=null;
	}
	public State(State<T> state)
	{
		this.state=state.getState();
		this.cost=state.getCost();
		this.cameFrom=state.getCameFrom();
		
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State<?> other = (State<?>) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	public T getState() {
		return state;
	}
	public void setState(T state) {
		this.state = state;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	
	
	
	
}
