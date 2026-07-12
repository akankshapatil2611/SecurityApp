package com.security.SecurityApp.service;

import com.security.SecurityApp.dto.PostDTO;
import com.security.SecurityApp.entity.PostEntity;
import com.security.SecurityApp.entity.User;
import com.security.SecurityApp.exception.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service @RequiredArgsConstructor
public class PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    public PostDTO createNewPost(PostDTO inputPost) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    public PostDTO getPostById(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("user {}", user);

        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id "+postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }
}
