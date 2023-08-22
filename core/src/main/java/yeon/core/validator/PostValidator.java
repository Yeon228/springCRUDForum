package yeon.core.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import yeon.core.data.Post;

@Component
public class PostValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Post.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Post post = (Post)target;

        if(post.getTitle() == null) {
            errors.rejectValue("title", "empty");
        }

        if(post.getBody() == null){

            errors.rejectValue("body", "empty");
        }
    }
}

