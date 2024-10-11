package discordstudy.calender.domain.post.service;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import discordstudy.calender.domain.post.dto.PostAllResponse;
import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.entity.Post;
import discordstudy.calender.domain.post.repository.HashtagRepository;
import discordstudy.calender.domain.post.repository.PostRepository;
import discordstudy.calender.domain.team.enums.Role;
import discordstudy.calender.global.dto.PageResponse;
import discordstudy.calender.global.exception.ApplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static discordstudy.calender.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

    private Member authorMember = new Member(
            1L,
            "loginId",
            "홍길동",
            "길동",
            Role.MEMBER
    );

    private Member admin = new Member(
            2L,
            "adminId",
            "어드민",
            "password",
            Role.ADMIN
    );

    private Member otherMember = new Member(
            2L,
            "loginIds",
            "고길동",
            "password",
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
            authorMember,
            new HashSet<>()
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
            BDDMockito.given(memberRepository.findByLoginId(ArgumentMatchers.anyString()))
                    .willReturn(Optional.of(authorMember));

            given(postRepository.save(ArgumentMatchers.any(Post.class)))
                    .willReturn(post);

            //When
            Long result = postService.postPost(successRequest, authorMember.getLoginId());

            //Then
            Assertions.assertThat(result).isEqualTo(post.getId());
        }

        @DisplayName("유저가 존재하지 않아 실패한다")
        @Test
        void failAboutNotExistMember() {
            //Given
            //Mockito 라이브러리 채용
            Mockito.when(memberRepository.findByLoginId(ArgumentMatchers.anyString()))
                    .thenReturn(Optional.empty());

            //When
            //원하는 Exception 이 발생했는지 확인하는 코드
            ApplicationException result = assertThrows(ApplicationException.class,
                    () -> postService.postPost(successRequest, authorMember.getLoginId()));

            //Then
            assertThat(result.getErrorCode()).isEqualTo(USER_NOT_EXIST);
        }
    }

    @Nested
    @DisplayName("postDelete() 메소드는")
    class PostDeleteTest {

        @DisplayName("본인이 삭제에 성공한다")
        @Test
        void successByAuthorTest() {
            //Given
            given(memberRepository.findByLoginId(anyString()))
                    .willReturn(Optional.of(authorMember));

            given(postRepository.findById(anyLong()))
                    .willReturn(Optional.of(post));
            //When
            postService.postDelete(post.getId(), authorMember.getLoginId());

            //Then
            //기대하는 메소드가 원하는 만큼 실행되었는지 확인하는 코드
            // 기본값 times(1)
            verify(postRepository).delete(post);
        }

        @DisplayName("관리자가 삭제에 성공한다")
        @Test
        void successByAdminTest() {
            //Given
            given(memberRepository.findByLoginId(anyString()))
                    .willReturn(Optional.of(admin));

            given(postRepository.findById(anyLong()))
                    .willReturn(Optional.of(post));
            //When
            postService.postDelete(post.getId(), "admin");

            //Then
            verify(postRepository).delete(post);
        }

        @DisplayName("유저가 존재하지 않아 실패한다")
        @Test
        void failAboutNotExistMember() {
            //Given
            given(memberRepository.findByLoginId(ArgumentMatchers.anyString()))
                    .willReturn(Optional.empty());

            //When
            ApplicationException result = assertThrows(ApplicationException.class,
                    () -> postService.postDelete(post.getId(), "notExistId"));

            //Then
            assertThat(result.getErrorCode()).isEqualTo(USER_NOT_EXIST);
        }

        @DisplayName("게시글이 존재하지 않아 실패한다")
        @Test
        void failAboutNotExistPost() {
            //Given
            given(memberRepository.findByLoginId(anyString()))
                    .willReturn(Optional.of(authorMember));

            given(postRepository.findById(anyLong()))
                    .willReturn(Optional.empty());

            //When
            ApplicationException result = assertThrows(ApplicationException.class,
                    () -> postService.postDelete(post.getId(), authorMember.getLoginId()));

            //Then
            assertThat(result.getErrorCode()).isEqualTo(POST_NOT_EXIST);
        }

        @DisplayName("작성자가 달라 삭제에 실패한다")
        @Test
        void failByDifferentAuthor() {
            //Given
            given(memberRepository.findByLoginId(anyString()))
                    .willReturn(Optional.of(otherMember));

            given(postRepository.findById(anyLong()))
                    .willReturn(Optional.of(post));
            //Then & When
            ApplicationException result = assertThrows(ApplicationException.class,
                    () -> postService.postDelete(post.getId(), otherMember.getLoginId()));

            assertThat(result.getErrorCode()).isEqualTo(UNAUTHORIZED_ACCESS);
        }
    }

    @Nested
    @DisplayName("postUpdate() 메소드는")
    class PostUpdateTest {

        PostRequest editedRequest = new PostRequest(
                "제목변경됨",
                "내용변경됨",
                Set.of("해시택")
        );

        @DisplayName("성공한다")
        @Test
        void successTest() {
            //Given
            given(memberRepository.findByLoginId(anyString()))
                    .willReturn(Optional.of(authorMember));

            given(postRepository.findById(anyLong()))
                    .willReturn(Optional.of(post));

            //중간 객체의 검증을 위한 메소드
            ArgumentCaptor<Post> capturedPost = ArgumentCaptor.forClass(Post.class);

            //When
            postService.postUpdate(editedRequest, post.getId(), authorMember.getLoginId());

            //Then
            verify(postRepository).save(capturedPost.capture());

            Post replacedPost = capturedPost.getValue();
            assertThat(replacedPost.getTitle()).isEqualTo(editedRequest.title());
            assertThat(replacedPost.getContent()).isEqualTo(editedRequest.content());
        }

        @DisplayName("유저가 존재하지 않아 실패한다")
        @Test
        void failAboutNotExistMember() {
            //Given
            given(memberRepository.findByLoginId(ArgumentMatchers.anyString()))
                    .willReturn(Optional.empty());

            //When
            ApplicationException result = assertThrows(ApplicationException.class,
                    () -> postService.postUpdate(editedRequest, post.getId(), "notExistId"));

            //Then
            assertThat(result.getErrorCode()).isEqualTo(USER_NOT_EXIST);
        }

        @DisplayName("게시글이 존재하지 않아 실패한다")
        @Test
        void failAboutNotExistPost() {
            //Given
            given(memberRepository.findByLoginId(anyString()))
                    .willReturn(Optional.of(authorMember));

            given(postRepository.findById(anyLong()))
                    .willReturn(Optional.empty());

            //When
            ApplicationException result = assertThrows(ApplicationException.class,
                    () -> postService.postUpdate(editedRequest, post.getId(), authorMember.getLoginId()));

            //Then
            assertThat(result.getErrorCode()).isEqualTo(POST_NOT_EXIST);
        }

        @DisplayName("작성자가 달라 수정에 실패한다")
        @Test
        void failByDifferentAuthor() {
            //Given
            given(memberRepository.findByLoginId(anyString()))
                    .willReturn(Optional.of(otherMember));

            given(postRepository.findById(anyLong()))
                    .willReturn(Optional.of(post));
            //Then & When
            ApplicationException result = assertThrows(ApplicationException.class,
                    () -> postService.postUpdate(editedRequest, post.getId(), otherMember.getLoginId()));

            assertThat(result.getErrorCode()).isEqualTo(UNAUTHORIZED_ACCESS);
        }
    }

    @Nested
    @DisplayName("postAll() 메소드는")
    class PostAllTest{
        @DisplayName("성공한다")
        @Test
        void successTest() {
            //Given
            PageRequest pageRequest = PageRequest.of(0, 20);
            given(postRepository.findAll(any(Pageable.class)))
                    .willReturn(new PageImpl<>(List.of(post),pageRequest,1));

            //When
            PageResponse<PostAllResponse> result = postService.postAll(pageRequest);

            //Then
            assertThat(result.pageSize()).isEqualTo(20);
            assertThat(result.articles().get(0).postId()).isEqualTo(1L);
        }
    }

    /* TODO
        replacePost, handlingHashtag 에 대한 테스트 코드 필요
     */
}


