package com.czf.server.services;

import com.czf.server.beans.CourseScore;
import com.czf.server.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private ScoreDAO scoreDAO;

    public boolean checkId(int id){
        return studentDAO.existsById(id);
    }

    public List<Course> findPossibleCourses(int id){
        Student student=studentDAO.findById(id);
        List<Course> courses=courseDAO.findAllByStartDateBefore(new Date(System.currentTimeMillis()));
        if(courses==null)
            courses=new ArrayList<>();
        courses.removeIf(next -> !next.getGrade().contains(student.getGrade()));
        return courses;
    }

    public List<Course> findSelectedCourses(int id){
        Student student=studentDAO.findById(id);
        List<Course> courses=new ArrayList<>();
        List<Integer> courseIds=student.getCourse();
        if(courseIds==null||courseIds.isEmpty())
            courses=new ArrayList<>();
        for(Integer integer:courseIds)
            courses.add(courseDAO.findById(integer.intValue()));
        return courses;
    }

    public List<CourseScore> sumAll(int stuId){
        List<CourseScore> scores=scoreDAO.sumByStuId(stuId);
        for(CourseScore courseScore:scores)
            courseScore.setCourseName(courseDAO.findNameById(courseScore.getCourse()));
        return scores;
    }

    public CourseScore sumOne(int stuId,int courseId){
        Score score=scoreDAO.findByCourseIdAndStudentId(courseId,stuId);
        CourseScore courseScore=new CourseScore(courseId,score.getScore());
        courseScore.setCourseName(courseDAO.findNameById(courseId));
        return courseScore;
    }

    public synchronized boolean selectCourse(int stuId,List<Integer> courses){
        Student student=studentDAO.findById(stuId);
        ArrayList<Integer> selected=new ArrayList<>(student.getCourse());

        for(Integer integer:courses){
            if(!selected.contains(integer)) {
                Course course=courseDAO.findById(integer.intValue());
                List<Integer> students=course.getStudents();
                students.add(stuId);

                course.setStudents(students);
                courseDAO.save(course);
                selected.add(integer);
            }
        }
        student.setCourse(selected);
        studentDAO.save(student);
        return true;
    }

    public synchronized boolean remove(int stuId,int courseId){
        Student student=studentDAO.findById(stuId);
        student.getCourse().remove((Integer)courseId);
        Course course=courseDAO.findById(courseId);
        course.getStudents().remove((Integer)stuId);

        studentDAO.save(student);
        courseDAO.save(course);
        return true;
    }
}
