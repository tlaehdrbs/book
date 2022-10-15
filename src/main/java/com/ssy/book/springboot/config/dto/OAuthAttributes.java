package com.ssy.book.springboot.config.dto;

import com.ssy.book.springboot.domain.user.Role;
import com.ssy.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes
                        ,String nameAttributeKey
                        ,String name
                        ,String email
                        ,String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /**
     * of()
     *  :OAuth2User에서 반환하는 사용자 정보는 Map이기에 값 하나하나를 변환해야함
     */
    public static OAuthAttributes of(String registrationId
                                ,String userNameAttributeName
                                ,Map<String, Object> attributes) {
            /**
             * 네이버 추가
             */
            if ("naver".equals(registrationId)) {
                return ofNaver("id", attributes);
            }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                    .name((String) attributes.get("name"))
                    .email((String) attributes.get("email"))
                    .picture((String) attributes.get("picture"))
                    .attributes(attributes)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
    }

    // ----------------------------------- 211Page!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    /**
     * 네이버 추가
     */
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * toEntity()
     *  :User 엔티티를 생성
     *  :OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때
     *  :가입할 때 기본 권한을 GUEST로 주기 위해서 role 빌더 값에는 Role.GUEST를 사용
     *  :OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스 생성
     */
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
//                .role(Role.GUEST)
                .role(Role.USER)
                .build();
    }
}
