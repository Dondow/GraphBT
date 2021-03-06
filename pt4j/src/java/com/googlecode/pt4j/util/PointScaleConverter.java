package com.googlecode.pt4j.util;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.pt4j.data.PointScaleData;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 * Converts a comma separated list of Integers
 *
 * @author Jon Stevens
 */
public class PointScaleConverter implements Converter
{
	/** */
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		PointScaleData input = (PointScaleData) source;
		List<Integer> data = new ArrayList<Integer>(input.getScale().size());
		for (Integer tmp : input.getScale())
			data.add(tmp);

		StringBuilder sb = new StringBuilder();
		boolean firstTime = true;
		for (Integer tmp : data)
		{
			if (!firstTime)
				sb.append(",");
			sb.append(tmp);
			firstTime = false;
		}
		writer.setValue(sb.toString());
	}

	/** */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		String data = reader.getValue();
		String[] parsed = data.split(",");
		List<Integer> result = new ArrayList<Integer>(parsed.length);
		for (String tmp : parsed)
			result.add(new Integer(tmp));

		PointScaleData psd = new PointScaleData(result);

		return psd;
	}

	/** */
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz)
	{
		return clazz.equals(PointScaleData.class);
	}
}
