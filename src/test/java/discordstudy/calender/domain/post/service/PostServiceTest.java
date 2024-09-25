package discordstudy.calender.domain.post.service;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.entity.Post;
import discordstudy.calender.domain.post.repository.HashtagRepository;
import discordstudy.calender.domain.post.repository.PostRepository;
import discordstudy.calender.domain.team.enums.Role;
import discordstudy.calender.global.exception.ApplicationException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Optional;
import java.util.Set;

@DisplayName("PostService 클래스의 ")
class PostServiceTest {

    @Mock
    private HashtagRepository hashtagRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PostService postService;

    private Member member = new Member(
            1L,
            "loginId",
            "홍길동",
            "길동",
            Role.MEMBER
    );

    private PostRequest successRequest = new PostRequest(
            "게시글",
            "내용",
            Set.of("해시택")
    );

    private Post post = new Post(
            1L,
            "게시글",
            "내용",
            member,
            null
    );

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("postPost() 메소드는")
    class PostPostTest {

        @DisplayName("성공한다")
        @Test
        void successTest() {
            //Given
            //BDD Mockito 라이브러리 채용
            BDDMockito.
                    given(memberRepository.findByLoginId(ArgumentMatchers.anyString()))
                    .willReturn(Optional.of(member));

            BDDMockito.
                    given(postRepository.save(ArgumentMatchers.any(Post.class)))
                    .willReturn(post);

            //When
            Long result = postService.postPost(successRequest, "loginId");

            //Then
            org.assertj.core.api.Assertions.assertThat(result).isEqualTo(post.getId());
        }

        @DisplayName("유저가 존재하지 않아 실패한다")
        @Test
        void failAboutNotExistMember() {
            //Given
            //Mockito 라이브러리 채용
            Mockito.when(memberRepository.findByLoginId(ArgumentMatchers.anyString()))
                    .thenReturn(Optional.empty());

            Assertions.assertThrows(ApplicationException.class,
                    () -> postService.postPost(successRequest, "loginId"));
        }
    }

    @Nested
    @DisplayName("postDelete() 메소드는")
    class PostDeleteTest {

    }

    @Nested
    @DisplayName("postUpdate() 메소드는")
    class PostUpdateTest {

    }
}