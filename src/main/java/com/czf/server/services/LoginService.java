package com.czf.server.services;

import com.czf.server.entities.User;
import com.czf.server.entities.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserDAO userDAO;

    public int checkPassword(String name,String password){
        if(!userDAO.existsByUserName(name))
            return 0;//0表示用户不存在
        else{
            if(userDAO.countAllByUserNameAndPassword(name, password)==0)
                return -1;//-1表示密码不对
            else
                return 1;//1表示密码正确
        }
    }

    public boolean modifyPassword(String name,String password){
        try{
            User user=userDAO.findByUserName(name);
            user.setPassword(password);
            userDAO.save(user);
            return true;
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkExist(String name){
        return userDAO.existsByUserName(name);
    }
}
