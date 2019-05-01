package com.rppowell.fillo.annotations.TestWorkbook001;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.rppowell.fillo.annotations.FilloAnnotationsFactory;
import com.rppowell.fillo.annotations.FilloColumn;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilloAnnotationsTestWorkbook001Example {
    @FilloColumn
    public String TestSheetOneColumnOne;

    @FilloColumn
    public String TestSheetOneColumnTwo;

    @FilloColumn
    public String TestSheetOneColumnThree;

    @FilloColumn (columnName="TestSheetOneColumnFour BooleanDropdownValues")
    public String TestSheetOneColumnFour;

    public String toString() {
        return "FilloAnnotationsTestWorkbook001Example(" +
                "TestSheetOneColumnOne='"+ TestSheetOneColumnOne + "', " +
                "TestSheetOneColumnTwo='"+ TestSheetOneColumnTwo + "', " +
                "TestSheetOneColumnThree='"+ TestSheetOneColumnThree + "', " +
                "TestSheetOneColumnFour='"+ TestSheetOneColumnFour + "')";
    }

    @Test
    public void testFilloTestSheetOne() throws FilloException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<String> expectedValues = Arrays.asList(
                "FilloAnnotationsTestWorkbook001Example(TestSheetOneColumnOne='valueone', TestSheetOneColumnTwo='foo', TestSheetOneColumnThree='1', TestSheetOneColumnFour='true')",
                "FilloAnnotationsTestWorkbook001Example(TestSheetOneColumnOne='valuetwo', TestSheetOneColumnTwo='bar', TestSheetOneColumnThree='2', TestSheetOneColumnFour='false')"
        );
        List<String> actualValues = new ArrayList<>();
        String xlsxFilename = "src/test/resources/TestWorkbook001.xlsx";
        System.out.println("xlsx=" + xlsxFilename);

        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From TestSheetOne");
        FilloAnnotationsTestWorkbook001Example annotatedFilloTestSheetOne;
        while(recordset.next()) {
            annotatedFilloTestSheetOne = FilloAnnotationsFactory.extractFirstClassFromRecordset(recordset, FilloAnnotationsTestWorkbook001Example.class);
            System.out.println(annotatedFilloTestSheetOne.toString());
            actualValues.add(annotatedFilloTestSheetOne.toString());
        }
        recordset.close();
        connection.close();
        Assert.assertEquals(actualValues, expectedValues, "extracted entries match");
    }

    @Test
    public void testFilloTestSheetTwo() throws FilloException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<String> expectedValues = Arrays.asList(
                "FilloAnnotationsTestWorkbook001Example(TestSheetOneColumnOne='valueone', TestSheetOneColumnTwo='foo', TestSheetOneColumnThree='1', TestSheetOneColumnFour='true')",
                "FilloAnnotationsTestWorkbook001Example(TestSheetOneColumnOne='valuetwo', TestSheetOneColumnTwo='bar', TestSheetOneColumnThree='2', TestSheetOneColumnFour='false')"
        );
        List<String> actualValues = new ArrayList<>();
        String xlsxFilename = "src/test/resources/TestWorkbook001.xlsx";
        System.out.println("xlsx=" + xlsxFilename);

        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From TestSheetOne");
        FilloAnnotationsTestWorkbook001Example annotatedFilloTestSheetOne;

        List<FilloAnnotationsTestWorkbook001Example> entries = FilloAnnotationsFactory.extractClassesFromRecordset(recordset, FilloAnnotationsTestWorkbook001Example.class);

        for (int i = 0; i < entries.size(); i++) {
            System.out.println(entries.get(i).toString());
            actualValues.add(entries.get(i).toString());
        }
        recordset.close();
        connection.close();
        Assert.assertEquals(actualValues, expectedValues, "extracted entries match");
    }
}
