package com.czf.server.controllers;

import com.alibaba.fastjson.JSON;
import com.czf.server.beans.Score;
import com.czf.server.entities.TeacherDAO;
import com.czf.server.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherFunctions {
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/{id}/self",method = RequestMethod.GET)
    public ResponseEntity<String> getSelf(@PathVariable("id")int id){
        if(!teacherService.checkId(id))
            return new ResponseEntity<>("该教师不存在", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(JSON.toJSONString(teacherDAO.findById(id)),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/studentScore/{stuId}",method = RequestMethod.GET)
    public ResponseEntity<String> getScore(@PathVariable("id")int id,@PathVariable("stuId")int stuId){
        if(!teacherService.checkId(id))
            return new ResponseEntity<>("该教师不存在", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(JSON.toJSONString(teacherService.sumStudent(id, stuId)),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/avg",method = RequestMethod.GET)
    public ResponseEntity<String> getAvg(@PathVariable("id")int id){
        if(!teacherService.checkId(id))
            return new ResponseEntity<>("该教师不存在", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(JSON.toJSONString(teacherService.avg(id)),HttpStatus.OK);
    }

    @RequestMapping(value = "/modifyScore",method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestParam("content")String jsonString){
        Score score=JSON.parseObject(jsonString,Score.class);
        teacherService.modify(score);
        return new ResponseEntity<>("修改成功",HttpStatus.OK);
    }

    @RequestMapping(value = "/getNameWithIDs",method = RequestMethod.GET)
    public ResponseEntity<String> getNames(@RequestParam("IDList")String list){
        List<Integer> ids=JSON.parseArray(list,Integer.class);
        if(ids==null||ids.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else{
            List<String> names=teacherDAO.findNames(ids);
            StringBuffer stringBuffer=new StringBuffer(names.get(0));
            if(names.size()>1){
                for(int i=1;i<names.size();++i){
                    stringBuffer.append('、');
                    stringBuffer.append(names.get(i));
                }
            }
            return new ResponseEntity<>(stringBuffer.toString(),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/addScore",method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestParam("teacherId")int teacher,@RequestParam("content")String content){
        List<Score> scoreList=JSON.parseArray(content,Score.class);
        String result=teacherService.addScore(scoreList,teacher);
        if(result==null||result.isEmpty())
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }
}
