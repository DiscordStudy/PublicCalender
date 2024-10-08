package discordstudy.calender.domain.post.service;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import discordstudy.calender.domain.post.dto.PostAllResponse;
import discordstudy.calender.domain.post.dto.PostRequest;
import discordstudy.calender.domain.post.entity.Hashtag;
import discordstudy.calender.domain.post.entity.HashtagMap;
import discordstudy.calender.domain.post.entity.Post;
import discordstudy.calender.domain.post.repository.HashtagRepository;
import discordstudy.calender.domain.post.repository.PostRepository;
import discordstudy.calender.domain.post.util.PostConverter;
import discordstudy.calender.domain.team.enums.Role;
import discordstudy.calender.global.dto.PageResponse;
import discordstudy.calender.global.dto.util.PageConverter;
import discordstudy.calender.global.exception.ApplicationException;
import discordstudy.calender.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final HashtagRepository hashtagRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long postPost(PostRequest request, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXIST));

        Set<HashtagMap> hashtagMaps = handlingHashtag(request.hashtag());

        Post post = postRepository.save(PostConverter.createPost(request, member, hashtagMaps));

        hashtagMaps.forEach(hashtagMap -> hashtagMap.setPost(post));

        return post.getId();
    }

    private Set<HashtagMap> handlingHashtag(Set<String> hashtags) {
        List<Hashtag> existingTags = hashtagRepository.findByTagIn(hashtags);
        Set<String> existingTagNames = existingTags.stream()
                .map(Hashtag::getTag)
                .collect(Collectors.toSet());

        List<Hashtag> newTags = hashtags.stream()
                .filter(tag -> !existingTagNames.contains(tag))
                .map(Hashtag::new)
                .collect(Collectors.toList());

        if (!newTags.isEmpty()) {
            hashtagRepository.saveAll(newTags);
            existingTags.addAll(newTags);
        }

        return existingTags.stream()
                .map(HashtagMap::new).collect(Collectors.toSet());
    }

    @Transactional
    public void postDelete(Long postId, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXIST));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_EXIST));

        if (!(member.getId().equals(post.getMember().getId()) || member.getRole() == Role.ADMIN)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        postRepository.delete(post);
    }

    @Transactional
    public void postUpdate(PostRequest request, Long postId, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXIST));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_EXIST));

        if (!member.getId().equals(post.getMember().getId())) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        postRepository.save(replacePost(post, request));
    }

    private Post replacePost(Post post, PostRequest request) {
        post.setContent(request.content());
        post.setTitle(request.title());

        Set<HashtagMap> hashtagMaps = handlingHashtag(request.hashtag());
        post.getHashtagMaps().clear();
        post.getHashtagMaps().addAll(hashtagMaps);

        hashtagMaps.forEach(hm->hm.setPost(post));

        return post;
    }
}
