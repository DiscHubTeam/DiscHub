package com.dischub.login;

/**
 *
 * @author rodtr
 */
public class UserCredentials {

    private String userEmail;
    private int userExperience;
    private String userPassword;
    private String userScreenName;

    public UserCredentials(String email, String password, String screenName, int experience) {
        userEmail = email;
        userPassword = password;
        userScreenName = screenName;
        userExperience = experience;
        
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the userExperience
     */
    public int getUserExperience() {
        return userExperience;
    }

    /**
     * @param userExperience the userExperience to set
     */
    public void setUserExperience(int userExperience) {
        this.userExperience = userExperience;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the userScreenName
     */
    public String getUserScreenName() {
        return userScreenName;
    }

    /**
     * @param userScreenName the userScreenName to set
     */
    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

}
