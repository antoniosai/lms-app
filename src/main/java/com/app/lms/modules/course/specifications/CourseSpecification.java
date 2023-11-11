package com.app.lms.modules.course.specifications;

import com.app.lms.core.utils.StringUtil;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class CourseSpecification {


    public static Specification<CourseEntity> searchByPaginationRequest(GetCourseRequest request) {
        return Specification
                .where(inCourseUuid(request.getCoursesUuid()))
                .and(equalInstructorUuid(request.getInstructorUuid()));
    }

    private static Specification<CourseEntity> likeCourseName(String courseName) {

        if (courseName == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("courseName"), StringUtil.transformToKeyword(courseName));
    }

    private static Specification<CourseEntity> equalInstructorUuid(UUID instructorUuid) {
        if (instructorUuid == null) return null;

        return (root, query, builder) ->
                builder.equal(root.get("courseInstructor").get("instructorUuid"), instructorUuid);
    }

    private static Specification<CourseEntity> inCourseUuid(List<UUID> coursesUuid) {
        if (coursesUuid == null) return null;

        return (root, query, builder) ->
                root.get("courseUuid").in(coursesUuid);
    }
}
