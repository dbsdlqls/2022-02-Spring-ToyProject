package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.Heart.Heart;
import com.jojoldu.book.springboot.domain.reply.Reply;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
public class HeartResponseDto {
    private Long id;
    private Long pid;
    private Long uid;
    private String name;
    private String modifiedDate;

    public HeartResponseDto(Heart heart){
        this.id = heart.getId();
        this.pid = heart.getPost().getId();
        this.uid = heart.getUser().getId();
        this.name = heart.getUser().getName();
        this.modifiedDate = heart.getModifiedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
