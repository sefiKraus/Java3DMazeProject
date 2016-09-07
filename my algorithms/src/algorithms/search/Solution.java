
package algorithms.search;

import java.util.ArrayList;
/**
 * 
 * Solution class contains problem's solution
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */
public class Solution<T> {

	private ArrayList<T> solution;

	
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
