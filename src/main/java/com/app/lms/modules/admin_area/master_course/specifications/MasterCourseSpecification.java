package com.app.lms.modules.admin_area.master_course.specifications;

import com.app.lms.core.utils.StringUtil;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import org.springframework.data.jpa.domain.Specification;

public class MasterCourseSpecification {


    public static Specification<MasterCourseEntity> searchByPaginationRequest(GetMasterCourseRequest request) {
        return Specification
                .where(likeCourseName(request.getCourseName()));
    }

    private static Specification<MasterCourseEntity> likeCourseName(String studentName) {

        if (studentName == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("studentName"), StringUtil.transformToKeyword(studentName));
    }
}
