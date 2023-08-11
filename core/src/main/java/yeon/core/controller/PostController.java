package yeon.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yeon.core.data.Post;
import yeon.core.repository.RamPostRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.logging.Logger;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final RamPostRepository repository;
    @GetMapping
    public String posts(Model model){
        Collection<Post> posts = repository.getAll();
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/{postID}")
    public String readPost(@PathVariable Integer postID, Model model){
        Post post = repository.getPost(postID);
        log.info("포스트 가져오기 성공");
        log.info("postID = {}", postID);
        log.info("post = {} ", post);
        model.addAttribute("post", post);
        return "post/post";
    }

    @GetMapping("/addPost")
    public String getAddForm(){
        return "post/addPost";
    }

    @PostMapping("/addPost")
    public String posting(@RequestParam String title
            ,@RequestParam String body
            , RedirectAttributes redirectAttributes
            , Model model){

        int postID = repository.getCount() + 1;
        Post post = new Post(title, body, postID, LocalDateTime.now());
        repository.createPost(post, postID);
        log.info("post ={}",post);

        redirectAttributes.addAttribute("postID", post.getPostID());
        model.addAttribute("post", post);
        return "redirect:/post/{postID}";
    }

}
