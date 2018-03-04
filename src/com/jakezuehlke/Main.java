package com.jakezuehlke;

/**
 * @author Jake Zuehlke
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Main: <br>
 *     This program reads in csv date values from date_diff.csv,
 *     then converts them to type Date from String.<br>
 *     Once as Dates, calculations are made to discover the time between dates in
 *     years, months, days, and total days.<br>After calculations,
 *     each date's month value is considered and tallied into a treeMap collection
 *     in order to output how many dates occurred in each month for the total file.
 */

public class Main
{
    private final static FileInput date_diff = new FileInput("date_diff.csv");

    public static void main(String[] args)
    {
        String dateLine;  //entire single line from file
        String[] dateLineFields;  //split elements from dateLine
        Date date1 = null;  //element 0 of dateLineFields, converted to date
        Date date2 = null;  //element 1 of dateLineFields, converted to date
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yy");  //1 of 2 possible formats in file
        SimpleDateFormat df2 = new SimpleDateFormat("MMM. dd, yyyy");  //2 of 2 possible formats in file
        long diff;  //milliseconds between date 1 and 2
        long totalDays;  //total days between date 1 and 2
        long years;  //total years between date 1 and 2
        long months;  //total months between date 1 and 2, using remainder from years
        long days;  //total days between date 1 and 2, using remainder from months
        String month1;  //month value from date1
        String month2;  //month value from date2
        TreeMap<String, Integer> tMap = new TreeMap<String, Integer>();  //K: Month name, V: Occurrences

        //formatted output header
        System.out.format("%10s %10s %10s %10s\n", "Total Days", "Years", "Months", "Days");
        System.out.format("%10s %10s %10s %10s\n", "==========", "=====", "======", "====");

        //read through file line by line
        while ((dateLine = date_diff.fileReadLine()) != null)
        {
            //split line into 2 dates, store in string array
            if(dateLine.startsWith("\""))
            {
                dateLineFields = dateLine.split("\",", 2);
            }
            else
            {
                dateLineFields = dateLine.split(",", 2);
            }

            //after split, remove quotes and any other trailing characters for smooth conversion to date
            dateLineFields[0] = dateLineFields[0].replace("\"", "").trim();
            dateLineFields[1] = dateLineFields[1].replace("\"", "").trim();

            try
            {
                //convert 0th element from string array to a date
                if (dateLineFields[0].contains("-"))
                {
                    date1 = df1.parse(dateLineFields[0]);
                }
                else
                {
                    date1 = df2.parse(dateLineFields[0]);
                }

                //convert 1st element from string array to a date
                if (dateLineFields[1].contains("-"))
                {
                    date2 = df1.parse(dateLineFields[1]);
                }
                else
                {
                    date2 = df2.parse(dateLineFields[1]);
                }
            }
            catch(ParseException e)
            {
                System.out.println("Exception: " + e);
            }

            //find difference between both dates per line in days
            //convert total days to years - months - days format
            diff = Math.abs(date1.getTime() - date2.getTime());
            totalDays = diff / (1000 * 60 * 60 * 24);
            years = totalDays / 365;
            months = (totalDays % 365) / (365 / 12);
            days = (totalDays % 365) % (365 / 12);

            //output formatted results
            System.out.format("%-10d %10d %10d %10d\n", totalDays, years, months, days);

            //store month tally into collection
            //get month from dates
            month1 = new SimpleDateFormat("MMM").format(date1);
            month2 = new SimpleDateFormat("MMM").format(date2);

            //if month 1 is not in the collection, add it as a key and give begin its value at 1
            if(tMap.get(month1) == null)
            {
                tMap.put(month1, new Integer(1));
            }
            else
            {
                //if already in the collection, increment the value of that key month by 1
                tMap.put(month1, tMap.get(month1) + 1);
            }

            //if month 2 is not in the collection, add it as a key and give begin its value at 1
            if(tMap.get(month2) == null)
            {
                tMap.put(month2, new Integer(1));
            }
            else
            {
                //if already in the collection, increment the value of that key month by 1
                tMap.put(month2, tMap.get(month2) + 1);
            }
        }
        date_diff.fileClose();

        //iterate total number of date occurrences by month using collection method
        //get a set of the entries, and the iterator
        Set tMapSet = tMap.entrySet();
        Iterator tMapIterator = tMapSet.iterator();

        //output header
        System.out.println("\nTotal date occurrences by month:");
        System.out.format("%5s %11s\n", "Month", "Occurrences");
        System.out.format("%5s %11s\n", "=====", "===========");

        //display treeMap with formatted output
        while(tMapIterator.hasNext())
        {
            Map.Entry tMapEntry = (Map.Entry) tMapIterator.next();
            System.out.format("%-5s %6d\n",tMapEntry.getKey(), tMapEntry.getValue());
        }
    }
}