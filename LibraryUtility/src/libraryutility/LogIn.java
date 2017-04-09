/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryutility;

/**
 *
 * @author ENGR. Cristopher
 */
public class LogIn {
    
    String id;
    String password;
    String lastName;
    String firstName;
    String position;
    
    public LogIn(){

    }
    public LogIn(String id, String lastName, String firstName, String position, String password){
        this.id = id;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.position = position;
    }
    
    public String getId(){
        return this.id;
    }
    public String getPassword(){
        return this.password;
    }
    public String getLastname(){
        return this.lastName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getPosition(){
        return this.position;
    }
    public String getName() {
        return this.firstName + " " + this.lastName;
    }
    
}
