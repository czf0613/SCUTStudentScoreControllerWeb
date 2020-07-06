package com.czf.server.controllers;

import com.alibaba.fastjson.JSON;
import com.czf.server.entities.Administer;
import com.czf.server.entities.Student;
import com.czf.server.entities.Teacher;
import com.czf.server.services.LoginService;
import com.czf.server.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/{type}/register",method = RequestMethod.POST)
    public ResponseEntity<String> register(@PathVariable("type")String type, @RequestParam(value = "userName")String userName, @RequestParam("password")String password, @RequestParam("content") String jsonString){
        int flag;
        switch (type){
            case "teacher":
                flag=2;
                break;
            case "administer":
                flag=3;
                break;
            default:
                flag=1;
        }
        if(loginService.checkExist(userName))
            return new ResponseEntity<>("该用户已存在",HttpStatus.BAD_REQUEST);

        boolean status=false;
        switch (flag){
            case 1:
                Student student= JSON.parseObject(jsonString,Student.class);
                student.setId(null);
                status=registerService.register(userName,password,student);
                break;
            case 2:
                Teacher teacher=JSON.parseObject(jsonString,Teacher.class);
                teacher.setId(null);
                status=registerService.register(userName,password,teacher);
                break;
            case 3:
                Administer administer=JSON.parseObject(jsonString,Administer.class);
                administer.setId(null);
                status=registerService.register(userName,password,administer);
                break;
        }

        if(status)
            return new ResponseEntity<>("注册成功",HttpStatus.OK);
        else
            return new ResponseEntity<>("注册失败",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
