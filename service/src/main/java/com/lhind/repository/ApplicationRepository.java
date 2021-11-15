package com.lhind.repository;

import com.lhind.entities.Application;
import com.lhind.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer>, JpaSpecificationExecutor<Application> {

    @Query("select app from #{#entityName} app where app.user.id = ?1 and app.status=?2")
    List<Application> getRequestedApplications(Integer userId, ApplicationStatus applicationStatus);

    @Modifying
    @Query("update #{#entityName} app set app.flagDeleted=true where app.id = ?1")
    void delete(Integer id);

    @Query("select case when count(app) > 0 then true else false end from #{#entityName} app where app.id = ?1 ")
    boolean userExistsWithId(Integer id);

}
