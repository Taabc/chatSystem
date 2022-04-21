package pojo;

import java.io.Serializable;

public class Friend implements Serializable {
    private static final long serialVersionUID = 1L;
    int faccount;
    String name;
    int sort;
    int lstatus;
    String lip;
    int lport;

    public int getFaccount() {
        return faccount;
    }

    public void setFaccount(int faccount) {
        this.faccount = faccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getLstatus() {
        return lstatus;
    }

    public void setLstatus(int lstatus) {
        this.lstatus = lstatus;
    }

    public String getLip() {
        return lip;
    }

    public void setLip(String lip) {
        this.lip = lip;
    }

    public int getLport() {
        return lport;
    }

    public void setLport(int lport) {
        this.lport = lport;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "faccount=" + faccount +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", lstatus=" + lstatus +
                ", lip=" + lip +
                ", lport=" + lport +
                '}';
    }
}
