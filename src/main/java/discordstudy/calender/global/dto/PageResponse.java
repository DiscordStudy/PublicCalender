package discordstudy.calender.global.dto;

import java.util.List;

public record PageResponse<T>(
        Integer pageSize,
        Integer currentPage,
        Long totalElements,
        Integer totalPages,
        List<T> articles
) {
}
