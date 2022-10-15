package com.ssy.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가(Argument X)
@Entity // 해당 클래스의 빌더 패턴 클래스 생성
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    // 생성자 대신 해당 Annotation을 통해 제공되는 빌더 클래스 사용
    // 생성자나 빌더나 생성 시점에 값을 채워주는 역할은 동일하나, 생성자의 경우 지금 채워야 할 필드가 무엇인지 명확히 지정할 수 없음
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    
    // Q) Setter가 없는데 어떻게 값을 채워 DB에 insert?
    // A) 기본적인 구조는 생성자를 통해 최종값을 채운 후 DB에 삽입.
    //    만약 값 변경이 피료하다면 해당 이벤트에 맞는 별도 public 메소드를 호출하여 변경

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 113Page~ 영속성 컨텍스트!
     */
}
