package Elkhadema.khadema.Service.ServiceImplemantation;

import Elkhadema.khadema.DAO.DAOImplemantation.AdminDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.PostDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.UserDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.AdminService;
import Elkhadema.khadema.domain.Admin;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.User;

public class AdminServiceImp implements AdminService {
    AdminDAO adminDAO = new AdminDAO();
    UserDAO userDAO = new UserDAO();
    PostDAO postDAO = new PostDAO();

    @Override
    public boolean isAdmin(User user) {
        return adminDAO.isAdmin(user).isPresent();
    }

    @Override
    public Admin addAdmin(User user) {
        adminDAO.makeAdmin(user, 1);
        return adminDAO.isAdmin(user).get();

    }

    @Override
    public void BanUser(User user) {
        user.setIs_banned(true);
        userDAO.update(user, user);

    }

    public void unBanUser(User user) {
        user.setIs_banned(false);
        userDAO.update(user, user);
    }

    @Override
    public void deletePost(Post post) {
        postDAO.delete(post);
    }

}
