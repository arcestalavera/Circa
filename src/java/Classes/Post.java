/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.ArrayList;

/**
 *
 * @author Arces
 */
public class Post {
    private String postText, imgPath;
    private User poster;
    private ArrayList<Like> likeList;
    private ArrayList<Comment> commentList;
    private int postID;
    
    public Post(int postID, String postText, User poster, String imgPath){
        this.postID = postID;
        this.postText = postText;
        this.poster = poster;
        this.imgPath = imgPath;
    }
    
    public Post(int postID, String postText, User poster){
        this.postID = postID;
        this.postText = postText;
        this.poster = poster;
    }
    
    public void addLike(Like like){
        likeList.add(like);
    }
    
    public void removeLike(Like like){
        likeList.remove(like);
    }
    
    public void addComment(Comment comment){
        commentList.add(comment);
    }
    
    public void removeComment(Comment comment){
        commentList.remove(comment);
    }
}
