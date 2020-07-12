package com.czf.server.services;

import com.czf.server.beans.CourseScoreInDouble;
import com.czf.server.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdministerService {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private AdministerDAO administerDAO;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private ScoreDAO scoreDAO;
    @Autowired
    private UserDAO userDAO;

    public boolean removeStudent(int id){
        studentDAO.deleteById(id);
        userDAO.deleteAllByFlagAndAccount(1,id);
        return true;
    }

    public boolean removeTeacher(int id){
        teacherDAO.deleteById(id);
        userDAO.deleteAllByFlagAndAccount(2,id);
        return true;
    }

    public boolean removeAdminister(int id){
        administerDAO.deleteById(id);
        userDAO.deleteAllByFlagAndAccount(3,id);
        return true;
    }

    public synchronized boolean removeCourse(int id){
        courseDAO.deleteById(id);
        scoreDAO.deleteAllByCourseId(id);
        List<Student> students=studentDAO.findAll();
        List<Teacher> teachers=teacherDAO.findAll();

        for(Student student:students)
            student.getCourse().remove((Integer)id);
        for(Teacher teacher:teachers)
            teacher.getCourse().remove((Integer)id);

        studentDAO.saveAll(students);
        teacherDAO.saveAll(teachers);
        return true;
    }

    public List<CourseScoreInDouble> avgInAll(){
        List<Integer> allCourses=courseDAO.findAllId();
        List<CourseScoreInDouble> courseScoreInDoubleList=scoreDAO.avg(allCourses);
        courseScoreInDoubleList.sort(CourseScoreInDouble::compareTo);
        for(CourseScoreInDouble courseScoreInDouble:courseScoreInDoubleList)
            courseScoreInDouble.setCourseName(courseDAO.findNameById(courseScoreInDouble.getCourse()));
        return courseScoreInDoubleList;
    }

    public CourseScoreInDouble avg(int id){
        List<CourseScoreInDouble> courseScoreInDoubleList=scoreDAO.avg(Arrays.asList(id));
        if(courseScoreInDoubleList==null||courseScoreInDoubleList.isEmpty())
            return new CourseScoreInDouble(id,0);
        courseScoreInDoubleList.get(0).setCourseName(courseDAO.findNameById(id));
        return courseScoreInDoubleList.get(0);
    }

    public boolean modify(Student student){
        studentDAO.save(student);
        return true;
    }

    public boolean modify(Teacher teacher){
        teacherDAO.save(teacher);
        return true;
    }

    public boolean modify(Administer administer){
        administerDAO.save(administer);
        return true;
    }

    public boolean modify(Course course){
        for(Integer integer:course.getTeachers()){
            Teacher teacher=teacherDAO.findById(integer.intValue());
            if(!teacher.getCourse().contains(course.getId()))
                teacher.getCourse().add(course.getId());
            teacherDAO.save(teacher);
        }
        courseDAO.save(course);
        return true;
    }
}
