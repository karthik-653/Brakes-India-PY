package com.brakesindia.testgroundapp1.networking.Response;

public class ApprovalResponse {
    public int pyorder;
    public String pyno, pydesc, pypurpose, pytype, updatename, date, check;

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

    public String getUpdatename() {
        return updatename;
    }

    public void setUpdatename(String updatename) {
        this.updatename = updatename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
