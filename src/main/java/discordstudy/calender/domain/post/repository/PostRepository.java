package discordstudy.calender.domain.post.repository;

import discordstudy.calender.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"hashtagMaps", "hashtagMaps.hashtag"})
    Page<Post> findAll(Pageable pageable);
}
