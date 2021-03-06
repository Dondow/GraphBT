package com.googlecode.pt4j.util;

import com.googlecode.pt4j.data.IterationLengthData;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 * Converts IterationLengthData
 *
 * @author Jon Stevens
 */
public class IterationLengthConverter implements Converter
{
	/** */
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		IterationLengthData data = (IterationLengthData) source;
		writer.addAttribute("type", data.getType());
		writer.setValue(data.getIterationLength().toString());
	}

	/** */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		String data = reader.getValue();
		IterationLengthData result = new IterationLengthData(new Long(data));
		return result;
	}

	/** */
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz)
	{
		return true;
	}
}
