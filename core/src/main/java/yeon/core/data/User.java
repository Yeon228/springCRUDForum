package yeon.core.data;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID uuid;
    private String userName;
}
