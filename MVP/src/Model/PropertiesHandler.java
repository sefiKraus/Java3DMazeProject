package Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Presenter.Properties;

public class PropertiesHandler {
	private static Properties properties;
	
	public PropertiesHandler() {
	
	}
	public static Properties getProperties() throws FileNotFoundException
	{
		if(properties==null)
		{
			properties=readProperties("prop/properties/properties.xml");
		}
		return properties;
	}
	
	public static Properties readProperties(String path) throws FileNotFoundException
	{
		XMLDecoder xmldecoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
		Properties prop=(Properties)xmldecoder.readObject();
		xmldecoder.close();
		return prop;
	}
	
	public static void writeProperties(Properties properties,String path) throws FileNotFoundException
	{
		XMLEncoder xmlencoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
		xmlencoder.writeObject(properties);
		xmlencoder.flush();
		xmlencoder.close();
	}
}
