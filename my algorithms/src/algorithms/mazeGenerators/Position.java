package algorithms.mazeGenerators;

/**
 * 
 * @author Krausz Sefi 305371320
 * @since 30/07/2016	
 * @category Position Class
 *
 */
public class Position {

	private int y;
	private int x;
	private int z;
	private int value;

	public Position() {

	}
	
	/**
	 * 
	 * @param y
	 * @param x
	 * @param z
	 */
	public Position(int y, int x, int z) {
		super();
		this.y = y;
		this.x = x;
		this.z = z;
	
	}
/**
 * 
 * @param poString new position string
 */
	public Position(String poString)
	{
		String[] size=poString.split(",",3);
		this.setY(Integer.valueOf(size[0]));
		this.setX(Integer.valueOf(size[1]));
		this.setZ(Integer.valueOf(size[2]));
	}
	/**
	 * copy constructor
	 * @param position
	 */
	public Position(Position position)
	{
		if(position!=null)
		{
			this.y=position.getY();
			this.x=position.getX();
			this.z=position.getZ();
			
		}
	}
	@Override
	public String toString() {
	//	return "("+Integer.toString(y)+","+ Integer.toString(x)+","+ Integer.toString(z)+")";
		return Integer.toString(y)+","+ Integer.toString(x)+","+ Integer.toString(z);

	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		Position other = (Position) obj;
		if (value != other.value)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}



	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
	
	
}
