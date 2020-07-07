package com.czf.server.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface AdministerDAO extends JpaRepository<Administer,Integer> {
    @Transactional
    void deleteById(int id);
}
