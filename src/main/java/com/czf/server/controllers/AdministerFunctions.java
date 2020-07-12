package com.czf.server.controllers;

import com.alibaba.fastjson.JSON;
import com.czf.server.beans.CourseScoreInDouble;
import com.czf.server.beans.Score;
import com.czf.server.entities.*;
import com.czf.server.services.AdministerService;
import com.czf.server.services.StudentService;
import com.czf.server.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/administer")
public class AdministerFunctions {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private AdministerService administerService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/getInfo/{type}/{id}",method = RequestMethod.GET)
    public ResponseEntity<String> getInfo(@PathVariable("type")String type,@PathVariable("id")int id){
        switch (type){
            case "student":
                if(!studentDAO.existsById(id))
                    return new ResponseEntity<>("此学生不存在",HttpStatus.BAD_REQUEST);
                Student student=studentDAO.findById(id);
                return new ResponseEntity<>(JSON.toJSONString(student), HttpStatus.OK);
            case "teacher":
                if(!teacherDAO.existsById(id))
                    return new ResponseEntity<>("此老师不存在",HttpStatus.BAD_REQUEST);
                Teacher teacher=teacherDAO.findById(id);
                return new ResponseEntity<>(JSON.toJSONString(teacher), HttpStatus.OK);
            case "course":
                if(!courseDAO.existsById(id))
                    return new ResponseEntity<>("此课程不存在",HttpStatus.BAD_REQUEST);
                Course course=courseDAO.findById(id);
                return new ResponseEntity<>(JSON.toJSONString(course), HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/studentCourse/{id}",method = RequestMethod.GET)
    public ResponseEntity<String> studentCourse(@PathVariable("id")int id){
        List<Course> courses=studentService.findSelectedCourses(id);
        return new ResponseEntity<>(JSON.toJSONString(courses),HttpStatus.OK);
    }

    @RequestMapping(value = "/teacherCourse/{id}",method = RequestMethod.GET)
    public ResponseEntity<String> teacherCourse(@PathVariable("id")int id){
        Teacher teacher=teacherDAO.findById(id);
        if(teacher==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Course> courses=new ArrayList<>();
        for(Integer integer:teacher.getCourse())
            courses.add(courseDAO.findById(integer.intValue()));
        return new ResponseEntity<>(JSON.toJSONString(courses),HttpStatus.OK);
    }

    @RequestMapping(value = "/addCourse",method = RequestMethod.POST)
    public ResponseEntity<String> addCourse(@RequestParam("content")String jsonString){
        Course course=JSON.parseObject(jsonString,Course.class);
        course=courseDAO.save(course);
        administerService.modify(course);
        return new ResponseEntity<>("添加成功",HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{type}/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> remove(@PathVariable("type")String type,@PathVariable("id")int id){
        switch (type){
            case "student":
                administerService.removeStudent(id);
                break;
            case "teacher":
                administerService.removeTeacher(id);
                break;
            case "administer":
                administerService.removeAdminister(id);
                break;
            case "course":
                administerService.removeCourse(id);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/avg/{type}",method = RequestMethod.GET)
    public ResponseEntity<String> avg(@PathVariable("type")String type,@RequestParam(value = "limit",required = false,defaultValue = "-1")int limit){
        if(type.equals("all")){
            List<CourseScoreInDouble> data=administerService.avgInAll();
            if(limit<0)
                limit=data.size();
            else
                limit=Math.min(limit,data.size());
            return new ResponseEntity<>(JSON.toJSONString(data.subList(0,limit)),HttpStatus.OK);
        }
        else{
            int course=Integer.parseInt(type);
            return new ResponseEntity<>(JSON.toJSONString(administerService.avg(course)),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/modify/{type}",method = RequestMethod.POST)
    public ResponseEntity<String> modify(@PathVariable("type")String type,@RequestParam("content")String jsonString){
        switch (type){
            case "student":
                Student student=JSON.parseObject(jsonString,Student.class);
                administerService.modify(student);
                break;
            case "teacher":
                Teacher teacher=JSON.parseObject(jsonString,Teacher.class);
                administerService.modify(teacher);
                break;
            case "administer":
                Administer administer=JSON.parseObject(jsonString,Administer.class);
                administerService.modify(administer);
                break;
            case "course":
                Course course=JSON.parseObject(jsonString,Course.class);
                administerService.modify(course);
                break;
            case "score":
                Score score=JSON.parseObject(jsonString,Score.class);
                teacherService.modify(score);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("修改成功",HttpStatus.OK);
    }
}
