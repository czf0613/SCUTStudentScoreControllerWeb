package com.czf.server.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public interface CourseDAO extends JpaRepository<Course,Integer> {
    List<Course> findAllByStartDateBefore(Date date);
    Course findById(int id);
    boolean existsById(int id);

    @Transactional
    void deleteById(int id);

    @Query("select u.courseName from Course u where u.id=:id")
    String findNameById(@Param("id") int id);

    @Query("select u.id from Course u")
    List<Integer> findAllId();
}
