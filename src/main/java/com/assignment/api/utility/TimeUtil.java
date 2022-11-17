package com.assignment.api.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
 
public class TimeUtil {

    public TimeUtil() {

    }
    
    public  String getTimeInMillis() {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        return timeInMillis+"";
    }
    
    public  String getRandom() {
        Random random = new Random();
        int nextInt = random.nextInt(9000000);
        nextInt=nextInt+1000000;
        String str=nextInt+"";
        return str;
    }
    public  String getTimeYearMonthDay() {
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        int hh=c.get(Calendar.HOUR_OF_DAY);
        int mi=c.get(Calendar.MINUTE);
        int ss=c.get(Calendar.SECOND);
        System.out.println(year+" "+month+" "+day+" "+hh+" "+mi+" "+ss);
        return null;
    }
   
    public  String getTimeYearMonthDay(String dateFormat) {
        String[] strNow = new SimpleDateFormat(dateFormat).format(new Date()).toString().split("-");
        String str="";
        for (String string : strNow) {
            str=str+string;
        }
        return str;
    }
    
    public String getMillPrimaryKey() {
        return getTimeInMillis()+getRandom();
    }
 
    public  String getDatePrimaryKey() {
        return getTimeYearMonthDay("yyyyMMddHHmmss")+getRandom();
    }
 
    
    public static String getInvoiceDate(Date date) {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    	return formatter.format(date);
    }
    
    public static String getFormatedDate(Date date) {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	return formatter.format(date);
    }
}