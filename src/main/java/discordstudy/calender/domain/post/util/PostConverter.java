package discordstudy.calender.domain.post.util;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.entity.HashtagMap;
import discordstudy.calender.domain.post.entity.Post;

import java.util.Set;

public class PostConverter {

    public static Post createPost(PostRequest request, Member member, Set<HashtagMap> hashtagMaps) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .hashtagMaps(hashtagMaps)
                .member(member)
                .build();
    }
}
