package com.brakesindia.testgroundapp1.networking.Response;

import java.util.List;

public class CellTypeResponse {
    public List<String> getCellTypes() {
        return cellTypes;
    }

    public void setCellTypes(List<String> cellTypes) {
        this.cellTypes = cellTypes;
    }

    private List<String> cellTypes;

}
