package service;

import org.apache.ibatis.annotations.Param;
import pojo.*;

import java.util.List;

public interface UserService {
    //查找所有用户
    List<User> findAllUser();
    //通过账号找到所有用户的好友
    List<Friend> getAllFriendByAccount(Integer account);
    //通过账号得到用户好友的账号列表
    List<Integer> getFriendAccountByAccount(Integer account);
    //通过账号删除好友
    Boolean deleteFriendByAccount(Integer uaccount,Integer friendAccount);
    //通过账号密码返回User
    User login(Integer account,String password);
    //通过账号得到账号的自定义组
    List<SubGroup> getSubGroupByAccount(int account);
    //通过账号获取该账号加入的群
    List<Group> getGroupByAccount(int account);
    //通过账号更新登录的ip和端口
    Boolean updateIpPort(Integer account, String lip,Integer lport);
    //通过账号获取IP和端口
    User getIpPortByAccount(Integer account);
    //通过账号更新登录信息，退出登录
    Boolean loginOut(Integer account);
    //将上传的群文件信息插入数据库
    Boolean insertGroupFileInfo(Integer gnum,String file_load,String filename);
    //通过群的编号获取该群所有群文件
    List<UserFile> getGroupFileList(Integer gnum);
    //通过群的编号获得群成员
    List<User> getGroupNumber (Integer gnum);
    //插入新的好友关系
    Boolean insertFriendRelation(Integer userAccount,Integer friendAccount,Integer sort);
    //创建新群
    Integer addGroup(String gName);
    //添加新群成员
    Boolean addGroupNumber(Integer userAccount, Integer ugnum, Integer gnum);
    //通过群id获取群成员
    Group getGroupById(Integer gid);

}
