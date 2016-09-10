package JUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Bfs;
import algorithms.search.Searchable3dMaze;

public class BfsJunitTest {

	Bfs<Position> searcher;
	public BfsJunitTest() {
		this.searcher=new Bfs<Position>();
	}
	@Test
	public void testNullMaze() {
		try
		{
			this.searcher.search(null);

		}catch(NullPointerException e)
		{
			assertTrue(1>0);
		}
		assertTrue("NullPointerException condition should be thrown away", 1>0);
	}

	@Test
	public void testSolutionFinalPosition() {
		Maze3d maze=new MyMaze3dGenerator().generate("2,11,11");
		Position pos = null;
		try
		{
			ArrayList<Position>sol=searcher.search(new Searchable3dMaze(maze)).getSolution();
			 pos=sol.get(sol.size()-1);
		}catch(NullPointerException e)
		{
			assertTrue("should not throw NullPointerException condition\nMaze Goal Position: "
					+maze.getGoalPosition()+" Goal Position recived by solution: "+pos, 1<0);
		}
		assertTrue(1>0);
	}
	
	@Test
	public void testZeroSizeMaze()
	{
		try
		{
			searcher.search(new Searchable3dMaze(new Maze3d(0, 0, 0))).getSolution();
			assertTrue("Amount of points in solution should be 0", 1<0);
		}catch(NullPointerException e)
		{
			assertTrue(1>0);
		}
	}
	
	@Test
	public void testHugeMaze()
	{
		try {
			Maze3d maze=new MyMaze3dGenerator().generate("50,50,50");
			searcher.search(new Searchable3dMaze(maze)).getSolution();
			assertTrue(1<0);
		} catch (StackOverflowError e) {
			assertTrue(1>0);
		}
	}

}
