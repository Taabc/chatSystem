package dao;

import org.apache.ibatis.annotations.Param;
import pojo.*;

import java.util.Date;
import java.util.List;

public interface UserDao {

    User login(Integer account);
    List<User> findAllUser();
    List<Integer> getFriendAccountByAccount(Integer account);
    List<Friend> getAllFriendByAccount(Integer account);
    List<SubGroup>getSubGroupByAc(Integer account);
    List<Group> getGroupByAc(Integer account);
    Integer updateIpPort(@Param("account") Integer account,@Param("lip") String lip, @Param("lport") Integer lport);
    Integer updateLoginStatus(Integer account);
    User getIpPortByAccount(Integer account);
    String getPasswordByAccount(Integer account);
    Integer insertGroupFileInfo(@Param("gnum") Integer gnum,@Param("load_road") String load_road,@Param("filename") String filename);
    List<UserFile> getGroupFileList(Integer gnum);
    List<User> getGroupNumber (Integer gnum);
    Integer insertFriendRelation(@Param("userAccount") Integer userAccount,@Param("friendAccount") Integer friendAccount,@Param("sort") Integer sort);
    Integer deleteFriend(@Param("userAccount")Integer userAccount,@Param("friendAccount") Integer friendAccount);
    // Integer addGroup(@Param("gName")String gName, @Param("date")Date date,@Param("gid")Integer gid);
    Integer addGroup(Group group);
    Integer addGroupNumber(@Param("userAccount")Integer userAccount,@Param("ugnum")Integer ugnum,@Param("gnum")Integer gnum);
    Group getGroupById(Integer gid);


}
