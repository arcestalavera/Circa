/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Arces
 */
public class User {
    private String firstName, lastName, emailAddress, profilePicture;
    private int userID;
    private Date birthDate;
    private ArrayList<User> buddyList;
    
    public User(int userID, String firstName, String lastName, String emailAddress, Date birthDate, String profilePicture){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the buddyList
     */
    public ArrayList<User> getBuddyList() {
        return buddyList;
    }

    /**
     * @param buddyList the buddyList to set
     */
    public void setBuddyList(ArrayList<User> buddyList) {
        this.buddyList = buddyList;
    }

    /**
     * @return the profilePicture
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * @param profilePicture the profilePicture to set
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}