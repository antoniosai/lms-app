package com.app.lms.composites.instructor_area.course.specifications;

import com.app.lms.core.utils.StringUtil;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import org.springframework.data.jpa.domain.Specification;

public class InstructorCourseSpecifications {

    public static Specification<CourseEntity> searchByPaginationRequest(GetCourseRequest request) {
        return Specification
                .where(likeCourseName(request.getCourseName()));
    }

    private static Specification<CourseEntity> likeCourseName(String courseName) {

        if (courseName == null) return null;

        return (root, query, builder) ->
                builder.like(root.get("courseName"), StringUtil.transformToKeyword(courseName));
    }
}
