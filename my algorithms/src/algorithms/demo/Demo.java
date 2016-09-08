package algorithms.demo;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Bfs;
import algorithms.search.CommonSearcher;
import algorithms.search.Dfs;
import algorithms.search.Searchable3dMaze;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * 
 * @author sefy1
 * @since 02/08/2016
 */
public class Demo {

	public void Run() throws IOException
	{


		String size="5,30,30";

		Maze3d maze=new MyMaze3dGenerator().generate(size);
		
		  maze.printMaze();
		System.out.println("Start position: "+maze.getStartPosition());
		System.out.println("Goal position: "+maze.getGoalPosition());


		CommonSearcher<Position> searcher1;
		CommonSearcher<Position>searcher2;
		Searchable3dMaze searchable3dMaze=new Searchable3dMaze(maze);
		ArrayList<Position> solution;
		
		searcher1= new Bfs<Position>();
		 System.out.println("Test for the bfs solution algorithm");
		solution=(searcher1.search(searchable3dMaze)).getSolution();
		System.out.println("Solution with Bfs is: "+solution);
		System.out.println("Number of nodes evaluated: "+searcher1.getNumberOfNodesEvaluated());
	
		
		
		 searcher2=new Dfs<Position>();
		 System.out.println("Test for the dfs solution algorithm");
		 solution=searcher2.search(searchable3dMaze).getSolution();
		 System.out.println("Solution with Dfs is: "+solution);
		 System.out.println("Number of nodes evaluated: "+searcher2.getNumberOfNodesEvaluated());

		 //Compressor and Decompressor Test
		OutputStream out=new MyCompressorOutputStream(new FileOutputStream("1.maze"));
		out.write(maze.toByteArray());
		out.flush();
		out.close();
		
		InputStream in1=new MyDecompressorInputStream(new FileInputStream("1.maze"));
		byte b[]=new byte[maze.toByteArray().length];
		in1.read(b);	
		in1.close();
		
		Maze3d loaded=new Maze3d(b);
		System.out.println(loaded.equals(maze));

	}
	public static void main(String[] args) throws IOException {
		Demo demo=new Demo();
		demo.Run();

	}

}
