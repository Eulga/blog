package com.example.bloghw2.comment.service;

import com.example.bloghw2.comment.dto.CommentRequestDTO;
import com.example.bloghw2.comment.dto.CommentResponseDTO;
import com.example.bloghw2.comment.entity.Comment;
import com.example.bloghw2.comment.repository.CommentRepository;
import com.example.bloghw2.post.entity.Post;
import com.example.bloghw2.post.exception.PostNotFoundException;
import com.example.bloghw2.post.repository.PostRepository;
import com.example.bloghw2.user.entity.User;
import com.example.bloghw2.user.exception.UserNotFoundException;
import com.example.bloghw2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j(topic = "CommentService")
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );
        List<Comment> commentList = commentRepository.findAllByPost(post);

        return commentList.stream()
                .map(CommentResponseDTO::new)
                .toList();
    }

    @Transactional
    @Override
    public CommentResponseDTO createComment(CommentRequestDTO crqd, String username) {
        log.info(username + "게시글 작성");
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );
        Post post = postRepository.findById(crqd.getPostId()).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(crqd.getContent())
                .build();

        return new CommentResponseDTO(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentResponseDTO modifyComment(Long commentId, CommentRequestDTO commentRequestDTO, String username) {
        return null;
    }

    @Transactional
    @Override
    public Map<String, String> deleteComment(Long commentId, String username) {
        return null;
    }
}
