package com.jojoldu.book.springboot.domain.reply;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.domain.user.Role;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        replyRepository.deleteAll();
    }

    @Test
    public void 댓글저장_불러오기(){
        //given
        User user = User.builder()
                .name("dbsdlqls")
                .email("test email")
                .picture("none")
                .role(Role.USER)
                .build();
        Long uid = userRepository.save(user).getId();
        Posts post = Posts.builder()
                .title("test")
                .author("테스트계정")
                .content("first post content")
                .build();
        Long pid = postsRepository.save(post).getId();
        String replyContent = "reply content";

        replyRepository.save(Reply.builder()
                .user(user)
                .post(post)
                .replyContent(replyContent)
                .build()
        );

        //when
        List<Reply> replyList = replyRepository.findAll();

        //then
        Reply reply = replyList.get(0);
        System.out.println("new reply id"+reply.getId());
        assertThat(reply.getReplyContent()).isEqualTo(replyContent);
        assertThat(reply.getUser().getId()).isEqualTo(uid);
        assertThat(reply.getPost().getId()).isEqualTo(pid);
        assertThat(reply.getUser().getEmail()).isEqualTo("test email");
        assertThat(reply.getUser().getPicture()).isEqualTo("none");
        assertThat(reply.getPost().getContent()).isEqualTo("first post content");
    }
}
