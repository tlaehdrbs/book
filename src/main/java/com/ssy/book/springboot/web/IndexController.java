package com.ssy.book.springboot.web;

import com.ssy.book.springboot.config.auth.LoginUser;
import com.ssy.book.springboot.config.dto.SessionUser;
import com.ssy.book.springboot.service.posts.PostsService;
import com.ssy.book.springboot.web.dto.PostsResponseDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        /**
         * @LoginUser 를 통해 처리하도록 리팩토링
         *  :어떤 컨트롤러든지 @LoginUser만 사용하면 세션 정보 조회 가능
         */
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

//    public String index() {
//        return "index";
//    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
