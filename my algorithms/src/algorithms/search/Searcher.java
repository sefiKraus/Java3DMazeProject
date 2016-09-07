package algorithms.search;

public interface Searcher<T> {
/**
 * 
 * @param searchable
 * @return Solution<T> solution for the problem sent
 */
	public Solution<T> search(Searchable<T> searchable);
	
	public int getNumberOfNodesEvaluated();
	
	
}
