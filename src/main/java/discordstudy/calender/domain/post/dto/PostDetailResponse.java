package discordstudy.calender.domain.post.dto;

import java.util.Set;

public record PostDetailResponse(
        String title,
        String content,
//        Category category,
        Set<String> hashtag
) {
}
