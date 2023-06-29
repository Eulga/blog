package com.example.bloghw2.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.example.bloghw2.UserSetup;
import com.example.bloghw2.user.dto.BaseResponseDTO;
import com.example.bloghw2.user.dto.UserRequestDTO;
import com.example.bloghw2.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserSetup userSetup;

    @BeforeEach
    public void beforeEach() {
        userSetup.clearUsers();
    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void signUp_O() throws Exception{
        UserRequestDTO userRequestDTO = new UserRequestDTO("username1","password1");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("true",201);
        String request = objectMapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(post("/api/signup")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("회원가입에 실패한다. - 중복된 Username 존재")
    void signUp_X_1() throws Exception{
        UserRequestDTO userRequestDTO = new UserRequestDTO("username1","password1");
        User existingUser = User.builder()
            .username(userRequestDTO.getUsername())
            .password(userRequestDTO.getPassword()).build();
        userSetup.saveUser(existingUser);
        UserRequestDTO newUserRequestDTO = new UserRequestDTO("username1","diffPassword");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("false",409);
        String request = objectMapper.writeValueAsString(newUserRequestDTO);

        mockMvc.perform(post("/api/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("회원가입에 실패한다. - userName은 최소 4자 이상")
    void signUp_X_2() throws Exception{

        UserRequestDTO userRequestDTO = new UserRequestDTO("asd","password");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("false",400);
        String request = objectMapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(post("/api/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("회원가입에 실패한다. - userName은 최대 10자 이하")
    void signUp_X_3() throws Exception{

        UserRequestDTO userRequestDTO = new UserRequestDTO("qwerasdfzxcv","password");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("false",400);
        String request = objectMapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(post("/api/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("회원가입에 실패한다. - userName은 소문자, 숫자로 구성")
    void signUp_X_4() throws Exception {

        UserRequestDTO userRequestDTO = new UserRequestDTO("abAb!@12","password");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("false",400);
        String request = objectMapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(post("/api/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("회원가입에 실패한다. - password는 최소 8자 이상")
    void signUp_X_5() throws Exception{

        UserRequestDTO userRequestDTO = new UserRequestDTO("username","passwor");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("false",400);
        String request = objectMapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(post("/api/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("회원가입에 실패한다. - password는 최대 15자 이하")
    void signUp_X_6() throws Exception{

        UserRequestDTO userRequestDTO = new UserRequestDTO("username","passwordP1235678");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("false",400);
        String request = objectMapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(post("/api/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("회원가입에 실패한다. - password는 대문자, 소문자, 숫자로 구성")
    void signUp_X_7() throws Exception {

        UserRequestDTO userRequestDTO = new UserRequestDTO("username","password12PA!#");
        BaseResponseDTO expectedResponse = new BaseResponseDTO("false",400);
        String request = objectMapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(post("/api/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(expectedResponse.getSuccess()))
            .andExpect(jsonPath("$.status").value(expectedResponse.getStatus()))
            .andDo(MockMvcResultHandlers.print());
    }
}
