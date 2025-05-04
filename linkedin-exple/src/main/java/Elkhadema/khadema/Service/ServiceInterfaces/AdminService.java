package Elkhadema.khadema.Service.ServiceInterfaces;

import Elkhadema.khadema.domain.Admin;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.User;

public interface AdminService {
    boolean isAdmin(User user);

    Admin addAdmin(User user);

    void BanUser(User user);

    void deletePost(Post post);
}
