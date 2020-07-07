package com.czf.server.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User,Integer> {
    boolean existsByUserName(String userName);

    User findByUserName(String userName);

    @Query(value = "select u.flag from User u where u.userName= :userName")
    int getTypeWithUserName(@Param("userName")String userName);

    @Query(value = "select u.password from User u where u.userName= :userName")
    String getPasswordWithUserName(@Param("userName")String userName);

    @Query(value = "select u.account from User u where u.userName= :userName")
    int getAccountWithUserName(@Param("userName")String userName);
}
