package com.example.bloghw2.post.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bloghw2.PostSetup;
import com.example.bloghw2.UserSetup;
import com.example.bloghw2.jwtutil.JwtProvider;
import com.example.bloghw2.post.dto.PostRequestDTO;import com.example.bloghw2.post.entity.Post;
import com.example.bloghw2.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostSetup postSetup;

    @Autowired
    UserSetup userSetup;

    @Autowired
    JwtProvider jwtProvider;

    @BeforeEach
    public void beforeEach() {
        postSetup.clearPosts();
        userSetup.clearUsers();
    }

    private String mockLogin(String userName) {
        User user = User.builder()
            .username(userName)
            .password("userPassword")
            .build();
        userSetup.saveUser(user);
        return jwtProvider.createToken(user.getUsername());
    }

    @DisplayName("게시글 작성에 성공한다.")
    @Test
    void CreateTest_O() throws Exception {
        //given
        String accessToken = mockLogin("username1");
        PostRequestDTO postRequestDTO = new PostRequestDTO("title", "contents");
        String request = objectMapper.writeValueAsString(postRequestDTO);
        String username = jwtProvider.getUserInfoFromToken(jwtProvider.substringToken(accessToken)).getSubject();
        //when
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(JwtProvider.AUTHORIZATION_HEADER, accessToken))

            //then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value(postRequestDTO.getTitle()))
            .andExpect(jsonPath("$.contents").value(postRequestDTO.getContents()))
            .andExpect(jsonPath("$.author").value(username))
            .andDo(print());

    }

    @DisplayName("게시글 작성에 실패한다 - JWT 누락")
    @Test
    void createPosts_X() throws Exception {
        //given
        mockLogin("username1");
        PostRequestDTO postRequestDTO = new PostRequestDTO("title", "contents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

            //then
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("게시글 작성에 실패한다 - Invalid Token")
    @Test
    void createPosts_X2() throws Exception {
        //given
        String accessToken = mockLogin("username1");

        String InvalidToken = accessToken + "abc";
        PostRequestDTO postRequestDTO = new PostRequestDTO("title", "contents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(JwtProvider.AUTHORIZATION_HEADER, InvalidToken))
            //then
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("게시글 작성에 실패한다 - Expired Token")
    @Test
    void createPosts_X3() throws Exception {
        //given
        mockLogin("username1");
        String expiredToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZTEiLCJleHAiOjE2ODgwMjE5ODksImlhdCI6MTY4ODAxODM4OX0.50O_HuH5Vl_tjMbvmh5h3ztYLtHwK4MJLPXsLwwUd6o";
        PostRequestDTO postRequestDTO = new PostRequestDTO("title", "contents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(JwtProvider.AUTHORIZATION_HEADER, expiredToken))
            //then
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("게시글 작성에 실패한다 - 회원가입 되지 않은 유저")
    @Test
    void createPosts_X4() throws Exception {
        //given
        String accessToken = mockLogin("username1");
        userSetup.clearUsers();
        PostRequestDTO postRequestDTO = new PostRequestDTO("title", "contents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(JwtProvider.AUTHORIZATION_HEADER, accessToken))
            //then
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("게시글 수정에 성공한다")
    @Test
    void modifyPosts_O() throws Exception {
        //given
        String accessToken = mockLogin("username1");
        User user = userSetup.findUser("username1");

        Post savedPost = postSetup.savePosts(user);
        PostRequestDTO postRequestDTO = new PostRequestDTO("modifiedTitle", "modifiedContents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(put("/api/posts/{postId}", savedPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtProvider.AUTHORIZATION_HEADER, accessToken)
                .content(request))

            //then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value(postRequestDTO.getTitle()))
            .andExpect(jsonPath("$.contents").value(postRequestDTO.getContents()))
            .andDo(print());
    }

    @DisplayName("게시글 수정에 실패한다 - 권한 없음")
    @Test
    void modifyPost_X() throws Exception {
        //given
        String accessToken = mockLogin("username1");
        User diffUser = User.builder()
            .username("diffUser")
            .password("diffPassword")
            .build();
        userSetup.saveUser(diffUser);
        Post otherUserPost = postSetup.savePosts(diffUser);
        PostRequestDTO postRequestDTO = new PostRequestDTO("modifiedTitle", "modifiedContents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(put("/api/posts/{postId}", otherUserPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtProvider.AUTHORIZATION_HEADER, accessToken)
                .content(request))

            //then
            .andExpect(status().isForbidden())
            .andDo(print());
    }

    @DisplayName("게시글 삭제에 성공한다")
    @Test
    void deletePosts_O() throws Exception {
        //given
        String accessToken = mockLogin("username1");
        User user = userSetup.findUser("username1");

        Post savedPost = postSetup.savePosts(user);
        PostRequestDTO postRequestDTO = new PostRequestDTO("modifiedTitle", "modifiedContents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(delete("/api/posts/{postId}", savedPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtProvider.AUTHORIZATION_HEADER, accessToken)
                .content(request))

            //then
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("게시글 삭제에 실패한다 - 권한 없음")
    @Test
    void deletePosts_X() throws Exception {
        //given
        String accessToken = mockLogin("username1");
        User diffUser = User.builder()
            .username("diffUser")
            .password("diffPassword")
            .build();
        userSetup.saveUser(diffUser);
        Post otherUserPost = postSetup.savePosts(diffUser);
        PostRequestDTO postRequestDTO = new PostRequestDTO("modifiedTitle", "modifiedContents");
        String request = objectMapper.writeValueAsString(postRequestDTO);

        //when
        mockMvc.perform(delete("/api/posts/{postId}", otherUserPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtProvider.AUTHORIZATION_HEADER, accessToken)
                .content(request))

            //then
            .andExpect(status().isForbidden())
            .andDo(print());
    }
}
