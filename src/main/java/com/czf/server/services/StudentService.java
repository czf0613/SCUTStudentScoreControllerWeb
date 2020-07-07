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
        List<Course> courses=student.getCourse();
        if(courses.isEmpty())
            courses=new ArrayList<>();
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
        ArrayList<Integer> selected=new ArrayList<>();
        List<Course> selectedCourse=new ArrayList<>(student.getCourse());
        for(Course course:student.getCourse())
            selected.add(course.getId());

        for(Integer integer:courses){
            if(!selected.contains(integer)) {
                Course course=courseDAO.findById(integer.intValue());
                List<Student> students=course.getStudents();
                students.add(studentDAO.findById(integer.intValue()));
                course.setStudents(students);
                courseDAO.save(course);
                selectedCourse.add(course);
            }
        }
        student.setCourse(selectedCourse);
        studentDAO.save(student);
        return true;
    }

    public synchronized boolean remove(int stuId,int courseId){
        Student student=studentDAO.findById(stuId);
        List<Course> selectedCourse=student.getCourse();
        selectedCourse.removeIf(course -> course.getId() == courseId);
        Course course=courseDAO.findById(courseId);
        List<Student> students=course.getStudents();
        students.removeIf(student1 -> student1.getId()==stuId);

        student.setCourse(selectedCourse);
        course.setStudents(students);
        studentDAO.save(student);
        courseDAO.save(course);
        return true;
    }
}
