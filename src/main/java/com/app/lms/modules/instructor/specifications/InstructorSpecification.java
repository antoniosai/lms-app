package com.app.lms.modules.instructor.specifications;

import com.app.lms.core.utils.StringUtil;
import com.app.lms.modules.instructor.entities.InstructorEntity;
import com.app.lms.modules.instructor.requests.GetInstructorRequest;
import org.springframework.data.jpa.domain.Specification;

public class InstructorSpecification {

    public static Specification<InstructorEntity> searchByPaginationRequest(GetInstructorRequest request) {
        return Specification
                .where(likeInstructorName(request.getName()))
                .or(likeInstructorIdentificationNumber(request.getIdentificationNumber()));
    }

    private static Specification<InstructorEntity> likeInstructorName(String studentName) {

        if (studentName == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentName"), StringUtil.transformToKeyword(studentName));
    }

    private static Specification<InstructorEntity> likeInstructorIdentificationNumber(String studentIdentificationNumber) {

        if (studentIdentificationNumber == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentIdentificationNumber"), StringUtil.transformToKeyword(studentIdentificationNumber));
    }
}
