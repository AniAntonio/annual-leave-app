package com.lhind.service.specification;

import com.lhind.dto.application.ApplicationFilterDto;
import com.lhind.entities.Application;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationSpecification {

    public static Specification<Application> getUserApplications(ApplicationFilterDto applicationFilterDto) {
        return ((root, query, cb) -> {
            List<Predicate> whereConditions = new ArrayList<>();

            if (applicationFilterDto.getDaysOff() != null) {
                whereConditions.add(cb.equal(root.get("daysOff"), applicationFilterDto.getDaysOff()));
            }

            if (applicationFilterDto.getStatus() != null) {
                whereConditions.add(cb.equal((root.get("status")), applicationFilterDto.getStatus()));
            }

            if (applicationFilterDto.getApplicationDateFrom() != null) {
                whereConditions.add(cb.greaterThanOrEqualTo(root.get("startDate"), applicationFilterDto.getApplicationDateFrom()));
            }
            if (applicationFilterDto.getApplicationDateTo() != null) {
                whereConditions.add(cb.lessThanOrEqualTo(root.get("startDate"), applicationFilterDto.getApplicationDateTo()));
            }

            if (applicationFilterDto.getUserId() != null) {
                whereConditions.add(cb.equal(root.get("user").get("id"), applicationFilterDto.getUserId()));
            }

            if (applicationFilterDto.getSupervisorId() != null) {
                whereConditions.add(cb.equal(root.get("supervisor").get("id"), applicationFilterDto.getSupervisorId()));
            }

            return cb.and(whereConditions.toArray(new Predicate[0]));
        });
    }
}
