//package com.example.slot.application;
//
//import com.example.slot.domain.SlotMachinePort;
//import com.example.slot.domain.SpinResultDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class SlotServiceTest {
//
//    @Mock
//    private SlotMachinePort slotMachinePort;
//
//    private SlotService slotService;
//
//    @BeforeEach
//    void setUp() {
//        slotService = new SlotService(slotMachinePort);
//    }
//
//    @Test
//    void shouldPlayWithValidBetOnSpin() {
//        SpinResultDTO expectedResult = mock(SpinResultDTO.class);
//        when(slotMachinePort.spin(10)).thenReturn(expectedResult);
//
//        SpinResultDTO result = slotService.play(10);
//
//        assertNotNull(result);
//        assertEquals(expectedResult, result);
//        verify(slotMachinePort, times(1)).spin(10);
//    }
//
//    @Test
//    void shouldPlayWithValidBetOnAutoSpin() {
//        SpinResultDTO expectedResult = mock(SpinResultDTO.class);
//        List<SpinResultDTO> resultList = List.of(expectedResult);
//
//        when(slotMachinePort.autospin(10, 10)).thenReturn(resultList);
//
//        List<SpinResultDTO> result = slotService.autoSpin(10,10);
//
//        assertNotNull(result);
//        assertEquals(resultList, result);
//        verify(slotMachinePort, times(1)).autospin(10, 10);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenBetIsZeroOnSpin() {
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> slotService.play(0)
//        );
//
//        assertEquals("Bet must be greater than zero", exception.getMessage());
//        verify(slotMachinePort, never()).spin(anyInt());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenSpinIsZeroOnAutoSpin() {
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> slotService.autoSpin(5, 0)
//        );
//
//        assertEquals("Spin count must be greater than zero", exception.getMessage());
//        verify(slotMachinePort, never()).autospin(anyInt(), anyInt());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenBetIsNegative() {
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> slotService.play(-5)
//        );
//
//        assertEquals("Bet must be greater than zero", exception.getMessage());
//        verify(slotMachinePort, never()).spin(anyInt());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenSpinIsNegative() {
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> slotService.autoSpin(5, -5)
//        );
//
//        assertEquals("Spin count must be greater than zero", exception.getMessage());
//        verify(slotMachinePort, never()).autospin(anyInt(), anyInt());
//    }
//
//    @Test
//    void shouldCallPortWithCorrectBetAmount() {
//        SpinResultDTO mockResult = mock(SpinResultDTO.class);
//        when(slotMachinePort.spin(25)).thenReturn(mockResult);
//
//        slotService.play(25);
//
//        verify(slotMachinePort).spin(25);
//    }
//
//    @Test
//    void shouldCallPortWithCorrectBetAmountAndSpin() {
//        SpinResultDTO mockResult = mock(SpinResultDTO.class);
//        List<SpinResultDTO> resultList = List.of(mockResult);
//
//        when(slotMachinePort.autospin(25, 10)).thenReturn(resultList);
//
//        slotService.autoSpin(25,10);
//
//        verify(slotMachinePort).autospin(25, 10);
//    }
//}