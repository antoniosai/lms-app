package com.app.lms.modules.admin_area.admin.specifications;

import com.app.lms.core.utils.StringUtil;
import com.app.lms.modules.admin_area.admin.entities.AdminEntity;
import com.app.lms.modules.admin_area.admin.requests.GetAdministratorRequest;
import org.springframework.data.jpa.domain.Specification;

public class AdministratorSpecification {

    public static Specification<AdminEntity> searchByPaginationRequest(GetAdministratorRequest request) {
        return Specification
                .where(likeAdminName(request.getName()))
                .or(likeAdminIdentificationNumber(request.getIdentificationNumber()));
    }

    private static Specification<AdminEntity> likeAdminName(String studentName) {

        if(studentName == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentName"), StringUtil.transformToKeyword(studentName));
    }

    private static Specification<AdminEntity> likeAdminIdentificationNumber(String studentIdentificationNumber) {

        if(studentIdentificationNumber == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentIdentificationNumber"), StringUtil.transformToKeyword(studentIdentificationNumber));
    }
}
