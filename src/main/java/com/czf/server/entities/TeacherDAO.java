package com.czf.server.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDAO extends JpaRepository<Teacher,Integer> {
    boolean existsById(int id);
    Teacher findById(int id);
}
