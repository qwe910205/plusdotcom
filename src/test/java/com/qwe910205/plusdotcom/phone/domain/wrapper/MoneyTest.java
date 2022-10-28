package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1000, Integer.MAX_VALUE})
    @DisplayName("돈을 0이나 자연수로 생성할 수 있다.")
    void newPrice(int price) {
        assertThatCode(() -> new Money(price)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("돈을 음수로 생성하면 예외가 발생한다.")
    void newPrice() {
        int price = -1;

        assertThatThrownBy(() -> new Money(price)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("돈을 더했을 때 오버플로우가 발생하면 예외가 발생한다.")
    void plus() {
        Money money1 = new Money(Integer.MAX_VALUE);
        Money money2 = new Money(Integer.MAX_VALUE);

        assertThatThrownBy(() -> money1.plus(money2)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("돈을 뺄 때 빼는 돈의 양이 더 크면 0원을 반환한다.")
    void minus() {
        Money money1 = new Money(0);
        Money money2 = new Money(Integer.MAX_VALUE);

        Money newMoney = money1.minus(money2);

        assertThat(newMoney.getValue()).isZero();
    }

    @Test
    @DisplayName("돈을 나눌 때 나누는 값이 0이면 나뉘어지지 않는다.")
    void divide() {
        Money money = new Money(10000);

        Money dividedMoney = money.divide(0);

        assertThat(dividedMoney).isEqualTo(money);
    }

}