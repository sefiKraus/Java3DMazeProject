package Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Presenter.Properties;

public class PropertiesXmlHandler {

	private static Properties properties;
	
	public PropertiesXmlHandler() {
		
	}
	
	
	public static void writeProperties(Properties prop,String path) throws FileNotFoundException
	{
		XMLEncoder encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
		encoder.writeObject(prop);
		encoder.flush();
		encoder.close();
	}
	public static Properties getPropertiesInstance() throws FileNotFoundException
	{
		if(properties==null)
		{
			properties=readProperties("res/properties.xml");
		}
		return properties;
	}
	public static Properties readProperties(String path) throws FileNotFoundException
	{
		XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
		Properties prop=(Properties)decoder.readObject();
		decoder.close();
		return prop;
		
	}
	
}
