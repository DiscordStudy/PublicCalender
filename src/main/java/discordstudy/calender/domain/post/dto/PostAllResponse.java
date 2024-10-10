package discordstudy.calender.domain.post.dto;

import java.util.Set;

public record PostAllResponse(
        Long postId,
        String title,
//        Category category,
        Set<String> hashtag
) {
}
