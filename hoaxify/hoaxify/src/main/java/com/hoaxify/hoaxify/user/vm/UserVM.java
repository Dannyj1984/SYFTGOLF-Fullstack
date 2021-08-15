package com.hoaxify.hoaxify.user.vm;

import com.hoaxify.hoaxify.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVM {

    private long ID;

    private String userName;

    private String displayname;

    private String image;

    public UserVM(User user) {
        this.setID(user.getId());
        this.setDisplayname(user.getDisplayName());
        this.setUserName(user.getUsername());
        this.setImage(user.getImage());
    }
}
