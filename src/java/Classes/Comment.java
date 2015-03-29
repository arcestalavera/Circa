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
    private int commentID;
    private User commenter;
    private String commentText;
    
    public Comment(int commentID, User commenter, String commentText){
        this.commentID = commentID;
        this.commenter = commenter;
        this.commentText = commentText;
    }
    
    public String getComment(){
        return commentText;
    }
}