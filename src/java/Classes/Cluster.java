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
public class Cluster {
    private String name;
    private ArrayList<User> memberList;
    private User owner;
    
    public Cluster(String name, User owner){
        this.name = name;
        this.owner = owner;
        memberList = new ArrayList<>();
    }
    
    public void addMember(User member){
        memberList.add(member);
    }
    
    public void removeMember(User member){
        memberList.remove(member);
    }
}
