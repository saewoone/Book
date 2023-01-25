package org.mega.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor //final, notNull 객체
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
