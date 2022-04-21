package pojo;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class SocketStream implements Serializable {
    private static final long serialVersionUID = 1L;
    private Socket socket;
    private ObjectOutputStream obops;
    private int linkAccount;
    private int type;//0表示被动连接的socket,1表示主动连接的socket

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLinkAccount() {
        return linkAccount;
    }

    public void setLinkAccount(int linkAccount) {
        this.linkAccount = linkAccount;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getObops() {
        return obops;
    }

    public void setObops(ObjectOutputStream obops) {
        this.obops = obops;
    }

    @Override
    public String toString() {
        return "SocketStream{" +
                "socket=" + socket +
                ", obops=" + obops +
                '}';
    }
}
