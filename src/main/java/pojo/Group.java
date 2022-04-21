package pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    int ugnum;
    int gnum;
    String gname;
    Date gdate;
    List<User> GroupNumber;


    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }

    public List<User> getGroupNumber() {
        return GroupNumber;
    }

    public void setGroupNumber(List<User> groupNumber) {
        GroupNumber = groupNumber;
    }

    public int getUgnum() {
        return ugnum;
    }

    public void setUgnum(int ugnum) {
        this.ugnum = ugnum;
    }

    public int getGnum() {
        return gnum;
    }

    public void setGnum(int gnum) {
        this.gnum = gnum;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    @Override
    public String toString() {
        return "Group{" +
                "ugnum=" + ugnum +
                ", gnum=" + gnum +
                ", gname='" + gname + '\'' +
                '}';
    }
}
