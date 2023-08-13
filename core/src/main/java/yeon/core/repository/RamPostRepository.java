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
    private int postCount = 0;
    public void createPost(Post post){
        map.put(++postCount, post);
    }

    public Post getPost(int postNumber){
        return map.get(postNumber);
    }

    public void editPost(int itemID, Post post){
        map.remove(itemID);
        map.put(itemID,post);
    }

    public void removePost(int postNumber){
        map.remove(postNumber);
    }

    public Collection<Post> getAll(){
        return map.values();
    }
    public int getCount(){
        return postCount;
    }
}
