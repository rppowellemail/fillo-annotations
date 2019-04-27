package com.rppowell.fillo.annotations.TestWorkbook002;

import com.rppowell.fillo.annotations.FilloColumn;

public class VerticalDataEntry {
    @FilloColumn
    public String ColumnOne;
    public String toString() { return ColumnOne; }
    public VerticalDataEntry(){ }
}
