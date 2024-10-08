package discordstudy.calender.domain.post.util;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.dto.PostAllResponse;
import discordstudy.calender.domain.post.entity.HashtagMap;
import discordstudy.calender.domain.post.entity.Post;

import java.util.Set;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post createPost(PostRequest request, Member member, Set<HashtagMap> hashtagMaps) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .hashtagMaps(hashtagMaps)
                .member(member)
                .build();
    }

    public static PostAllResponse toDto(Post post) {
        return new PostAllResponse(post.getId(), post.getTitle(), post.getTitle(),
                post.getHashtagMaps().stream()
                .map(v -> v.getHashtag().getTag())
                        .collect(Collectors.toSet())
        );
    }
}
