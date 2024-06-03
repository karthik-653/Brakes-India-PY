package com.brakesindia.testgroundapp1.networking.Requests;

public class SubmitRequest {
    String plant;
    String celltype;
    String model;
    String process;
    String pydesc;
    String pyno;
    String pypurpose;
    String pytype;

    String docno;
    String check;
    String updatename;
    int unit,cellno,pyorder;

    String date;

    public String getDocno() { return docno; }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    @Override
    public String toString() {
        return "SubmitRequest{" +
                "plant='" + plant + '\'' +
                ", celltype='" + celltype + '\'' +
                ", model='" + model + '\'' +
                ", process='" + process + '\'' +
                ", pydesc='" + pydesc + '\'' +
                ", pyno='" + pyno + '\'' +
                ", pypurpose='" + pypurpose + '\'' +
                ", pytype='" + pytype + '\'' +
                ", date='" + date + '\'' +
                ", docno='" + docno + '\'' +
                ", check='" + check + '\'' +
                ", updatename='" + updatename + '\'' +
                ", unit=" + unit +
                ", cellno=" + cellno +
                ", pyorder=" + pyorder +
                '}';
    }
    public String getPyno() {
        return pyno;
    }

    public void setPyno(String pyno) {
        this.pyno = pyno;
    }

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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
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


    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getUpdatename() {
        return updatename;
    }

    public void setUpdatename(String updatename) {
        this.updatename = updatename;
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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
