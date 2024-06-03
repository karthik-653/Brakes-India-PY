package com.brakesindia.testgroundapp1.networking.Response;

import java.util.List;

public class CellNumberResponse {
    public List<Integer> getCellNumbers() {
        return cellNumbers;
    }

    public void setCellNumbers(List<Integer> cellNumbers) {
        this.cellNumbers = cellNumbers;
    }

    private List<Integer> cellNumbers;

}
