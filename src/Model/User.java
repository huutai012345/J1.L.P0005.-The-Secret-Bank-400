/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Library.PasswordHelper.getSHA;
import static Library.PasswordHelper.toHexString;
import java.util.Scanner;

/**
 *
 * @author NHT
 */
public class User {

    private int id;
    private String userName;
    private String password;
    private String confirm;
    private float amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public User() {
    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.amount = 0;
    }
    
     public User(int id, String userName,float  amount, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.amount = amount;
    }

    public void input(int id, String userName) {
        Scanner sc = new Scanner(System.in);
        String regexPass="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        boolean checkPass; 
        
        this.id = id;
        this.userName = userName;

        do {
            checkPass=true; 
            System.out.print("Enter Password:");
            this.password = sc.nextLine();
            if (this.password.length() < 6 || !this.password.matches(regexPass)) {
                System.out.println("Password invalid");
                checkPass=false;
            }
        } while (!checkPass);

        do {
            System.out.print("Enter Confirm Password:");
            this.confirm = sc.nextLine();
            if (!this.password.equals(this.confirm)) {
                System.out.println("Confirm password must equal password");
            }
        } while (!this.password.equals(this.confirm));

        this.password = toHexString(getSHA(this.password));
    }

    public void output() {
        System.out.printf("%-20d%-20s%-20f\n", this.id, this.userName,this.amount);
    }

    public void updateAmount(float amount) {
        this.amount += amount;
    }
}
