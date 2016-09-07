package io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 
 * @author sefy1
 * @since 01/09/2016
 * Decompressor
 */
public class MyDecompressorInputStream extends InputStream {
	
	private DataInputStream in;
	private int size;
	
	
	public MyDecompressorInputStream(InputStream in) throws IOException {
		super();
		this.in = new DataInputStream(in);
		this.size=read();
	}
	
	public int getSize() {
		return size;
	}

	@Override
	public int read(byte[] b) throws IOException
	{
		int digit,length,i=0;
		while(in.available()>4)
		{
			digit=this.read();
			length=this.read();
			for(int j=i;j<(i+length);j++)
			{
				b[j]=(byte) digit;
			}
			i+=length;
		}
		return size;
		
	}
	@Override
	public int read() throws IOException {
		return in.readInt();
	}

	
}
