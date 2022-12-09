package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.Heart.Heart;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartDto {
    private Long pid;
    private String email;

    @Builder
    public HeartDto(Heart heart){
        this.pid = heart.getPost().getId();
        this.email = heart.getUser().getEmail();
    }
}
