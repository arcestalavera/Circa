
package Classes;

import java.util.ArrayList;


public class Cluster {
    private int clusterID;
    private String name;
    private ArrayList<User> memberList;

    public Cluster(int clusterID, String name){
        this.clusterID = clusterID;
        this.name = name;
        memberList = new ArrayList<>();
    }
    
    public int getClusterID(){
        return clusterID;
    }
    
    public void setClusterID(int clusterID){
        this.clusterID = clusterID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<User> memberList) {
        this.memberList = memberList;
    }
}
    
