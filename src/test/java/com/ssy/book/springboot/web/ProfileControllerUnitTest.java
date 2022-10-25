package com.ssy.book.springboot.web;

import com.ssy.book.springboot.web.dto.ProfileController;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileControllerUnitTest {

    /**
     * ProfileController, Environment 는 모두 자바 클래스(인터페이스)
     * Environment는 인터페이스 -> 가짜 구현체인 MockEnvironment를 사용해서 테스트 가능
     *
     * 생성자 의존성 주입(DI)이기에 스프링 테스트 없이 동작!
     * 만약 필드 인젝션이였다면?
     */

    @Test
    public void real_profile이_조회된다() {
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController profileController = new ProfileController(env);

        //when
        String profile = profileController.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile이_없으면_첫번째가_조회된다() {
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController profileController = new ProfileController(env);

        //when
        String profile = profileController.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

}
