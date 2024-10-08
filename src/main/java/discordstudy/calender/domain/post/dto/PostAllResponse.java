package discordstudy.calender.domain.post.dto;

import java.util.Set;

public record PostAllResponse(
        Long postId,
        String title,
        String content,
//        Category category,
        Set<String> hashtag
) {
}
