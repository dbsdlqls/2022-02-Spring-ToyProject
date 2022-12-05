package com.jojoldu.book.springboot.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("SELECT r FROM Reply r WHERE r.post.id = :pid")
    List<Reply> findAllByPid(@Param("pid") Long pid);

    @Query("SELECT r FROM Reply r WHERE r.user.id = :uid")
    List<Reply> findAllByUid(@Param("uid") Long uid);
}
