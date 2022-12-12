package com.jojoldu.book.springboot.domain.Heart;

import com.jojoldu.book.springboot.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByPost(Posts post);

    boolean existsByPost(Posts posts);

    @Query("SELECT r FROM Heart r WHERE r.user.id = :uid")
    List<Heart> findAllByUid(@Param("uid") Long uid);

}
