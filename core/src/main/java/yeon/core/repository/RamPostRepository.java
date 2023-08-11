package yeon.core.repository;

import org.springframework.stereotype.Repository;
import yeon.core.data.Post;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RamPostRepository {
    private final Map<Integer, Post> map = new HashMap<>();
    public void createPost(Post post, int postID){
        map.put(getCount() + 1, post);
    }

    public Post getPost(int postNumber){
        return map.get(postNumber);
    }

    public void editPost(int postNumber, String newTitle, String newBody){
        Post post = map.get(postNumber);
        post.setTitle(newTitle);
        post.setBody(newBody);
    }

    public void removePost(int postNumber){
        map.remove(postNumber);
    }

    public Collection<Post> getAll(){
        return map.values();
    }
    public int getCount(){
        return map.size();
    }
}
