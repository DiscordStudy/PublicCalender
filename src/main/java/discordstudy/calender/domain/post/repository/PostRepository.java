package discordstudy.calender.domain.post.repository;

import discordstudy.calender.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
