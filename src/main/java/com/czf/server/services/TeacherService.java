package com.czf.server.services;

import com.czf.server.beans.CourseScore;
import com.czf.server.beans.Score;
import com.czf.server.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private ScoreDAO scoreDAO;
    @Autowired
    private CourseDAO courseDAO;

    public boolean checkId(int id){
        return teacherDAO.existsById(id);
    }

    public List<CourseScore> sumStudent(int teacherId,int stuId){
        Teacher teacher=teacherDAO.findById(teacherId);
        List<Integer> courses=new ArrayList<>();
        for(Course course:teacher.getCourse())
            courses.add(course.getId());
        List<CourseScore> courseScores=new ArrayList<>(scoreDAO.sumByStuIdAndCourseIn(stuId,courses));
        for(CourseScore score:courseScores)
            score.setCourseName(courseDAO.findNameById(score.getCourse()));
        courseScores.sort(CourseScore::compareTo);
        return courseScores;
    }

    public List<CourseScore> avg(int teacherId){
        Teacher teacher=teacherDAO.findById(teacherId);
        List<Integer> courses=new ArrayList<>();
        for(Course course:teacher.getCourse())
            courses.add(course.getId());

        List<CourseScore> courseScores=new ArrayList<>(scoreDAO.avg(courses));
        for(CourseScore score:courseScores)
            score.setCourseName(courseDAO.findNameById(score.getCourse()));
        courseScores.sort(CourseScore::compareTo);
        return courseScores;
    }

    public synchronized boolean modify(Score score){
        com.czf.server.entities.Score score1=scoreDAO.findByCourseIdAndStudentId(score.getCourseId(),score.getStuId());
        score1.setScore(BigDecimal.valueOf(score.getScore()));
        scoreDAO.save(score1);
        return true;
    }
}
