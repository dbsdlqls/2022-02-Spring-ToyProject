package com.jojoldu.book.springboot.domain.Heart;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Heart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="post_id", referencedColumnName = "id")
    private Posts post;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Builder
    public Heart(Posts post, User user){
        this.post = post;
        this.user = user;
    }
}
