package main.java.converter.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import main.java.converter.IStringToDate;

public class StringToDate implements IStringToDate {
	//public static IStringToDate stringToDate;
	
	public Date convertDate(String datumString){
		//Quelle: http://www.christian-klisch.de/java-simpledateformat.html
		Date date = null;
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = simpleDateFormat.parse(datumString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return date;
	}
}
