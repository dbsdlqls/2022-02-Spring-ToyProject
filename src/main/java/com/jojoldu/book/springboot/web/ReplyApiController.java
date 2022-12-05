package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.reply.ReplyService;
import com.jojoldu.book.springboot.web.dto.ReplyResponseDto;
import com.jojoldu.book.springboot.web.dto.ReplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reply")
@RestController
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping
    public Long save(@RequestBody ReplySaveRequestDto requestDto){
        return replyService.save(requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id, @RequestParam String email){
        System.out.println("rid: "+id+" email:"+email);
        replyService.delete(id, email);
        return id;
    }

    @GetMapping("/user/{id}")
    public List<ReplyResponseDto> findByUser(@PathVariable Long id){
        return replyService.findAllByUser(id);
    }
}
