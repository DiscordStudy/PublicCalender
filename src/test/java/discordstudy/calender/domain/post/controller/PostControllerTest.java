package discordstudy.calender.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import discordstudy.calender.domain.member.util.WithMember;
import discordstudy.calender.domain.post.dto.PostDetailResponse;
import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.service.PostService;
import discordstudy.calender.global.dto.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private PostService postService;

    @DisplayName("게시글 생성 API")
    @Test
    @WithMember
    void postPostTest() throws Exception {
        //Given
        PostRequest request = new PostRequest("제목", "내용", Set.of("해쉬태그"));

        given(postService.postPost(any(PostRequest.class), anyString()))
                .willReturn(1L);

        //When & Then
        mvc.perform(
                        post("/posts")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 수정 API")
    @Test
    @WithMember
    void postUpdateTest() throws Exception {
        //Given
        PostRequest request = new PostRequest("제목", "내용", Set.of("해쉬태그"));

        //When & Then
        mvc.perform(
                        put("/posts/{postId}", 1L)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 삭제 API")
    @Test
    @WithMember
    void postDeleteTest() throws Exception {
        //When & Then
        mvc.perform(delete("/posts/{postId}", 1L).with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 전체 조회 API")
    @Test
    @WithMember
    void postAllTest() throws Exception {
        //Given
        given(postService.postAll(any())).willReturn(new PageResponse<>(20, 0, 0L, 0, List.of()));

        //When & Then
        mvc.perform(get("/posts").with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 상세 조회 API")
    @Test
    @WithMember
    void postDetailTest() throws Exception {
        //Given
        given(postService.postDetail(anyLong()))
                .willReturn(new PostDetailResponse("제목", "내용", Set.of()));

        //When & Then
        mvc.perform(get("/posts/{postId}", 1L).with(csrf()))
                .andExpect(status().isOk());
    }
}