package discordstudy.calender.domain.post.controller;

import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.service.PostService;
import discordstudy.calender.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<Void>> postPost(
            @RequestBody PostRequest request,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        postService.postPost(request, userDetails.getUsername());

        return ApiResponse.ok();
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
}
