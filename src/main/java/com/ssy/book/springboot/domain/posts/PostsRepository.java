package com.ssy.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Posts 클래스로 DB를 접근하게 해줄 JpaRepositor
// iBatis/MyBatis에서 Dao(Data Access Object)라고 불리는 DB Layer 접근자
// JPA에서는 Repository라고 부르며 인터페이스로 생성
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
