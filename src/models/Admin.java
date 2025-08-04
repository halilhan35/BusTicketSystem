package models;

public class Admin extends User {

  private double serviceFee = 1000;

  public Admin (String username, String password){
    super(username, password);
  }

  @Override
  public boolean login(String username, String password){
   return this.username.equals(username) && this.password.equals(password);
  }

  public void setServiceFee(double fee){
    this.serviceFee = fee;}

  public double getServiceFee(){
    return serviceFee;

  }

}
