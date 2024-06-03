package com.brakesindia.testgroundapp1.networking.Requests;

public class NewDataRequest {
//    "plant": "string",
//            "unit": 0,
//            "celltype": "string",
//            "cellno": 0,
//            "model": "string",
//            "docno": "string",
//            "process": "string",
//            "pyorder": 0,
//            "pyno": "string",
//            "pydesc": "string",
//            "pypurpose": "string",
//            "pytype": "string"

    String plant, celltype, model, docno, process, pyno, pydesc, pypurpose, pytype;
    int unit, cellno, pyorder;

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getCelltype() {
        return celltype;
    }

    public void setCelltype(String celltype) {
        this.celltype = celltype;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getPyno() {
        return pyno;
    }

    public void setPyno(String pyno) {
        this.pyno = pyno;
    }

    public String getPydesc() {
        return pydesc;
    }

    public void setPydesc(String pydesc) {
        this.pydesc = pydesc;
    }

    public String getPypurpose() {
        return pypurpose;
    }

    public void setPypurpose(String pypurpose) {
        this.pypurpose = pypurpose;
    }

    public String getPytype() {
        return pytype;
    }

    public void setPytype(String pytype) {
        this.pytype = pytype;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getCellno() {
        return cellno;
    }

    public void setCellno(int cellno) {
        this.cellno = cellno;
    }

    public int getPyorder() {
        return pyorder;
    }

    public void setPyorder(int pyorder) {
        this.pyorder = pyorder;
    }
}
