package discordstudy.calender.domain.post.controller;

import discordstudy.calender.domain.post.dto.PostAllResponse;
import discordstudy.calender.domain.post.dto.PostCreateResponse;
import discordstudy.calender.domain.post.dto.PostDetailResponse;
import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.service.PostService;
import discordstudy.calender.global.dto.ApiResponse;
import discordstudy.calender.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostCreateResponse>> postPost(
            @RequestBody PostRequest request,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ApiResponse.ok(
                new PostCreateResponse(
                        postService.postPost(request, userDetails.getUsername())
                )
        );
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> postUpdate(
            @RequestBody PostRequest request,
            @PathVariable Long postId,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        postService.postUpdate(request, postId, userDetails.getUsername());

        return ApiResponse.ok();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> postDelete(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        postService.postDelete(postId, userDetails.getUsername());

        return ApiResponse.ok();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostAllResponse>>> postAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageRequest
    ) {
        return ApiResponse.ok(
                postService.postAll(pageRequest)
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> postDetail(@PathVariable Long postId){
        return ApiResponse.ok(
                postService.postDetail(postId)
        );
    }
}
