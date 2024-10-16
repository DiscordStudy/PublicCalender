package discordstudy.calender.domain.post.util;

import discordstudy.calender.domain.member.util.MemberFixture;
import discordstudy.calender.domain.post.entity.Post;

import java.util.HashSet;

public class PostFixture {
    public static Post post = new Post(
            1L,
            "게시글",
            "내용",
            MemberFixture.member,
            new HashSet<>()
    );
}
