package org.mega.book.springboot.service;

import lombok.RequiredArgsConstructor;
import org.mega.book.springboot.domain.posts.Posts;
import org.mega.book.springboot.domain.posts.PostsRepository;
import org.mega.book.springboot.web.dto.PostsListResponseDto;
import org.mega.book.springboot.web.dto.PostsResponseDto;
import org.mega.book.springboot.web.dto.PostsSaveRequestDto;
import org.mega.book.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //읽어오기만
    public List<PostsListResponseDto> findAllDesc(){//자료형 바꾸기 용이하게 하려고, stream으로 바꿔요~ (Posts->Object(dto))
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }   //postsListResponseDto로 바꾸는 방식이에요.
}
