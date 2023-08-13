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
        model.addAttribute("post", post);
        return "post/post";
    }

    @GetMapping("/{postID}/remove")
    public String removePost(@PathVariable Integer postID){
        repository.removePost(postID);
        return "redirect:/post";
    }

    @GetMapping("/{postID}/edit")
    public String getEdit(@PathVariable Integer postID, Model model){
        model.addAttribute(repository.getPost(postID));
        return "post/editPost";
    }

    @PostMapping("/{postID}/edit")
    public String editPost(@PathVariable Integer postID, @ModelAttribute Post post){
        repository.editPost(postID, post);
        return "redirect:/post/{postID}";
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
        repository.createPost(post);
        log.info("post ={}",post);

        redirectAttributes.addAttribute("postID", post.getPostID());
        model.addAttribute("post", post);
        return "redirect:/post/{postID}";
    }

}
