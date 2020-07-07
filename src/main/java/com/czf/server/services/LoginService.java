package com.czf.server.services;

import com.czf.server.entities.User;
import com.czf.server.entities.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public int checkPassword(String name,String password){
        if(!userDAO.existsByUserName(name))
            return 0;//0表示用户不存在
        else{
            String temp=userDAO.getPasswordWithUserName(name);
            if(bCryptPasswordEncoder.matches(password,temp))
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

    public boolean checkType(String name,int type){
        int result=userDAO.getTypeWithUserName(name);
        return result==type;
    }

    public int findIdWithUserName(String name){
        return userDAO.getAccountWithUserName(name);
    }
}
