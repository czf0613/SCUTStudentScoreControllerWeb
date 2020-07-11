package com.czf.server.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TeacherDAO extends JpaRepository<Teacher,Integer> {
    boolean existsById(int id);
    Teacher findById(int id);

    @Transactional
    void deleteById(int id);

    @Query("select u.name from Teacher u where u.id in :list")
    List<String> findNames(@Param("list")List<Integer> list);
}
