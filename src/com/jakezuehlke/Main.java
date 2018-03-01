package com.jakezuehlke;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main
{

    private final static FileInput date_diff = new FileInput("date_diff.csv");

    public static void main(String[] args)
    {
        String dateLine;
        String[] dateLineFields;
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
        TreeMap<String, Integer> tMap = new TreeMap<String, Integer>();

        //formatted output header
        System.out.format("%10s %10s %10s %10s\n", "Total Days", "Years", "Months", "Days");
        System.out.format("%10s %10s %10s %10s\n", "==========", "=====", "======", "====");

        //read through file line by line
        while ((dateLine = date_diff.fileReadLine()) != null)
        {
            //get rid of periods from line, trim whitespace, carriage returns, etc.
            if(dateLine.contains("."))
            {
                dateLine = dateLine.replace(".", "").trim();
            }

            //split line into 2 dates, store in string array
            if(dateLine.startsWith("\""))
            {
                dateLineFields = dateLine.split("\",", 2);
            }
            else
            {
                dateLineFields = dateLine.split(",", 2);
            }

            //after split, remove quotes for smooth conversion to date
            dateLineFields[0] = dateLineFields[0].replace("\"", "");
            dateLineFields[1] = dateLineFields[1].replace("\"", "");

            //convert 0th element from string array to a date
            if(dateLineFields[0].contains("-"))
            {
                try
                {
                    date1 = df1.parse(dateLineFields[0]);
                }
                catch(ParseException e)
                {
                    System.out.println("Exception: " + e);
                }
            }
            else
            {
                try
                {
                    date1 = df2.parse(dateLineFields[0]);
                }
                catch(ParseException e)
                {
                    System.out.println("Exception: " + e);
                }
            }

            //convert 1st element from string array to a date
            if(dateLineFields[1].contains("-"))
            {
                try
                {
                    date2 = df1.parse(dateLineFields[1]);
                }
                catch(ParseException e)
                {
                    System.out.println("Exception: " + e);
                }
            }
            else
            {
                try
                {
                    date2 = df2.parse(dateLineFields[1]);
                }
                catch(ParseException e)
                {
                    System.out.println("Exception: " + e);
                }
            }

            //find difference between both dates per line in days
            //convert total days to years - months - days format
            long diff = Math.abs(date1.getTime() - date2.getTime());
            long totalDays = diff / (1000 * 60 * 60 * 24);
            long years = totalDays / 365;
            long months = (long) Math.floor((totalDays % 365) / 30.4167);
            long days = (long) Math.floor((totalDays % 365) % 30.4167);

            //output formatted results
            System.out.format("%-10d %10d %10d %10d\n", totalDays, years, months, days);


            //store month tally into collection
            //get month from dates
            String month1 = new SimpleDateFormat("MMM").format(date1);
            String month2 = new SimpleDateFormat("MMM").format(date2);

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
