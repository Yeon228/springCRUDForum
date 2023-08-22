package yeon.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yeon.core.data.Post;
import yeon.core.repository.RamPostRepository;
import yeon.core.validator.PostValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.logging.Logger;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostValidator validator;
    private final RamPostRepository repository;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(validator);
    }
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
    public String editPost(@PathVariable Integer postID, @ModelAttribute Post post, BindingResult bindingResult){
        if(!StringUtils.hasText(post.getBody())){
            bindingResult.rejectValue("body", "empty");
            System.out.println();
            System.out.println("PostController.posting");

        }
        if(!StringUtils.hasText(post.getTitle())){
            bindingResult.rejectValue("title", "empty");
            System.out.println();
            System.out.println("PostController.posting");
        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "post/editPost";
        }
        repository.editPost(postID, post);
        return "redirect:/post/{postID}";
    }

    @GetMapping("/addPost")
    public String getAddForm(Model model){
        model.addAttribute("post", new Post());
        return "post/addPost";
    }

    @PostMapping("/addPost")
    public String posting(@Validated @ModelAttribute Post post
            ,BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , Model model
            ){
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "post/addPost";
        }

        int postID = repository.getCount() + 1;
        post.setPostID(postID);
        post.setDate(LocalDateTime.now());
        repository.createPost(post);

        redirectAttributes.addAttribute("postID", post.getPostID());
        model.addAttribute("post", post);
        return "redirect:/post/{postID}";
    }

}
