package Elkhadema.khadema.Service.ServiceInterfaces;

import java.util.List;

import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.Reaction;
import Elkhadema.khadema.domain.User;

public interface PostService {
    List<Post> getPostsByUser(User user);

    List<Post> getPostComments(Post post);

    void makePost(Post post);
    List<Post> feed();
    public Post getPostById(Post post) ;

    void addCommentToPost(Post post, Post comment);

    void removeCommentFromPost(Post post, Post comment);

    List<Reaction> getPostReactions(Post post);
    void addReactionPost(Post post,Reaction reaction);
    void removePost(Post post);
    void removeReactionFromPost(Post post, Reaction reaction);}
