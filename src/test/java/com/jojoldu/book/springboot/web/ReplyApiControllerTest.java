package com.jojoldu.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.domain.reply.Reply;
import com.jojoldu.book.springboot.domain.reply.ReplyRepository;
import com.jojoldu.book.springboot.domain.user.Role;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import com.jojoldu.book.springboot.web.dto.ReplySaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplyApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        replyRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Reply_등록된다() throws Exception{
        //given
        String email = userRepository.save(User.builder()
                .name("dbsdlqls")
                .email("test email")
                .picture("none")
                .role(Role.USER)
                .build()
        ).getEmail();

        Long pid = postsRepository.save(Posts.builder()
                .title("test")
                .author("테스트 계정")
                .content("reply api controller: test post content")
                .build()
        ).getId();

        String replyContent = "reply content";
        ReplySaveRequestDto requestDto = ReplySaveRequestDto.builder()
                .pid(pid)
                .email(email)
                .replyContent(replyContent)
                .build();
        String url = "http://localhost:" + port + "/api/v1/reply";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Reply> all = replyRepository.findAll();
        Reply reply = all.get(0);

        assertThat(reply.getPost().getId()).isEqualTo(pid);
        assertThat(reply.getUser().getEmail()).isEqualTo("test email");
        assertThat(reply.getUser().getName()).isEqualTo("dbsdlqls");
        assertThat(reply.getPost().getContent()).isEqualTo("reply api controller: test post content");
        assertThat(reply.getReplyContent()).isEqualTo("reply content");
    }
}
