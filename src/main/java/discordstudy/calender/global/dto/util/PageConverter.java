package discordstudy.calender.global.dto.util;

import discordstudy.calender.global.dto.PageResponse;
import org.springframework.data.domain.Page;

public class PageConverter {

    public static PageResponse toDto(Page page) {
        return new PageResponse<>(
                page.getSize(),
                page.getPageable().getPageNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
