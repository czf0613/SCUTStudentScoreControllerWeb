package com.czf.server.services;

import com.czf.server.entities.Student;
import com.czf.server.entities.StudentDAO;
import com.czf.server.entities.User;
import com.czf.server.entities.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private StudentDAO studentDAO;

    public synchronized boolean register(String userName, String password, Student student){
        try{
            int id=studentDAO.save(student).getId();

            User user=new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setFlag(1);
            user.setAccount(id);
            userDAO.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
