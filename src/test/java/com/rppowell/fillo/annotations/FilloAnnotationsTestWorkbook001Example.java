package com.rppowell.fillo.annotations;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

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
        String xlsxFilename = "src/test/resources/TestWorkbook001.xlsx";
        System.out.println("xlsx=" + xlsxFilename);

        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From TestSheetOne");
        while(recordset.next()) {
            FilloAnnotationsTestWorkbook001Example annotatedFilloTestSheetOne = (FilloAnnotationsTestWorkbook001Example) FilloAnnotationsFactory.extract(recordset, FilloAnnotationsTestWorkbook001Example.class);
            System.out.println(annotatedFilloTestSheetOne.toString());
        }
        recordset.close();
        connection.close();
    }

    @Test
    public void testFilloTestSheetTwo() throws FilloException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String xlsxFilename = "src/test/resources/TestWorkbook001.xlsx";
        System.out.println("xlsx=" + xlsxFilename);

        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From TestSheetOne");
        while(recordset.next()) {
            FilloAnnotationsTestWorkbook001Example annotatedFilloTestSheetOne = FilloAnnotationsFactory.extractFirstClassFromRecordset(recordset, FilloAnnotationsTestWorkbook001Example.class);
            System.out.println(annotatedFilloTestSheetOne.toString());
        }
        recordset.close();
        connection.close();
    }
}
