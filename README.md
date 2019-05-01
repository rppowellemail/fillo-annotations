# Fillo Annotations

Java annotations to help extract data to class from spreadsheets using the fillo library.

## Simple Fillo Annotations Example

Given a spreadsheet with the following header-column layout:


| TestSheetOneColumnOne | TestSheetOneColumnTwo | TestSheetOneColumnThree | TestSheetOneColumnFour BooleanDropdownValues |
| --------------------- | --------------------- | ----------------------- |:--------------------------------------------:|
| valueone              | foo                   | 1                       | TRUE                                         |
| valuetwo              | bar                   | 2                       | FALSE                                        |
| ...                   | ...                   | ...                     | ...                                          |


The intention is to annotate Java class public instance variables and extract row entries.

The following example shows annotating the class and extracting the spreadsheet:

    public class FilloAnnotationsTestSheetOne {
        @FilloColumn
        public String TestSheetOneColumnOne;
    
        @FilloColumn
        public String TestSheetOneColumnTwo;
    
        @FilloColumn
        public String TestSheetOneColumnThree;
    
        @FilloColumn (columnName="TestSheetOneColumnFour BooleanDropdownValues")
        public String TestSheetOneColumnFour;
    
    }

Extracting list of annotated class from Fillo recordset:

    ...
        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From HorizontalData");
        
        List<HorizontalDataEntry> entries = FilloAnnotationsFactory.extractClassesFromRecordset(recordset, HorizontalDataEntry.class);
        
        recordset.close();
        connection.close();
    ...

Iterating over recordset:

    ...
        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From TestSheetOne");
        while(recordset.next()) {
            FilloAnnotationsTestSheetOne filloAnnotationsTestSheetOne = (FilloAnnotationsTestSheetOne) FilloAnnotationsfactory.extract(recordset, FilloAnnotationsTestSheetOne.class);
            System.out.println(annotatedFilloTestSheetOne.toString());
        }
        recordset.close();
        connection.close();
        
    ...

Values are set in the created class:

    ...
    FilloAnnotationsTestSheetOne(TestSheetOneColumnOne='valueone', TestSheetOneColumnTwo='foo', TestSheetOneColumnThree='1', TestSheetOneColumnFour='true')
    FilloAnnotationsTestSheetOne(TestSheetOneColumnOne='valuetwo', TestSheetOneColumnTwo='bar', TestSheetOneColumnThree='2', TestSheetOneColumnFour='false')
    ...


# Dependencies / References

 * Fillo ( https://codoid.com/fillo/ )
