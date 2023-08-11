package yeon.core.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    public Post(String title, String body, int postID,  LocalDateTime date){
        this.title = title;
        this.body = body;
        this.postID = postID;
        this.date = date;
    }

    private String title;
    private String body;
    private Integer postID;
//    private User author;
    private LocalDateTime date;
}
