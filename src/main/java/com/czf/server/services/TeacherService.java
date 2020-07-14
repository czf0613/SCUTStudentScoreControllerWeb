package com.czf.server.services;

import com.czf.server.beans.CourseScore;
import com.czf.server.beans.CourseScoreInDouble;
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
    @Autowired
    private StudentDAO studentDAO;

    public boolean checkId(int id){
        return teacherDAO.existsById(id);
    }

    public List<CourseScore> sumStudent(int teacherId,int stuId){
        Teacher teacher=teacherDAO.findById(teacherId);
        List<Integer> courses=new ArrayList<>(teacher.getCourse());
        List<CourseScore> courseScores=new ArrayList<>(scoreDAO.sumByStuIdAndCourseIn(stuId,courses));
        for(CourseScore score:courseScores)
            score.setCourseName(courseDAO.findNameById(score.getCourse()));
        courseScores.sort(CourseScore::compareTo);
        return courseScores;
    }

    public List<CourseScoreInDouble> avg(int teacherId){
        Teacher teacher=teacherDAO.findById(teacherId);
        List<Integer> courses=new ArrayList<>(teacher.getCourse());

        List<CourseScoreInDouble> courseScores=new ArrayList<>(scoreDAO.avg(courses));
        for(CourseScoreInDouble courseScoreInDouble:courseScores)
            courseScoreInDouble.setCourseName(courseDAO.findNameById(courseScoreInDouble.getCourse()));
        courseScores.sort(CourseScoreInDouble::compareTo);
        return courseScores;
    }

    public synchronized boolean modify(Score score){
        com.czf.server.entities.Score score1=scoreDAO.findByCourseIdAndStudentId(score.getCourseId(),score.getStuId());
        score1.setScore(BigDecimal.valueOf(score.getScore()));
        scoreDAO.save(score1);
        return true;
    }

    public synchronized String addScore(List<Score> scores,int teacher){
        if(!teacherDAO.existsById(teacher))
            return "该教师不存在";
        StringBuilder stringBuilder=new StringBuilder();
        for(Score score:scores){
            Course course=courseDAO.findById(score.getCourseId());
            if(course==null)
                stringBuilder.append(String.format("课程id：%d不存在\n",score.getCourseId()));

            Student student=studentDAO.findById(score.getStuId());
            if(student==null)
                stringBuilder.append(String.format("学生id：%d不存在\n",score.getStuId()));

            if(stringBuilder.length()!=0)
                return stringBuilder.toString();

            if(!course.getTeachers().contains(teacher))
                stringBuilder.append(String.format("你不是“%s”课的任课老师，不允许提交成绩\n",course.getCourseName()));

            if(!course.getStudents().contains(score.getStuId()))
                stringBuilder.append(String.format("你不是%s同学在 %s 课的任课老师，不允许提交成绩",student.getName(),course.getCourseName()));

            if(stringBuilder.length()!=0)
                return stringBuilder.toString();

            if(scoreDAO.existsByCourseIdAndStudentId(score.getCourseId(),score.getStuId())){
                com.czf.server.entities.Score score1=scoreDAO.findByCourseIdAndStudentId(score.getCourseId(),score.getStuId());
                score1.setScore(BigDecimal.valueOf(score.getScore()));
                scoreDAO.save(score1);
            }
            else{
                com.czf.server.entities.Score score1=new com.czf.server.entities.Score();
                score1.setCourseId(score.getCourseId());
                score1.setStudentId(score.getStuId());
                score1.setScore(BigDecimal.valueOf(score.getScore()));
                scoreDAO.save(score1);
            }
        }
        return stringBuilder.toString();
    }
}
