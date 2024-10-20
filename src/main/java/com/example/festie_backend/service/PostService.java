package com.example.festie_backend.service;

import com.example.festie_backend.model.Post;
import com.example.festie_backend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(Post user) {
        return postRepository.save(user);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
