package algorithms.search;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 02/08/2016
 */
public class Searchable3dMaze implements Searchable<Position> {
	private Maze3d maze;

	
	public Searchable3dMaze(Maze3d maze) {
		super();
		this.maze = maze;
	}

	@Override
	public State<Position> getStartState() {
		State<Position> startState=new State<Position>(maze.getStartPosition());
		startState.setCost(0);
		
		return startState;
	}

	@Override
	public State<Position> getGoalState() {
		State<Position> goalState=new State<Position>(maze.getGoalPosition());
		goalState.setCost(1);
		return goalState;
	}

	/**
	 * @param State<Position> state 
	 * @return ArrayList<State<Position>> possibleMoveList list that contains all points that are
	 *  reachable from state
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleMoves(State<Position> state) {
		
		
		String[] possibleMoves=this.maze.getAllPossibleMoves(state.getState());
		ArrayList<State<Position>> possibleMoveList=new ArrayList<State<Position>>();
		Position p;
		State<Position> possibleMoveStatePosition;
		for(int i=0;i<possibleMoves.length;i++)
		{
			if(possibleMoves[i]!=null)
			{
				 p= new Position(possibleMoves[i]);
				 possibleMoveStatePosition=new State<Position>(p);
				 possibleMoveStatePosition.setCost(1);
				possibleMoveList.add(possibleMoveStatePosition);
			}
		}
		return possibleMoveList;
	}

	
}
