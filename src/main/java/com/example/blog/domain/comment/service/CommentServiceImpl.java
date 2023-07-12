package com.example.blog.domain.comment.service;

import com.example.blog.domain.comment.dto.CommentRequestDTO;
import com.example.blog.domain.comment.dto.CommentResponseDTO;
import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.comment.exception.CommentNotFoundException;
import com.example.blog.domain.comment.repository.CommentLikeRepository;
import com.example.blog.domain.comment.repository.CommentRepository;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.exception.PostNotFoundException;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.entity.UserRoleEnum;
import com.example.blog.domain.user.exception.UserNotFoundException;
import com.example.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "CommentService")
@RequiredArgsConstructor
@Service
//@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    private final MessageSource messageSource;

    // 해당 게시글 댓글 조회
    @Override
    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );
        List<Comment> commentList = commentRepository.findAllByPostOrderByCreatedDateDesc(post);

        return commentList.stream()
                .map(CommentResponseDTO::new)
                .toList();
    }

    // 댓글 작성
    @Transactional
    @Override
    public CommentResponseDTO createComment(CommentRequestDTO crqd, String username) {
        log.info(username + " 댓글 작성 post_id: " + crqd.getPostId());
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

    // 댓글 수정
    @Transactional
    @Override
    public CommentResponseDTO modifyComment(Long commentId, CommentRequestDTO commentRequestDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("Not Found Comment")
        );

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(comment.getUser().getId().equals(user.getId()))) {
                throw new IllegalArgumentException();
            }
        }
        if (validationAuthority(user, comment)) {
            comment.modifyComment(commentRequestDTO.getContent());
        } else {
            throw new IllegalArgumentException();
        }

        // 댓글 좋아요 수
        comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId()));

        return new CommentResponseDTO(comment);
    }

    // 댓글 삭제
    @Transactional
    @Override
    public Map<String, String> deleteComment(Long commentId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("Not Found Comment")
        );

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(comment.getUser().getId().equals(user.getId()))) {
                throw new IllegalArgumentException();
            }
        }
        if (validationAuthority(user, comment)) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException();
        }


        return new LinkedHashMap<>() {{
            put("success", "true");
            put("status", "200");
        }};
    }

    // 수정, 삭제시 권한 확인
    private boolean validationAuthority(User user, Comment comment) {
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!user.getUsername().equals(comment.getUser().getUsername())) {
                return false;
            }
        }
        return true;
    }
}
