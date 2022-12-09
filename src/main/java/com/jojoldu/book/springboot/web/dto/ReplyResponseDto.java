package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.reply.Reply;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
public class ReplyResponseDto {
    private Long id;
    private Long pid;
    private Long uid;
    private String name;
    private String replyContent;
    private String modifiedDate;

    public ReplyResponseDto(Reply reply){
        this.id = reply.getId();
        this.pid = reply.getPost().getId();
        this.uid = reply.getUser().getId();
        this.name = reply.getUser().getName();
        this.replyContent = reply.getReplyContent();
        this.modifiedDate = reply.getModifiedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
