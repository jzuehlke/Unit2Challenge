package com.jakezuehlke;

/**
 * @author Matt Green
 * @author Jake Zuehlke
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * FileInput: <br>
 *     This class buffer reads a file into an object and throws appropriate exceptions
 */
public class FileInput
{
    private BufferedReader in = null;
    private String fileName;

    public FileInput(String fileName)
    {
        this.fileName = fileName;
        try
        {
            in = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Open Error: " + fileName + " " + e);
        }
    }

    /**
     * fileReadLine: <br>
     *     This class simply reads the next line of the FileInput object.  Throws exception if it cannot.
     * @return String of file's line contents
     */
    public String fileReadLine()
    {
        try
        {
            String line = in.readLine();
            return line;
        }
        catch (Exception e)
        {
            System.out.println("File Read Error: " + fileName + " " + e);
            return null;
        }
    }

    /**
     * fileClose: <br>
     *     This method closes the FileInput object, or throws an exception with stack trace if it cannot.
     */
    public void fileClose()
    {
        if (in != null)
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
