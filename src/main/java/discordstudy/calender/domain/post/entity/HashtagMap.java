package discordstudy.calender.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class HashtagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "hashtagId")
    private Hashtag hashtag;
}