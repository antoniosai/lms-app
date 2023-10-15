package com.app.lms.core.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class SpecificationUtil {
    public static Pageable generatePagination(int page, int perPage) {
        return PageRequest.of(page - 1, perPage);
    }
}
