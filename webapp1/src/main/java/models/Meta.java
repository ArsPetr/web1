package models;

import java.util.Date;

public class Meta {
    private int visitNumber;
    private Date curentDate;

    public Meta() {
    }

    @Override
    public String toString() {
        return "Meta{" +
                "visitNumber=" + visitNumber +
                ", curentDate=" + curentDate +
                '}';
    }

    public Meta(int visitNumber, Date curentDate) {
        this.visitNumber = visitNumber;
        this.curentDate = curentDate;
    }

    public int getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(int visitNumber) {
        this.visitNumber = visitNumber;
    }

    public Date getCurentDate() {
        return curentDate;
    }

    public void setCurentDate(Date curentDate) {
        this.curentDate = curentDate;
    }
}
