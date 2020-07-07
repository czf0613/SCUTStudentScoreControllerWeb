package com.czf.server.entities;

import com.czf.server.beans.Beans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreDAO extends JpaRepository<Score,Integer> {
    @Query("select new com.czf.server.beans.Beans.CourseScore(u.courseId,u.score) from Score u where u.studentId= :stuId")
    List<Beans.CourseScore> sumByStuId(@Param("stuId")int id);

    Score findByCourseIdAndStudentId(int courseId,int stuId);
}
