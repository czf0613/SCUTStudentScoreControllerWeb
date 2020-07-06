package com.czf.server.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDAO extends JpaRepository<Student,Integer> {
}
