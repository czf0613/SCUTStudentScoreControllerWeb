package com.czf.server.controllers;

import com.czf.server.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/{type}/login",method = RequestMethod.POST)
    public ResponseEntity<String> studentLogin(@PathVariable("type")String type, @RequestParam(value = "userName")String userName, @RequestParam("password")String password){
        boolean typeMatcher;
        switch (type) {
            case "student":
                typeMatcher = loginService.checkType(userName, 1);
                break;
            case "teacher":
                typeMatcher = loginService.checkType(userName, 2);
                break;
            case "administer":
                typeMatcher = loginService.checkType(userName, 3);
                break;
            default:
                typeMatcher=false;
        }
        if(!typeMatcher)
            return new ResponseEntity<>("非法用户",HttpStatus.BAD_REQUEST);

        int result=loginService.checkPassword(userName, password);
        if(result==0)
            return new ResponseEntity<>("用户名不存在",HttpStatus.NOT_FOUND);
        else if(result==-1)
            return new ResponseEntity<>("密码错误",HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<>("登陆成功",HttpStatus.OK);
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestParam(value = "userName")String userName,@RequestParam("password")String password,@RequestParam("newPassword")String newPassword){
        if(loginService.checkPassword(userName,password)!=1)
            return new ResponseEntity<>("原密码错误",HttpStatus.BAD_REQUEST);
        else{
            if(loginService.modifyPassword(userName,newPassword))
                return new ResponseEntity<>("修改成功",HttpStatus.OK);
            else
                return new ResponseEntity<>("修改失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
