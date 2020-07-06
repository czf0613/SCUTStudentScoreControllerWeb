package com.czf.server.controllers;

import com.alibaba.fastjson.JSON;
import com.czf.server.entities.Student;
import com.czf.server.services.LoginService;
import com.czf.server.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/student/register",method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestParam(value = "userName")String userName,@RequestParam("password")String password,@RequestParam("content") String jsonString){
        if(loginService.checkExist(userName))
            return new ResponseEntity<>("该用户已存在",HttpStatus.BAD_REQUEST);

        Student student= JSON.parseObject(jsonString,Student.class);
        student.setId(null);

        if(registerService.register(userName,password,student))
            return new ResponseEntity<>("注册成功",HttpStatus.OK);
        else
            return new ResponseEntity<>("注册失败",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
