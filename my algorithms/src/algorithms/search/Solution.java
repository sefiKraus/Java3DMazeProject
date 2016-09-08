
package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * Solution class contains problem's solution
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */
@SuppressWarnings("serial")
public class Solution<T> implements Serializable {

	private ArrayList<T> solution;

	public Solution() {
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((solution == null) ? 0 : solution.hashCode());
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
		Solution other = (Solution) obj;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		return true;
	}


	public Solution(ArrayList<T> solution) {
		super();
		this.solution = solution;
	}

	public ArrayList<T> getSolution() {
		return solution;
	}

	public void setSolution(ArrayList<T> solution) {
		this.solution = solution;
	}
	
	
}
