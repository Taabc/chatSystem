package pojo;

import java.io.Serializable;
import java.util.Arrays;

public class Picture implements Serializable {
    private static final long serialVersionUID = 1L;
    private String p_num;
    private String p_name;
    private long p_size;
    private byte[] p_date;
    private String type;
    private String OldLoad;

    public String getOldLoad() {
        return OldLoad;
    }

    public void setOldLoad(String oldLoad) {
        OldLoad = oldLoad;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getP_num() {
        return p_num;
    }

    public void setP_num(String p_num) {
        this.p_num = p_num;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public long getP_size() {
        return p_size;
    }

    public void setP_size(long p_size) {
        this.p_size = p_size;
    }

    public byte[] getP_date() {
        return p_date;
    }

    public void setP_date(byte[] p_date) {
        this.p_date = new byte[p_date.length];
        for (int i=0;i<p_date.length;i++){
            this.p_date[i] = p_date[i];
        }
    }

    @Override
    public String toString() {
        return "Picture{" +
                "p_num='" + p_num + '\'' +
                ", p_name='" + p_name + '\'' +
                ", p_size=" + p_size  +
                '}';
    }
}
