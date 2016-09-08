
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
public class Solution<T> implements Serializable{

	private ArrayList<T> solution;

	public Solution() {
		// TODO Auto-generated constructor stub
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
