package pojo;

import java.io.Serializable;

public class UserFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private String filename;
    private String file_load;
    private byte[] file_date;
    private int gnum;

    public String getFilename() {
        return filename;
    }

    public byte[] getFile_date() {
        return file_date;
    }

    public void setFile_date(byte[] file_date) {
        this.file_date = file_date;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile_load() {
        return file_load;
    }

    public void setFile_load(String file_load) {
        this.file_load = file_load;
    }

    public int getGnum() {
        return gnum;
    }

    public void setGnum(int gnum) {
        this.gnum = gnum;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "filename='" + filename + '\'' +
                ", file_load='" + file_load + '\'' +
                ", gnum=" + gnum +
                '}';
    }
}
