package com.app.lms.modules.admin_area.student.specifications;

import com.app.lms.core.utils.StringUtil;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.requests.GetStudentRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class StudentSpecification {

    public static Specification<StudentEntity> searchByPaginationRequest(GetStudentRequest request) {
        return Specification
                .where(whereStudentInByUuid(request.getStudentUuid()))
                .or(likeStudentName(request.getName()))
                .or(likeStudentIdentificationNumber(request.getIdentificationNumber()));
    }


    private static Specification<StudentEntity> whereStudentInByUuid(List<UUID> studentUuid) {
        if (studentUuid == null) return null;

        return (root, query, builder) ->
                root.get("studentUuid").in(studentUuid);
    }


    private static Specification<StudentEntity> likeStudentEmail(String studentEmail) {

        if (studentEmail == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentEmail"), StringUtil.transformToKeyword(studentEmail));
    }

    private static Specification<StudentEntity> likeStudentName(String studentName) {

        if (studentName == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentName"), StringUtil.transformToKeyword(studentName));
    }

    private static Specification<StudentEntity> likeStudentIdentificationNumber(String studentIdentificationNumber) {

        if (studentIdentificationNumber == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentIdentificationNumber"), StringUtil.transformToKeyword(studentIdentificationNumber));
    }
}
