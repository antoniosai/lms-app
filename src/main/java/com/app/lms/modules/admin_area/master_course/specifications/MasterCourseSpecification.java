package com.app.lms.modules.admin_area.master_course.specifications;

import com.app.lms.core.utils.StringUtil;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class MasterCourseSpecification {


    public static Specification<MasterCourseEntity> searchByPaginationRequest(GetMasterCourseRequest request) {
        return Specification
                .where(inCourseUuid(request.getCoursesUuid()))
                .and(equalInstructorUuid(request.getInstructorUuid()));
    }

    private static Specification<MasterCourseEntity> likeCourseName(String courseName) {

        if (courseName == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("courseName"), StringUtil.transformToKeyword(courseName));
    }

    private static Specification<MasterCourseEntity> equalInstructorUuid(UUID instructorUuid) {
        if (instructorUuid == null) return null;

        return (root, query, builder) ->
                builder.equal(root.get("courseInstructor").get("instructorUuid"), instructorUuid);
    }

    private static Specification<MasterCourseEntity> inCourseUuid(List<UUID> coursesUuid) {
        if (coursesUuid == null) return null;

        return (root, query, builder) ->
                root.get("courseUuid").in(coursesUuid);
    }
}
