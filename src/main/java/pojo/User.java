package pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int account;
    String password;
    int sign;
    int del;
    String lip;
    int lport;
    int lstatus;
    Date ldate;
    int ugnum;//在Group类下的List<User>中使用，表示其和该Group关系

    public int getUgnum() {
        return ugnum;
    }

    public void setUgnum(int ugnum) {
        this.ugnum = ugnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
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

    public int getLstatus() {
        return lstatus;
    }

    public void setLstatus(int lstatus) {
        this.lstatus = lstatus;
    }

    public Date getLdate() {
        return ldate;
    }

    public void setLdate(Date ldate) {
        this.ldate = ldate;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", sign=" + sign +
                ", del=" + del +
                ", lip='" + lip + '\'' +
                ", lport=" + lport +
                ", lstatus=" + lstatus +
                ", ldate=" + ldate +
                ", ugnum=" + ugnum +
                '}';
    }
}
