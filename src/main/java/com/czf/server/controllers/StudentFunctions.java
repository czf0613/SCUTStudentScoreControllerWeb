package com.czf.server.controllers;

import com.alibaba.fastjson.JSON;
import com.czf.server.beans.Beans;
import com.czf.server.entities.Course;
import com.czf.server.entities.Student;
import com.czf.server.entities.StudentDAO;
import com.czf.server.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentFunctions {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/{id}/self",method = RequestMethod.GET)
    public ResponseEntity<String> getSelf(@PathVariable("id")int id){
        Student student=studentDAO.findById(id);
        if(student==null)
            return new ResponseEntity<>("该学生不存在", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(JSON.toJSONString(student),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/courses/{type}",method = RequestMethod.GET)
    public ResponseEntity<String> getCourses(@PathVariable("id")int id,@PathVariable("type")String type){
        if(!studentService.checkId(id))
            return new ResponseEntity<>("该学生不存在", HttpStatus.NOT_FOUND);
        else{
            List<Course> courses;
            switch (type){
                case "all":
                    courses=studentService.findPossibleCourses(id);
                    return new ResponseEntity<>(JSON.toJSONString(courses),HttpStatus.OK);
                case "selected":
                    courses=studentService.findSelectedCourses(id);
                    return new ResponseEntity<>(JSON.toJSONString(courses),HttpStatus.OK);
                case "selectable":
                    courses=studentService.findPossibleCourses(id);
                    courses.removeAll(studentService.findSelectedCourses(id));
                    return new ResponseEntity<>(JSON.toJSONString(courses),HttpStatus.OK);
                default:
                    return new ResponseEntity<>("出错",HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/{id}/score/{type}",method = RequestMethod.GET)
    public ResponseEntity<String> getScore(@PathVariable("id")int id,@PathVariable("type")String type){
        if(!studentService.checkId(id))
            return new ResponseEntity<>("该学生不存在", HttpStatus.NOT_FOUND);
        else{
            if ("all".equals(type)) {
                List<Beans.CourseScore> scores = studentService.sumAll(id);
                return new ResponseEntity<>(JSON.toJSONString(scores), HttpStatus.OK);
            }
            int courseId = Integer.parseInt(type);
            Beans.CourseScore courseScore = studentService.sumOne(id, courseId);
            return new ResponseEntity<>(JSON.toJSONString(courseScore), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}/choose",method = RequestMethod.POST)
    public ResponseEntity<String> selectCourse(@PathVariable("id")int id,@RequestParam("courseId")String ids){
        if(!studentService.checkId(id))
            return new ResponseEntity<>("该学生不存在", HttpStatus.NOT_FOUND);
        else{
            List<Integer> courses= JSON.parseArray(ids,Integer.class);
            studentService.selectCourse(id,courses);
            return new ResponseEntity<>("选课已提交，请刷新查看",HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}/remove",method = RequestMethod.POST)
    public ResponseEntity<String> selectCourse(@PathVariable("id")int id,@RequestParam("courseId")int courseId){
        if(!studentService.checkId(id))
            return new ResponseEntity<>("该学生不存在", HttpStatus.NOT_FOUND);
        else{
            studentService.remove(id,courseId);
            return new ResponseEntity<>("退选成功",HttpStatus.OK);
        }
    }
}
