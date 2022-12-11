package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.heart.HeartService;
import com.jojoldu.book.springboot.web.dto.HeartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class HeartController {

    private final HeartService heartService;

    @PostMapping
    public Long insertHeart(@RequestBody HeartDto heartDto) throws IOException {
        return heartService.insertHeart(heartDto);
    }

    @DeleteMapping
    public Long deleteHeart(@RequestBody HeartDto heartDto) {
        return heartService.deleteHeart(heartDto);
    }

}
