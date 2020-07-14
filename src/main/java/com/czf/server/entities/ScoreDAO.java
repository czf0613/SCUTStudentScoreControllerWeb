package com.czf.server.entities;

import com.czf.server.beans.CourseScore;
import com.czf.server.beans.CourseScoreInDouble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ScoreDAO extends JpaRepository<Score,Integer> {
    @Query("select new com.czf.server.beans.CourseScore(u.courseId,u.score) from Score u where u.studentId= :stuId")
    List<CourseScore> sumByStuId(@Param("stuId")int id);

    @Query("select new com.czf.server.beans.CourseScore(u.courseId,u.score) from Score u where u.studentId= :stuId and u.courseId in :courses")
    List<CourseScore> sumByStuIdAndCourseIn(@Param("stuId")int id,@Param("courses")List<Integer> courses);

    @Query("select new com.czf.server.beans.CourseScoreInDouble(u.courseId,avg(u.score)) from Score u where u.courseId in :courses group by u.courseId")
    List<CourseScoreInDouble> avg(@Param("courses")List<Integer> courses);

    Score findByCourseIdAndStudentId(int courseId,int stuId);

    @Transactional
    void deleteAllByCourseId(int id);

    boolean existsByCourseIdAndStudentId(int courseId,int stuId);
}
