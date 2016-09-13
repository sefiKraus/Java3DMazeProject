package io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
/**
 * 
 * @author sefy1
 * @since 01/09/2016
 * Compressor
 */
public class MyCompressorOutputStream extends OutputStream {
	
	private DataOutputStream out;
	
/**
 * 
 * @param out - OutputStream type
 */
	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = new DataOutputStream(out);
	}

	
	@Override
	public void write(byte[] b) throws IOException
	{
		this.write(b.length);
		if(b.length>1)
		{
			int digit=b[0];
			int size=1;
			for(int i=1;i<b.length;i++)
			{
				if(digit!=b[i])
				{
					this.write(digit);
					this.write(size);
					digit=b[i];
					size=1;
					continue;
				}
				size++;
			}
			this.write(digit);
			this.write(size);
		}
		else
			if(b.length==1)
			{
				this.write(b[0]);
				this.write(1);
				return;
			}
		
	}	
	@Override
	public void write(int b) throws IOException {
		out.writeInt(b);
	}
	

}
