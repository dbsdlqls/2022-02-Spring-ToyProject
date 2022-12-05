package com.jojoldu.book.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplySaveRequestDto {
    private Long pid;
    private String email;
    private String replyContent;

    @Builder
    public ReplySaveRequestDto(Long pid, String email, String replyContent){
        this.pid = pid;
        this.email = email;
        this.replyContent = replyContent;
    }
}
