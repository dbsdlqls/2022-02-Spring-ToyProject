package com.jojoldu.book.springboot.service.reply;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.domain.reply.Reply;
import com.jojoldu.book.springboot.domain.reply.ReplyRepository;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import com.jojoldu.book.springboot.web.dto.ReplyResponseDto;
import com.jojoldu.book.springboot.web.dto.ReplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(ReplySaveRequestDto requestDto){
        Posts post = postsRepository.findById(requestDto.getPid())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+requestDto.getPid()));
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ExpressionException("해당 사용자가 없습니다. email="+requestDto.getEmail()));
        Reply reply = Reply.builder()
                .post(post)
                .user(user)
                .replyContent(requestDto.getReplyContent())
                .build();
        return replyRepository.save(reply).getId();
    }

    @Transactional
    public void delete(Long id, String email){
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id="+id));
        System.out.println("email: "+email+"getEmail: "+reply.getUser().getEmail());
        if(email.equals(reply.getUser().getEmail())){
            replyRepository.delete(reply);
        }
        else{
            throw new RuntimeException("사용자가 작성한 댓글만 삭제할 수 있습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<ReplyResponseDto> findAllByPost(Long pid){
        return replyRepository.findAllByPid(pid)
                .stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ReplyResponseDto> findAllByUser(Long uid){
        return replyRepository.findAllByUid(uid)
                .stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList());
    }
}
