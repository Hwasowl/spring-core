package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {
    @Test
    @DisplayName("")
    void statefulServiceSingleton() {
        // NOTE: 싱글톤을 사용할 땐 무상태로 설계하자. 공유필드를 사용하게 된다면 클라이언트에서 값을 변경할 수 없도록 설계해야 한다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService ss1 = ac.getBean(StatefulService.class);
        StatefulService ss2 = ac.getBean(StatefulService.class);

        // Thread A: A 사용자 10000원 주문
        ss1.order("userA", 10000);
        // Thread B: B 사용자 20000원 주문
        ss2.order("userB", 20000);

        // Thread A: A 사용자 주문 금액 조회
        Assertions.assertThat(ss1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}
