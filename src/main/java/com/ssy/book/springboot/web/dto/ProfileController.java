package com.ssy.book.springboot.web.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 이 API는 이후 배포시 8081을 사용할지, 8082를 사용할지 판단하는 기준
 *
 * +이 /profile이 인증 없이도 호출될 수 있어야 하기에
 *  SecurityConfig -> antMatchers에 추가한다.
 */

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        /**
         * env.getActiveProfiles()
         * :현재 실행 중인 ActiveProfile을 모두 가져옴
         * :즉, real, oauth, real-db 등이 활성화되어 있따면 3개가 모두 담겨있다
         * :여기서 real, real1, real2는 모두 배포에 사용될 profile이라 이 중 하나라도 있으면 그 값을 return
         * :실제로 이번 무중단 배포에서는 real1, real만 사용되지만 step2를 다시 사용해 볼 수 있으니 real도 남겨논다
         */
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
