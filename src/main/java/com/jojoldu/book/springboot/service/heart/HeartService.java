package com.jojoldu.book.springboot.service.heart;

import com.jojoldu.book.springboot.domain.Heart.Heart;
import com.jojoldu.book.springboot.domain.Heart.HeartRepository;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import com.jojoldu.book.springboot.web.dto.HeartDto;
import com.jojoldu.book.springboot.web.dto.HeartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    /**
     * 좋아요를 눌렀을 때 좋아요 반영
     */
    @Transactional
    public Long insertHeart(HeartDto heartDto) throws IOException {
        Posts posts = postsRepository.findById(heartDto.getPid())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + heartDto.getPid()));

        User user = userRepository.findByEmail(heartDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + heartDto.getEmail()));


        Heart heart = Heart.builder()
                .post(posts)
                .user(user)
                .build();

        return heartRepository.save(heart).getId();
    }

    public Long deleteHeart(HeartDto heartDto){
        System.out.println("==============HeartService.deleteHeart");
        System.out.println("heartDto.getPid() = " + heartDto.getPid());
        System.out.println("heartDto.getEmail() = " + heartDto.getEmail());

        Posts post = postsRepository.findById(heartDto.getPid())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + heartDto.getPid()));

        Heart heart = heartRepository.findByPost(post)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다"));

        Long deleteHeartId = heart.getId();

        if(heartDto.getEmail().equals(heart.getUser().getEmail())){
            heartRepository.delete(heart);
        }
        else{
            throw new RuntimeException("사용자가 누른 하트만 취소가능.");
        }
        return deleteHeartId;
    }

    public List<HeartResponseDto> findAllByUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email="+email));

        return heartRepository.findAllByUid(user.getId())
                .stream()
                .map(HeartResponseDto::new)
                .collect(Collectors.toList());
    }

    public Optional<Heart> checkExistHeart(Long pid){
        Posts post = postsRepository.findById(pid)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + pid));

        if (!heartRepository.existsByPost(post)) {
            return Optional.empty();
        }
        return heartRepository.findByPost(post);
    }
}
