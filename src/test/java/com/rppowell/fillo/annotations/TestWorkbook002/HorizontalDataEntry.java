package com.rppowell.fillo.annotations.TestWorkbook002;

import com.rppowell.fillo.annotations.FilloColumn;

import java.util.Arrays;

public class HorizontalDataEntry {
    @FilloColumn public String ColumnOne;
    @FilloColumn public String ColumnTwo;
    @FilloColumn public String ColumnThree;
    @FilloColumn public String ColumnFour;
    @FilloColumn public String ColumnFive;
    @FilloColumn public String ColumnSix;

    public HorizontalDataEntry(){ }
    public String toString() {
        return Arrays.asList(ColumnOne, ColumnTwo, ColumnThree, ColumnFour, ColumnFive,ColumnSix).toString();
    }
}

