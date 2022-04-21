package pojo;

import java.io.Serializable;
import java.net.Socket;

public class SocketAC implements Serializable {
    private static final long serialVersionUID = 1L;
    Socket socket;
    int account;

    public SocketAC(){

    }

    public SocketAC(Socket socket, int account){
        this.socket = socket;
        this.account = account;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "SocketAC{" +
                "socket=" + socket +
                ", account=" + account +
                '}';
    }
}
