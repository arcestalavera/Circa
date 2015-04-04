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
    private Event event;
    private User poster;
    private ArrayList<Like> likeList;
    private ArrayList<Comment> commentList;
    private int postID;

    public Post(int postID, String postText, User poster, Event event) {
        this.postID = postID;
        this.postText = postText;
        this.poster = poster;
        this.event = event;
    }

    public void addLike(Like like) {
        getLikeList().add(like);
    }

    public void removeLike(Like like) {
        getLikeList().remove(like);
    }

    public void addComment(Comment comment) {
        getCommentList().add(comment);
    }

    public void removeComment(Comment comment) {
        getCommentList().remove(comment);
    }

    /**
     * @return the postText
     */
    public String getPostText() {
        return postText;
    }

    /**
     * @param postText the postText to set
     */
    public void setPostText(String postText) {
        this.postText = postText;
    }

    /**
     * @return the imgPath
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * @param imgPath the imgPath to set
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * @return the poster
     */
    public User getPoster() {
        return poster;
    }

    /**
     * @param poster the poster to set
     */
    public void setPoster(User poster) {
        this.poster = poster;
    }

    /**
     * @return the likeList
     */
    public ArrayList<Like> getLikeList() {
        return likeList;
    }

    /**
     * @param likeList the likeList to set
     */
    public void setLikeList(ArrayList<Like> likeList) {
        this.likeList = likeList;
    }

    /**
     * @return the commentList
     */
    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    /**
     * @param commentList the commentList to set
     */
    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    /**
     * @return the postID
     */
    public int getPostID() {
        return postID;
    }

    /**
     * @param postID the postID to set
     */
    public void setPostID(int postID) {
        this.postID = postID;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }
}
