package com.jakezuehlke.test;

/**
 * @author Jake Zuehlke
 */

import com.jakezuehlke.FileInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertNotNull;

/**
 * FileInputTest:<br>
 *     This is a test class for FileInput, it will determine if places.csv and stuff.csv are being read in
 */

public class FileInputTest
{
    //variable for testing
    FileInput datesTest;

    //before is for file input
    @Before
    public void setUp()
    {
        //test the constructor.  This test example was copied from the main
        datesTest = new FileInput("date_diff.csv");
    }

    //test the FileInput class' methods
    @Test
    public void testFile()
    {
        //see if fileReadLine returns null.  It shouldn't.
        assertNotNull("Reader should return data.", datesTest.fileReadLine());
    }

    //after is for file closing
    @After
    public void tearDown()
    {
        datesTest.fileClose();
    }
}