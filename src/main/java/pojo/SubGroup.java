package pojo;

import java.io.Serializable;

public class SubGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    int sid;
    String sname;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "subgroup{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                '}';
    }
}
