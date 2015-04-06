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
    private Post post;
    
    public Like(Post post, User liker){
        this.post = post;
        this.liker = liker;
    }
    
    public User getLiker(){
        return liker;
    }

    /**
     * @param liker the liker to set
     */
    public void setLiker(User liker) {
        this.liker = liker;
    }

    /**
     * @return the post
     */
    public Post getPost() {
        return post;
    }

    /**
     * @param post the post to set
     */
    public void setPost(Post post) {
        this.post = post;
    }
}
