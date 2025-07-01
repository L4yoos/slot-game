//package com.example.slot.domain;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import java.util.List;
//import java.util.Random;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class SlotMachineFacadeTest {
//
//    private Random alwaysZeroRandom;
//    private Random incrementingRandom;
//
//    private SlotMachineFacade facadeWithAlwaysZeroRandom;
//    private SlotMachineFacade facadeWithIncrementingRandom;
//
//    @BeforeEach
//    void setUp() {
//        alwaysZeroRandom = new Random() {
//            @Override
//            public int nextInt(int bound) {
//                return 0;
//            }
//        };
//        facadeWithAlwaysZeroRandom = new SlotMachineFacade(alwaysZeroRandom);
//
//        incrementingRandom = new Random() {
//            private int callCount = 0;
//
//            @Override
//            public int nextInt(int bound) {
//                return callCount++ % bound;
//            }
//        };
//        facadeWithIncrementingRandom = new SlotMachineFacade(incrementingRandom);
//    }
//
//
//    @Test
//    void shouldReturnWinWhenThreeIdenticalSymbols() {
//        SpinResultDTO result = facadeWithAlwaysZeroRandom.spin(100);
//
//        assertThat(result.isWin()).isTrue();
//        assertThat(result.getSymbols()).hasSize(3);
//        assertThat(result.getSymbols().get(0)).isEqualTo(result.getSymbols().get(1));
//        assertThat(result.getSymbols().get(1)).isEqualTo(result.getSymbols().get(2));
//        assertThat(result.getPayout()).isGreaterThan(0);
//    }
//
//    @Test
//    void shouldReturnWinWhenThreeIdenticalSymbolsOnAutoSpin() {
//        List<SpinResultDTO> results = facadeWithAlwaysZeroRandom.autospin(100, 10);
//
//        assertThat(results).hasSize(10);
//
//        for (SpinResultDTO result : results) {
//            assertThat(result.isWin()).isTrue();
//            assertThat(result.getSymbols()).hasSize(3);
//            assertThat(result.getSymbols().get(0)).isEqualTo(result.getSymbols().get(1));
//            assertThat(result.getSymbols().get(1)).isEqualTo(result.getSymbols().get(2));
//            assertThat(result.getPayout()).isGreaterThan(0);
//        }
//    }
//
//    @Test
//    void shouldReturnLossWhenSymbolsAreDifferent() {
//        SpinResultDTO result = facadeWithIncrementingRandom.spin(100);
//
//        assertThat(result.isWin()).isFalse();
//        assertThat(result.getSymbols()).hasSize(3);
//        assertThat(result.getPayout()).isEqualTo(0);
//    }
//
//    @Test
//    void shouldReturnLossWhenSymbolsAreDifferentOnAutoSpin() {
//        List<SpinResultDTO> results = facadeWithIncrementingRandom.autospin(100, 5);
//
//        assertThat(results).hasSize(5);
//
//        for (SpinResultDTO result : results) {
//            assertThat(result.isWin()).isFalse();
//            assertThat(result.getSymbols()).hasSize(3);
//            assertThat(result.getPayout()).isEqualTo(0);
//        }
//    }
//
//    @Test
//    void shouldCalculatePayoutCorrectly() {
//        SpinResultDTO result = facadeWithAlwaysZeroRandom.spin(50);
//
//        assertThat(result.isWin()).isTrue();
//
//        Symbol firstSymbol = Symbol.values()[0];
//        int expectedPayout = firstSymbol.getPayout() * 50;
//        assertThat(result.getPayout()).isEqualTo(expectedPayout);
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {1, 10, 50, 100, 500})
//    void shouldHandleDifferentBetAmounts(int betAmount) {
//        SpinResultDTO result = facadeWithAlwaysZeroRandom.spin(betAmount);
//
//        assertThat(result.isWin()).isTrue();
//        assertThat(result.getPayout()).isEqualTo(Symbol.values()[0].getPayout() * betAmount);
//    }
//
//    @Test
//    void shouldGenerateExactlyThreeSymbols() {
//        SpinResultDTO result = facadeWithIncrementingRandom.spin(100);
//
//        assertThat(result.getSymbols()).hasSize(3);
//        assertThat(result.getSymbols()).allMatch(symbol -> symbol != null);
//    }
//
//    @Test
//    void shouldReturnOnlyValidSymbols() {
//        SpinResultDTO result = facadeWithAlwaysZeroRandom.spin(100);
//
//        Symbol[] validSymbols = Symbol.values();
//        assertThat(result.getSymbols()).allMatch(symbol ->
//                java.util.Arrays.asList(validSymbols).contains(symbol)
//        );
//    }
//
//    @Test
//    void shouldThrowExceptionHandleZeroBet() {
//        assertThatThrownBy(() -> facadeWithAlwaysZeroRandom.spin(0))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("Bet must be greater than zero");
//    }
//
//    @Test
//    void shouldThrowExceptionHandleZeroSpin() {
//        assertThatThrownBy(() -> facadeWithAlwaysZeroRandom.autospin(5, 0))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("Spin count must be greater than zero");
//    }
//
//    @Test
//    void shouldBeDeterministicWithSameSeed() {
//        SpinResultDTO result1 = facadeWithAlwaysZeroRandom.spin(100);
//        SpinResultDTO result2 = facadeWithAlwaysZeroRandom.spin(100);
//
//        assertThat(result1.getSymbols()).isEqualTo(result2.getSymbols());
//        assertThat(result1.isWin()).isEqualTo(result2.isWin());
//        assertThat(result1.getPayout()).isEqualTo(result2.getPayout());
//    }
//
//    @Test
//    void shouldHandleNegativeBetAmounts() {
//        assertThatThrownBy(() -> facadeWithIncrementingRandom.spin(-100))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("Bet must be greater than zero");
//    }
//
//    @Test
//    void shouldHandleNegativeSpinAmounts() {
//        assertThatThrownBy(() -> facadeWithIncrementingRandom.autospin(100, -100))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("Spin count must be greater than zero");
//    }
//}
