package com.brakesindia.testgroundapp1.networking.Response;

public class PydataResponse {
    public int pyorder;
    public String pyno;
    public String pydesc, pypurpose, pytype;

    public int getPyorder() {
        return pyorder;
    }

    public void setPyorder(int pyorder) {
        this.pyorder = pyorder;
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
}
