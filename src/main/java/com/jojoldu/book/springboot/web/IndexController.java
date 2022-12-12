package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.Heart.Heart;
import com.jojoldu.book.springboot.service.heart.HeartService;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.service.reply.ReplyService;
import com.jojoldu.book.springboot.web.dto.HeartResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.ReplyResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final ReplyService replyService;

    private  final HeartService heartService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/getDetail/{id}")
    public String postsDetail(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        List<ReplyResponseDto> replies = replyService.findAllByPost(id);
        Optional<Heart> heart = heartService.checkExistHeart(id);

        model.addAttribute("post", dto);
        model.addAttribute("user", user);
        model.addAttribute("replies", replies);

        if (heart.isPresent()) {
            model.addAttribute("heart", heart);
        }
        return "posts-detail";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/mypage")
    public String myPage(Model model, @LoginUser SessionUser user){
        model.addAttribute("user", user);

        return "mypage";
    }

    @GetMapping("/mypage/reply")
    public String myReply(Model model, @LoginUser SessionUser user){
        List<ReplyResponseDto> replies = replyService.findAllByUser(user.getEmail());
        model.addAttribute("replies", replies);

        return "mypage-reply";
    }
    @GetMapping("/mypage/like")
    public String myLike(Model model, @LoginUser SessionUser user){
        List<HeartResponseDto> hearts = heartService.findAllByUser(user.getEmail());
        model.addAttribute("hearts", hearts);

        return "mypage-like";
    }
}
