package com.brakesindia.testgroundapp1.networking.Requests;

public class ProcessRequest {
    public String plant;
    public int unit;
    public String cellType;
    public int cellno;
    public String model;

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String celltype) {
        this.cellType = celltype;
    }

    public int getCellno() {
        return cellno;
    }

    public void setCellno(int cellno) {
        this.cellno = cellno;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
