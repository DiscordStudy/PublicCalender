package discordstudy.calender.domain.post.repository;

import discordstudy.calender.domain.post.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findByTagIn(Set<String> tags);
}
