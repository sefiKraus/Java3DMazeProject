package algorithms.mazeGenerators;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
/**
 * 
 * @author Krausz Sefi 305371320
 * @since 30/07/2016
 * @category 3D maze Class
 *
 */
@SuppressWarnings("serial")
public class Maze3d implements Serializable{
//TODO: create a new generic method for legal move 
	 private int[][][] maze;
	private int sizeY;
	private int sizeX;
	private int sizeZ;
	
	private Position startPosition;
	private Position goalPosition;
	private Position playerPosition;
	
	public Maze3d(byte b[]) throws IOException {
		
		this.maze=new int[b[0]][b[1]][b[2]];
		int i=9;
		this.sizeY=b[0];
		this.sizeX=b[1];
		this.sizeZ=b[2];
		this.startPosition=new Position(b[3],b[4], b[5]);
		this.goalPosition=new Position(b[6], b[7], b[8]);
		for(int y=0;y<this.sizeY;y++)
		{
			for(int x=0;x<this.sizeX;x++)
			{
				for(int z=0;z<this.sizeZ;z++)
				{
					this.maze[y][x][z]=b[i];
					i++;
					
				}
			}
		}
	}
	
	/**
	 * 
	 * @param sizeY 
	 * @param sizeX
	 * @param sizeZ
	 */
	public Maze3d( int sizeY, int sizeX, int sizeZ) {
		super();
	
		this.sizeY = sizeY;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.setMaze(new int[sizeY][sizeX][sizeZ]);
		this.startPosition=new Position();
		this.goalPosition=new Position();
		this.playerPosition= new Position();
		
		
	}
	
	public byte[] toByteArray() throws IOException
	{
		int i=9;
		byte[] b=new byte[this.sizeX*this.sizeY*this.sizeZ+i];
		//Adding maze size to b
		b[0]=(byte)this.sizeY;
		b[1]=(byte)this.sizeX;
		b[2]=(byte)this.sizeZ;
		
		//Adding start position to b 
		b[3]=(byte)this.getStartPosition().getY();
		b[4]=(byte)this.getStartPosition().getX();
		b[5]=(byte)this.getStartPosition().getZ();
		
		//Adding goal position to b 
		
		b[6]=(byte)this.getGoalPosition().getY();
		b[7]=(byte)this.getGoalPosition().getX();
		b[8]=(byte)this.getGoalPosition().getZ();
		
		for(int y=0;y<this.sizeY;y++)
		{
			for(int x=0;x<this.sizeX;x++)
			{
				for(int z=0; z<this.sizeZ;z++)
				{
					b[i]=(byte)this.maze[y][x][z];
					i++;
				}
			}
		}
		return b;
		
	}
/**
 * 
 * @param y
 * @param x
 * @param z
 * @return value in position y,x,z
 */
	public int getPositionValueByIndex(int y,int x,int z)
	{
	/*	if(checkPositionInMaze(y, x, z))
		{
			return maze[y][x][z];
		}
		else
			return -1;*/
		return maze[y][x][z];

	}
	/**
	 * 
	 * @param pos - position we want to check for his value
	 * @return value of position inside the maze
	 */
	
	public int getPositionValueByPosition(Position pos)
	{
	/*	if(checkPositionInMaze(pos.getY(), pos.getX(), pos.getZ()))
		{
			return maze[pos.getY()][pos.getX()][pos.getZ()];
		}
		else
			return -1;*/
		return maze[pos.getY()][pos.getX()][pos.getZ()];

	}
	/**
	 * setting position value by position object
	 * @param pos
	 * @param value
	 * 
	 */
	public void setPositionValueByPosition(Position pos,int value)
	{
		if(checkLegalPositionForPlayer(pos))
		{
			maze[pos.getY()][pos.getX()][pos.getZ()]=value;
		}
		
	}
	/**
	 * setting position value by index
	 * @param y
	 * @param x
	 * @param z
	 * @param value
	 */
	public void setPositionValueByIndex(int y,int x,int z,int value)
	{
		if(checkPositionInMaze(y, x, z))
		{
			 maze[y][x][z]=value;
		}

	}
	
