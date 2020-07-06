package com.czf.server.services;

import com.czf.server.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private AdministerDAO administerDAO;

    public synchronized boolean register(String userName, String password, Student student){
        try{
            int id=studentDAO.save(student).getId();

            User user=new User();
            user.setUserName(userName);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setFlag(1);
            user.setAccount(id);
            userDAO.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public synchronized boolean register(String userName, String password, Teacher teacher){
        try{
            int id=teacherDAO.save(teacher).getId();

            User user=new User();
            user.setUserName(userName);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setFlag(2);
            user.setAccount(id);
            userDAO.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public synchronized boolean register(String userName, String password, Administer administer){
        try{
            int id=administerDAO.save(administer).getId();

            User user=new User();
            user.setUserName(userName);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setFlag(3);
            user.setAccount(id);
            userDAO.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
