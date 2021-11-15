package com.lhind.repository;

import com.lhind.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select p from #{#entityName} p where p.username = ?1 ")
    Optional<User> findByUsername(String userName);

    @Query("select case when count(u) > 0 then true else false end from #{#entityName} u where u.username = ?1 ")
    boolean userExistsWithUsername(String username);

    @Query("select case when count(p) > 0 then true else false end from #{#entityName} p where p.email = ?1 and p.id = ?2 ")
    boolean userExistsWithEmailAndId(String email,Integer id);

    @Modifying
    @Query("update #{#entityName} p set p.flagDeleted=true where p.id = ?1")
    void delete(Integer id);

    @Query("select case when count(p) > 0 then true else false end from #{#entityName} p where p.id = ?1 ")
    boolean userExistsWithId(Integer id);
}
