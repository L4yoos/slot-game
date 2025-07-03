//package com.example.slot.adapters.web;
//
//import com.example.slot.application.SlotService;
//import com.example.slot.application.dto.SpinResultDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(SlotController.class)
//class SlotControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private SlotService slotService;
//
//    @Test
//    void shouldReturnSpinResultWhenValidRequest() throws Exception {
//        SpinRequest request = new SpinRequest(10);
//        SpinResultDTO expectedResult = mock(SpinResultDTO.class);
//        when(slotService.play(10)).thenReturn(expectedResult);
//
//        mockMvc.perform(post("/api/slot/spin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.symbols").exists())
//                .andExpect(jsonPath("$.win").exists())
//                .andExpect(jsonPath("$.payout").exists());
//
//        verify(slotService, times(1)).play(10);
//    }
//
//    @Test
//    void shouldReturnAutoSpinResultWhenValidRequest() throws Exception {
//        AutoSpinRequest request = new AutoSpinRequest(10, 10);
//
//        SpinResultDTO spinResultMock = mock(SpinResultDTO.class);
//        List<SpinResultDTO> expectedResult = List.of(spinResultMock);
//
//        when(slotService.autoSpin(10, 10)).thenReturn(expectedResult);
//
//        mockMvc.perform(post("/api/slot/autospin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].symbols").exists())
//                .andExpect(jsonPath("$[0].win").exists())
//                .andExpect(jsonPath("$[0].payout").exists());
//
//        verify(slotService, times(1)).autoSpin(10, 10);
//    }
//
//    @Test
//    void shouldReturn400WhenBetIsZero() throws Exception {
//        SpinRequest request = new SpinRequest(0);
//
//        mockMvc.perform(post("/api/slot/spin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn400WhenBetAndSpinIsZero() throws Exception {
//        AutoSpinRequest request = new AutoSpinRequest(0, 0);
//
//        mockMvc.perform(post("/api/slot/autospin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn400WhenBetIsNegative() throws Exception {
//        SpinRequest request = new SpinRequest(-5);
//
//        mockMvc.perform(post("/api/slot/spin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn400WhenBetAndSpinIsNegative() throws Exception {
//        AutoSpinRequest request = new AutoSpinRequest(-5, -5);
//
//        mockMvc.perform(post("/api/slot/autospin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn400WhenRequestBodyIsEmptyForSpin() throws Exception {
//        mockMvc.perform(post("/api/slot/spin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn400WhenRequestBodyIsEmptyForAutoSpin() throws Exception {
//        mockMvc.perform(post("/api/slot/autospin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn400WhenRequestBodyIsInvalidForSpin() throws Exception {
//        mockMvc.perform(post("/api/slot/spin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"invalidField\": \"value\"}"))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn400WhenRequestBodyIsInvalidForAutoSpin() throws Exception {
//        mockMvc.perform(post("/api/slot/autospin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"invalidField\": \"value\"}"))
//                .andExpect(status().isBadRequest());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn415WhenContentTypeIsNotJsonForSpin() throws Exception {
//        SpinRequest request = new SpinRequest(10);
//
//        mockMvc.perform(post("/api/slot/spin")
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnsupportedMediaType());
//
//        verify(slotService, never()).play(anyInt());
//    }
//
//    @Test
//    void shouldReturn415WhenContentTypeIsNotJsonForAutoSpin() throws Exception {
//        AutoSpinRequest request = new AutoSpinRequest(10, 10);
//
//        mockMvc.perform(post("/api/slot/autospin")
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnsupportedMediaType());
//
//        verify(slotService, never()).play(anyInt());
//    }
//}