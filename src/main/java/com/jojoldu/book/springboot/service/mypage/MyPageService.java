package com.jojoldu.book.springboot.service.mypage;

import com.jojoldu.book.springboot.domain.mypage.MyPageRepository;
import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;

    //회원 정보 수정하기

    //회원 탈퇴하기
    @Transactional
    public void delete(Long id){
//        My posts = myPageRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
//        myPageRepository.delete(posts);
    }
}