	/**
	 * This method helps to initialize the maze
	 * @param y
	 * @param x
	 * @param z
	 * @return True if (y,x,z) is legal inside the maze
	 */
	boolean checkPositionInMaze(int y,int x,int z)
	{
		return(y>=0&&y<=this.sizeY&&x>=0&&x<=this.sizeX&&z>=0&&z<=this.sizeZ );	
	}
	
	
	/**
	 * 
	 * @param pos - player position object
	 * @return True if the position is inside the maze means player.y<sizeY && player.x<sizeX && player.z<sizeZ
	 */
	boolean checkLegalPositionForPlayer(Position pos)
	{
		//return ( pos.getY()>0 && pos.getY()<this.sizeY-1 && pos.getX()>0 && pos.getX()<this.sizeX-1 && pos.getZ()>0 && pos.getZ()<this.sizeZ-1);
		return ( (pos.getY()>=0 && pos.getY()<this.sizeY) && (pos.getX()>=0 && pos.getX()<this.sizeX) &&( pos.getZ()>=0 && pos.getZ()<this.sizeZ));

	}
	
	/**
	 * 
	 * @param y player y position
	 * @param x player x position
	 * @param z player z position
	 * @return True if the position is inside the maze means y<sizeY-1 && x<sizeX-1 && z<sizeZ-1
	 */
	boolean checkLegalPositionForPlayer(int y,int x,int z)
	{
		return ( y>0 && y<this.sizeY-1 && x>0 && x<this.sizeX-1 && z>0 && z<this.sizeZ-1);
		//return ( (y>=0 && y<this.sizeY) && (x>=0 && x<this.sizeX) &&( z>=0 && z<this.sizeZ));

	}
	
	
	
	/**
	 * printing the maze
	 */
	public void printMaze()
	{
		for(int y=0; y<sizeY;y++)
		{
			for(int x=0;x<sizeX;x++)
			{
				for(int z=0;z<sizeZ;z++)
				{
				
					
					System.out.print(maze[y][x][z]+",");
				}
				System.out.println();
			}
			System.out.println();
		}
		
	}
/**
 * 
 * @param moveTo
 * @return check if the move is legal
 */
	public boolean legalMove(int moveTo)
	{
		boolean flag=false;
		switch (moveTo) {
		case 1:
			flag= checkLegalPositionForPlayer(this.playerPosition.getY()+1, this.playerPosition.getX(), this.playerPosition.getZ());

			break;
		case 2:
			flag= checkLegalPositionForPlayer(this.playerPosition.getY()-1, this.playerPosition.getX(), this.playerPosition.getZ());

			break;
		case 3:
			flag= checkLegalPositionForPlayer(this.playerPosition.getY(), this.playerPosition.getX()+1, this.playerPosition.getZ());

			break;
		case 4:
			flag= checkLegalPositionForPlayer(this.playerPosition.getY(), this.playerPosition.getX()-1, this.playerPosition.getZ());

			break;
		case 5:
			flag= checkLegalPositionForPlayer(this.playerPosition.getY(), this.playerPosition.getX(), this.playerPosition.getZ()+1);

			break;
		case 6:
			flag= checkLegalPositionForPlayer(this.playerPosition.getY(), this.playerPosition.getX(), this.playerPosition.getZ()-1);

			break;
		default:
			flag=false;
		}
		return flag;

	}
	

