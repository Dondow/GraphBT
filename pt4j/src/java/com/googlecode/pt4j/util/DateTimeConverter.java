package com.googlecode.pt4j.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 * Converts 2008/02/28 23:30:11 GMT to/from a Joda DateTime
 *
 * @author Jon Stevens
 */
public class DateTimeConverter implements Converter
{
	private static final String PRINT_FORMAT = "yyyy/MM/dd HH:mm:ss GMT";
	private static final DateTimeFormatter fmtPrint = DateTimeFormat.forPattern(PRINT_FORMAT);
	private static final String PARSE_FORMAT = "yyyy/MM/dd HH:mm:ss zzz";
	private static final DateTimeFormatter fmtParse = DateTimeFormat.forPattern(PARSE_FORMAT);

	/** */
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		DateTime data = (DateTime) source;
		String date = fmtPrint.print(data);
		writer.setValue(date);
	}

	/** */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		String data = reader.getValue();
		DateTime date = fmtParse.parseDateTime(data);
		return date;
	}

	/** */
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz)
	{
		return true;
	}
}
