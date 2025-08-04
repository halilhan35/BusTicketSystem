package models;

import interfaces.ILoginable;

public abstract class User implements ILoginable {
  protected String username;
  protected String password;

 public User( String username, String password){
  this.username = username;
  this.password = password;
 }


}
