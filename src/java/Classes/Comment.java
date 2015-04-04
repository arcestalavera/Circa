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
public class Comment {
    private User commenter;
    private int commentID;
    private String commentText;
    
    public Comment(User commenter, String commentText, int commentID){
        this.commenter = commenter;
        this.commentText = commentText;
        this.commentID = commentID;
    }
    
    /**
     * @return the commenter
     */
    public User getCommenter() {
        return commenter;
    }

    /**
     * @param commenter the commenter to set
     */
    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    /**
     * @return the commentText
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * @return the commentID
     */
    public int getCommentID() {
        return commentID;
    }

    /**
     * @param commentID the commentID to set
     */
    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }
}