package pojo;

import javax.swing.text.StyledDocument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class  ClientMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    String operate;
    String message;
    StyledDocument messageSDoc;
    User user;
    int friendAccount;
    Group group;
    UserFile userFile;
    List<Friend> friendList;
    List<SubGroup> subGroupList;
    List<Group> groupList;
    List<UserFile> userFileList = null;
    List<Picture> pictureList = null;

    public UserFile getUserFile() {
        return userFile;
    }

    public void setUserFile(UserFile userFile) {
        this.userFile = userFile;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<UserFile> getUserFileList() {
        return userFileList;
    }

    public void setUserFileList(List<UserFile> userFileList) {
        this.userFileList = userFileList;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    public List<SubGroup> getSubGroupList() {
        return subGroupList;
    }

    public void setSubGroupList(List<SubGroup> subGroupList) {
        this.subGroupList = subGroupList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public int getFriendAccount() {
        return friendAccount;
    }

    public void setFriendAccount(int friendAccount) {
        this.friendAccount = friendAccount;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
//        if (this.pictureList==null){
//            System.out.println("Yes");
//            pictureList = new ArrayList<Picture>();
//        }
//        this.pictureList.addAll(pictureList);
        this.pictureList = pictureList;
    }

    public StyledDocument getMessageSDoc() {
        return messageSDoc;
    }

    public void setMessageSDoc(StyledDocument messageSDoc) {
        this.messageSDoc = messageSDoc;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "operate='" + operate + '\'' +
                ", message='" + message + '\'' +
                ", messageSDoc=" + messageSDoc +
                ", user=" + user +
                ", friendAccount=" + friendAccount +
                ", friendList=" + friendList +
                ", subGroupList=" + subGroupList +
                ", group=" + group +
                ", groupList=" + groupList +
                ", pictureList=" + pictureList +
                ", userFileList=" + userFileList +

                '}';
    }
}
