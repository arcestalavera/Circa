/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

/**
 *
 * @author Arces
 */
public class Like {
    private User liker;
    private int likeID;
    
    public Like(int likeID, User liker){
        this.likeID = likeID;
        this.liker = liker;
    }
    
    public User getLiker(){
        return liker;
    }
}
