package com.adjudication.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateRandomData {
	
	public String generateRandomString(int length){
		return RandomStringUtils.randomAlphabetic(length);
	}
	
	public String generateRandomAlphaNumericString(int length){
	   return RandomStringUtils.randomAlphanumeric(length);	
	}
	
	public String generateRandomNumber(int length){
		return RandomStringUtils.randomNumeric(length);
	}
	
	public int getRandomNumberWithRange(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	public String generateRandomEmailID(int length){
		   String email = RandomStringUtils.randomAlphanumeric(length).concat("@gmail.com");
		   return email;
	}
	
	public String generateRandomDate(Date startDate, Date endDate)
	{
		Date begin = new Date(startDate.getTime());
        LinkedList list = new java.util.LinkedList();
        list.add(new Date(begin.getTime()));
 
        while(begin.compareTo(endDate)<0)
        {
            begin = new Date(begin.getTime() + 86400000);
            list.add(new Date(begin.getTime()));
        }
        String[] comboDates = new String[list.size()];
        for(int i=0; i<list.size(); i++)
            comboDates[i] = new java.text.SimpleDateFormat("MM/dd/yyyy").format(((java.util.Date)list.get(i)));
        
        Random rand = new Random(); 
        return  comboDates[rand.nextInt(comboDates.length)];
       
	}
	
	
	/**
	 * Gets the first date of month.
	 *
	 * @param date the date
	 * @return the first date of month
	 */
	public Date getFirstDateOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
	
	public Date getCurrentDate(){
		return new Date();
	}
	
}
