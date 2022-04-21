package service.impl;

import dao.UserDao;


import org.apache.ibatis.session.SqlSession;
import pojo.*;
import service.UserService;
import utils.SqlSessionUtils;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private SqlSession sqlSession = SqlSessionUtils.getSession();

    private UserDao userDao = sqlSession.getMapper(UserDao.class);

    @Override
    public User login(Integer account ,String password) {
        if (account != null && password != null){
            User user = userDao.login(account);
            if (user != null){
                if (user.getPassword().equals(password)){
                    return user;
                }else {
                    return null;
                }
            }else{
                return null;
            }
        }
        return null;
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public List<Friend> getAllFriendByAccount(Integer account) {
        return  userDao.getAllFriendByAccount(account);
    }


    @Override
    public List<SubGroup> getSubGroupByAccount(int account) {
        return userDao.getSubGroupByAc(account);
    }

    @Override
    public List<Group> getGroupByAccount(int account) {
        return userDao.getGroupByAc(account);
    }

    @Override
    public Boolean updateIpPort(Integer account, String lip, Integer lport) {

        System.out.println(account+lip+lport);

        int row = userDao.updateIpPort(account,lip ,lport);
        if (row == 1){
            sqlSession.commit();
            //sqlSession.close();
            return true;
        }
        sqlSession.commit();
        //sqlSession.close();
        return false;
    }

    @Override
    public Boolean loginOut(Integer account) {
        int row = userDao.updateLoginStatus(account);
        if (row == 1){
            sqlSession.commit();
            //sqlSession.close();
            return true;
        }
        sqlSession.commit();
        //sqlSession.close();
        return false;
    }

    @Override
    public User getIpPortByAccount(Integer account) {
        return userDao.getIpPortByAccount(account);
    }

    @Override
    public Boolean insertGroupFileInfo(Integer gnum, String file_load, String filename) {
        int row = userDao.insertGroupFileInfo(gnum, file_load, filename);
        if (row == 1){
            sqlSession.commit();
            return true;
        }
        sqlSession.commit();
        return false;
    }

    @Override
    public List<UserFile> getGroupFileList(Integer gnum) {
        return userDao.getGroupFileList(gnum);
    }

    @Override
    public List<User> getGroupNumber(Integer gnum) {
        return userDao.getGroupNumber(gnum);
    }

    @Override
    public Boolean insertFriendRelation(Integer userAccount, Integer friendAccount, Integer sort) {
        int row =  userDao.insertFriendRelation(userAccount,friendAccount,sort);
        if (row == 1){
            sqlSession.commit();
            return true;
        }
        sqlSession.commit();
        return false;
    }

    @Override
    public Boolean deleteFriendByAccount(Integer uaccount, Integer friendAccount) {
        int row = userDao.deleteFriend(uaccount,friendAccount);
        if (row == 1){
            sqlSession.commit();
            return true;
        }
        sqlSession.commit();
        return false;
    }

    @Override
    public List<Integer> getFriendAccountByAccount(Integer account) {
        return userDao.getFriendAccountByAccount(account);
    }

    @Override
    public Integer addGroup(String gName) {
        Group group = new Group();
        group.setGname(gName);
        group.setGdate(new Date());


        if (userDao.addGroup(group) == 1){
            int gid = group.getGnum();
            System.out.println("gid="+gid);
            sqlSession.commit();
            return gid;
        }
        sqlSession.commit();
        return 0;
    }

    @Override
    public Boolean addGroupNumber(Integer userAccount, Integer ugnum, Integer gnum) {
        System.out.println(userAccount+","+ugnum+","+gnum);
        int row = userDao.addGroupNumber(userAccount,ugnum,gnum);
        if (row == 1){
            sqlSession.commit();
            return true;
        }
        sqlSession.commit();
        return false;
    }

    @Override
    public Group getGroupById(Integer gid) {
        return userDao.getGroupById(gid);
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        //List<User> userList = userService.findAllUser();
        //System.out.println(userList);
        boolean status = userService.loginOut(18145111);
        System.out.println(status);
    }
}

