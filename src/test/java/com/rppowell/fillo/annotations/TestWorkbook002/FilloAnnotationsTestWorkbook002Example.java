package com.rppowell.fillo.annotations.TestWorkbook002;

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

public class FilloAnnotationsTestWorkbook002Example {

    @Test
    public void testFilloTestSheetTwoVerticalData() throws FilloException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<String> expectedValues = Arrays.asList("ValueOne", "ValueTwo", "ValueThree", "ValueFour", "ValueFive", "ValueSix", "ValueSeven", "ValueEight");
        List<String> actualValues = new ArrayList<>();
        String xlsxFilename = "src/test/resources/TestWorkbook002.xlsx";
        System.out.println("xlsx=" + xlsxFilename);

        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From VerticalData");
        List<VerticalDataEntry> entries = FilloAnnotationsFactory.extractClassesFromRecordset(recordset, VerticalDataEntry.class);

        for (int i = 0; i < entries.size(); i++) {
            actualValues.add(entries.get(i).toString());
        }
        recordset.close();
        connection.close();
        System.out.println(actualValues);
        Assert.assertEquals(actualValues, expectedValues, "TestSheetTwo VerticalData matches");
    }

    @Test
    public void testFilloTestSheetTwoHorizontalData() throws FilloException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<String> expectedValues = Arrays.asList(
                "[ValueOneOne, ValueOneTwo, ValueOneThree, ValueOneFour, ValueOneFive, ValueOneSix]",
                "[ValueTwoOne, ValueTwoTwo, ValueTwoThree, ValueTwoFour, ValueTwoFive, ValueTwoSix]"
        );
        String xlsxFilename = "src/test/resources/TestWorkbook002.xlsx";
        System.out.println("xlsx=" + xlsxFilename);

        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From HorizontalData");
        List<HorizontalDataEntry> entries;

        entries = FilloAnnotationsFactory.extractClassesFromRecordset(recordset, HorizontalDataEntry.class);

        List<String> actualValues = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            actualValues.add(entries.get(i).toString());
        }
        recordset.close();
        connection.close();
        System.out.println(actualValues);
        Assert.assertEquals(actualValues, expectedValues, "TestSheetTwo HorizontalData matches");
    }
}