	public int[][][] getMaze() {
		return maze;
	}
	
/**
 * 
 * @param moveTo 
 * @return True if player can move to direction moveTo(1-6)
 */
	public boolean moveToDirection(int moveTo)
	{
		boolean flag= false;
		switch (moveTo) {
		case 1:
			flag=this.moveUp();
			break;
		case 2:
			flag=this.moveDown();
			break;
		case 3:
			flag=this.moveRight();
			break;
		case 4:
			flag=this.moveLeft();
			break;
		case 5:
			flag=this.moveIn();
			break;
		case 6:
			flag=this.moveOut();
			break;
		default:
			flag=false;
			break;
		}
		return flag;
		
	}
	/**
	 * 
	 * @return True if player can move up
	 */
	public boolean moveUp() // pressing 1 
	{
		if(legalMove(1))
		{
			this.playerPosition.setY(playerPosition.getY()+1);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return True if player can move down
	 */
	public boolean moveDown() //pressing 2 
	{
		if(legalMove(2))
		{
			this.playerPosition.setY(playerPosition.getY()-1);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return True if player can move left
	 */
	public boolean moveRight() //pressing 3 
	{
		if(legalMove(3))
		{
			this.playerPosition.setX(playerPosition.getX()+1);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return True if player can move right
	 */
	public boolean moveLeft()//pressing 4 
	{
		if(legalMove(4))
		{
			this.playerPosition.setX(playerPosition.getX()-1);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return True if player can move inside 
	 */
	public boolean moveIn()//pressing 5 
	{
		if(legalMove(5))
		{
			this.playerPosition.setZ(playerPosition.getZ()+1);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return True if player can move outside
	 */
	public boolean moveOut()//pressing 6 
	{
		if(legalMove(6))
		{
			this.playerPosition.setZ(playerPosition.getZ()-1);
			return true;
		}
		return false;
	}
	
	public void setMaze(int[][][] maze) {
		this.maze = maze;
	}

	/**
	 * 
	 * @return String[] with the legal Neighbors around the player will be used in Task 2 to solve the maze
	 */
	public String[] getAllPossibleMoves(Position position)
	{
		String[] allNeighbors=new String[6];
		if(legalMove(1)&& this.getPositionValueByIndex(position.getY()+1, position.getX(), position.getZ())==0)
		{
			
			allNeighbors[0]=(Integer.toString(position.getY()+1)+","+Integer.toString(position.getX())+","+Integer.toString(position.getZ()));
		}
		else
		{
			allNeighbors[0]=null;
				
		}
		
		if(legalMove(2)&& this.getPositionValueByIndex(position.getY()-1, position.getX(), position.getZ())==0)
		{
			allNeighbors[1]=(Integer.toString(position.getY()-1)+","+Integer.toString(position.getX())+","+Integer.toString(position.getZ()));
				
		}
		else
		{
			allNeighbors[1]=null;
				
		}
		if(legalMove(3)&& this.getPositionValueByIndex(position.getY(), position.getX()+1, position.getZ())==0)
		{
			allNeighbors[2]=(Integer.toString(position.getY())+","+Integer.toString(position.getX()+1)+","+Integer.toString(position.getZ()));
			
		}
		else 
		{
			allNeighbors[2]=null;
				
		}
		
		if(legalMove(4)&& this.getPositionValueByIndex(position.getY(), position.getX()-1, position.getZ())==0)
		{
			allNeighbors[3]=(Integer.toString(position.getY())+","+Integer.toString(position.getX()-1)+","+Integer.toString(position.getZ()));
			
		}
		else
		{
			allNeighbors[3]=null;
				
		}
		
		if(legalMove(5)&& this.getPositionValueByIndex(position.getY(), position.getX(), position.getZ()+1)==0)
		
		{
			allNeighbors[4]=(Integer.toString(position.getY())+","+Integer.toString(position.getX())+","+Integer.toString(position.getZ()+1));
			
		}
		else
		{
			allNeighbors[4]=null;
			
		}
		
		if(legalMove(6)&& this.getPositionValueByIndex(position.getY(), position.getX(), position.getZ()-1)==0)
		{
			allNeighbors[5]=(Integer.toString(position.getY())+","+Integer.toString(position.getX())+","+Integer.toString(position.getZ()-1));
				
		}
		else
		{
			allNeighbors[5]=null;
		}
			
		return allNeighbors;
	}
	
	/**
	 * This method is been used in MyMaze3dGenerator - helps to get other nodes 
	 * @param pos current position inside the maze
	 * @return all positions around pos
	 */
	String[] getAllPositionAround(Position pos)
	{
		String[] pointsAround= new String[6];
		if(checkLegalPositionForPlayer(pos.getY()+1,pos.getX(),pos.getZ()))
		{
			pointsAround[0]=(Integer.toString(pos.getY()+1))+","+(Integer.toString(pos.getX()))+","+Integer.toString(pos.getZ());
		}
		if(checkLegalPositionForPlayer(pos.getY()-1,pos.getX(),pos.getZ()))
		{
			pointsAround[1]=(Integer.toString(pos.getY()-1))+","+(Integer.toString(pos.getX()))+","+Integer.toString(pos.getZ());
		}
		if(checkLegalPositionForPlayer(pos.getY(),pos.getX()+2,pos.getZ()))
		{
			pointsAround[2]=(Integer.toString(pos.getY()))+","+(Integer.toString(pos.getX()+2))+","+Integer.toString(pos.getZ());
		}
		if(checkLegalPositionForPlayer(pos.getY(),pos.getX()-2,pos.getZ()))
		{
			pointsAround[3]=(Integer.toString(pos.getY()))+","+(Integer.toString(pos.getX()-2))+","+Integer.toString(pos.getZ());
		}
		if(checkLegalPositionForPlayer(pos.getY(),pos.getX(),pos.getZ()+2))
		{
			pointsAround[4]=(Integer.toString(pos.getY()))+","+(Integer.toString(pos.getX()))+","+Integer.toString(pos.getZ()+2);
		}
		if(checkLegalPositionForPlayer(pos.getY(),pos.getX(),pos.getZ()-2))
		{
			pointsAround[5]=(Integer.toString(pos.getY()))+","+(Integer.toString(pos.getX()))+","+Integer.toString(pos.getZ()-2);
		}
		
		
		return pointsAround;
	}
	
	/**
	 * 
	 * @param x 
	 * @return - Getting maze cross section with x's value
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByX(int x) throws IndexOutOfBoundsException
	{
		if(x-1 >this.sizeX || x<0 )
			throw new IndexOutOfBoundsException();
		int [][] maze2D= new int[this.sizeY][this.sizeZ];
		for(int i=0;i<this.sizeY;i++)
		{
			for(int j=0;j<this.sizeZ;j++)
			{
				maze2D[i][j]=getPositionValueByIndex(i, x, j);
			}
		}
		return maze2D;
		
	}
	/**
	 * 
	 * @param y
	 * @return- Getting maze cross section with y's value
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByY(int y)throws IndexOutOfBoundsException
	{
		if(y-1 >this.sizeY || y<0 )
			throw new IndexOutOfBoundsException();
		int [][] maze2D= new int[this.sizeX][this.sizeZ];
		for(int i=0;i<this.sizeX;i++)
		{
			for(int j=0;j<this.sizeZ;j++)
			{
				maze2D[i][j]=getPositionValueByIndex(y, i, j);
			}
		}
		return maze2D;
		
	}
	
	/**
	 * 
	 * @param z
	 * @return- Getting maze cross section with z's value
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByZ(int z)throws IndexOutOfBoundsException
	{
		if(z-1 >this.sizeZ || z<0 )
			throw new IndexOutOfBoundsException();
		int [][] maze2D= new int[this.sizeY][this.sizeX];
		for(int i=0;i<this.sizeY;i++)
		{
			for(int j=0;j<this.sizeX;j++)
			{
				maze2D[i][j]=getPositionValueByIndex(i, j, z);
			}
		}
		return maze2D;
		
	}
	/**
	 * 
	 * @param maze2d
	 * @param y 
	 * @param x
	 * This method prints a 2D maze
	 */
	public void printCrossSection(int[][] maze2d,int y,int x)
	{
		for(int i=0; i<y;i++)
		{
			for(int j=0;j<x;j++)
			{
				System.out.print(maze2d[i][j]+",");
			}
			System.out.println();
		}
	}
	
	public int getSizeY() {
		return sizeY;
	}

	

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}


	public int getSizeX() {
		return sizeX;
	}


	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}


	public int getSizeZ() {
		return sizeZ;
	}


	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}


	public Position getStartPosition() {
		return startPosition;
	}

/**
 * start position should be at the first floor - (1,x,z) 
 */
	public void setStartPosition() {
		this.startPosition.setY(1);
		Random movmentRequired=new Random();
		 int move=movmentRequired.nextInt(this.sizeX-2)+1;
		 if(move%2==0)
		 {
			 move+=1;
		 }
		 this.startPosition.setX(move);
		 move=movmentRequired.nextInt(this.sizeZ-2)+1;
		 if(move%2==0)
		 {
			 move+=1;
		 }
		 this.startPosition.setZ(move);
		 this.startPosition.setValue(0);
		 maze[startPosition.getY()][startPosition.getX()][startPosition.getZ()]=0;
	}


	public Position getGoalPosition() {
		return goalPosition;
	}

/**
 * goal position should be at the last floor - (sizeY-1,x,z)
 */
	public void setGoalPosition() {
		this.goalPosition.setY(this.sizeY-2);
		Random movmentRequired=new Random();
		 int move=movmentRequired.nextInt(this.sizeX-2)+1;
		 if(move%2==0)
		 {
			 move+=1;
		 }
		 this.goalPosition.setX(move);
		 move=movmentRequired.nextInt(this.sizeZ-2)+1;
		 if(move%2==0)
		 {
			 move+=1;
		 }
		 this.goalPosition.setZ(move);
		 this.goalPosition.setValue(0);
		 maze[goalPosition.getY()][goalPosition.getX()][goalPosition.getZ()]=0;
	}
/**
 * 
 * @param pos position inside the maze
 * @return if setting wall succeeded means checkPositionInMaze method sent back true
 */
	public boolean setWall(Position pos)
	{
		return (checkPositionInMaze(pos.getY(),pos.getX(),pos.getZ()));
		
	}
	public Position getPlayerPosition() {
		return playerPosition;
	}

	/**
	 * 
	 * @param playerPosition - setting player position by String
	 */
	public void setPlayerPosition(String playerPosition)
	{
		String[] parts=playerPosition.split(",", 3);
		this.playerPosition.setY(Integer.valueOf(parts[0]));
		this.playerPosition.setX(Integer.valueOf(parts[1]));
		this.playerPosition.setZ(Integer.valueOf(parts[2]));
	}
	public void setPlayerPosition(Position playerPosition) {
		this.playerPosition = playerPosition;
	}

	@Override
	public String toString() {
		return "Maze3d [maze=" + Arrays.toString(maze) + ", sizeY=" + sizeY + ", sizeX=" + sizeX + ", sizeZ=" + sizeZ
				+ ", startPosition=" + startPosition + ", goalPosition=" + goalPosition + ", playerPosition="
				+ playerPosition + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goalPosition == null) ? 0 : goalPosition.hashCode());
		result = prime * result + Arrays.deepHashCode(maze);
		result = prime * result + ((playerPosition == null) ? 0 : playerPosition.hashCode());
		result = prime * result + sizeX;
		result = prime * result + sizeY;
		result = prime * result + sizeZ;
		result = prime * result + ((startPosition == null) ? 0 : startPosition.hashCode());
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
		Maze3d other = (Maze3d) obj;
		if (goalPosition == null) {
			if (other.goalPosition != null)
				return false;
		} else if (!goalPosition.equals(other.goalPosition))
			return false;
		if (!Arrays.deepEquals(maze, other.maze))
			return false;
		if (startPosition == null) {
			if (other.startPosition != null)
				return false;
		} else if (!startPosition.equals(other.startPosition))
			return false;
		return true;
	}
	
	
	
	
}
