/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import static Library.PasswordHelper.getSHA;
import static Library.PasswordHelper.toHexString;
import Model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author NHT
 */
public class Process {
    private String urlFile="D:\\Code\\Java\\NetBeansProjects\\J1.L.P0005.-The-Secret-Bank-400\\src\\DB\\user.txt";
    private int id = 4;
    private ArrayList<User> users;
    private User user = null;

    public Process() {
        this.users = new ArrayList<>();
        this.loadFile();
    }

    public User getUser(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public boolean checkConfirm() {
        String chose = "n";
        Scanner sc = new Scanner(System.in);
        System.out.print("Do you want confirm(y/n)");
        chose = sc.nextLine();
        return "y".equals(chose);
    }

    public boolean checkContinue() {
        String chose = "n";
        Scanner sc = new Scanner(System.in);
        System.out.print("Do you want continue(y/n)");
        chose = sc.nextLine();
        return "y".equals(chose);
    }

    public boolean checkDelete() {
        String chose = "";
        Scanner sc = new Scanner(System.in);
        System.out.print("Do you want delete (y/n)");
        chose = sc.nextLine();
        return "y".equals(chose);
    }

    public boolean addUser() {
        Scanner sc = new Scanner(System.in);
        String userName;
        User userTemp = null;
        do {
            System.out.print("Enter Username(-1 to Exit):");
            userName = sc.nextLine();
            if ("-1".equals(userName)) {
                return false;
            }
            userTemp = getUser(userName);
            if (userTemp != null) {
                System.out.println("User does not exist");
                return false;
            }
        } while (userTemp != null);

        User newUser = new User();
        newUser.input(id++, userName);
        users.add(newUser);
        this.user = newUser;
        return true;
    }

    public boolean login() {
        Scanner sc = new Scanner(System.in);
        String userName, password;
        do {

            System.out.print("Enter Username(-1 to Exit):");
            userName = sc.nextLine();
            if ("-1".equals(userName)) {
                return false;
            }
            this.user = getUser(userName);
            if (this.user == null) {
                System.out.println("User Not Found!");
                return false;
            }
        } while (this.user == null);

        System.out.print("Enter Password:");
        password = sc.nextLine();
        password = toHexString(getSHA(password));
        
        if (this.user.getUserName().equals(userName) && this.user.getPassword().equals(password)) {
            return true;
        }

        System.out.println("Password Invalid");
        return false;
    }
    
    public void logout()
    {
        this.user=null;
    }

    public void print() {
        if (users.isEmpty()) {
            System.out.println("List User Is Empty");
            return;
        }
        System.out.println("\t\t List User \n");
        System.out.printf("%-20s%-20s\n", "ID", "UserName");
        for (User user : users) {
            user.output();
        }
        System.out.println("\n\t--------------------------------");
    }
    
    public Float inputFloat() {
        String input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            input = sc.nextLine();
            try {
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.println("Input is not float");
                System.out.print("Enter Amount Money:");
            }
        }
    }

    public float inputAmountMoney() {
        Scanner sc = new Scanner(System.in);
        float amount;

        do {
            System.out.print("Enter Amount Money:");
            amount = inputFloat();
            if (amount <= 0) {
                System.out.println("Amount Money Invalid");
            }
        } while (amount <= 0);

        return amount;
    }

    public void withdraw() {
        float amount = inputAmountMoney();
       
        if (this.user.getAmount() < amount) {
            System.out.println("Not Enough Money");
            return;
        }

        this.user.updateAmount(-amount);
        System.out.println("Success");
    }

    public void deposit() {
        float amount = inputAmountMoney();

        if (this.checkConfirm()) {
            this.user.updateAmount(amount);
            System.out.println("Success");
        }
    }

    public void transfer() {
        String userName;
        User userRecive;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Username(-1 to Exit):");
        userName = sc.nextLine();
        if ("-1".equals(userName)) {
            return;
        }
        userRecive = getUser(userName);
        if (userRecive == null) {
            System.out.println("User Not Found!");
            return;
        }
        
           if (userRecive.getId() == user.getId()) {
            System.out.println("User Invalid!");
            return;
        }

        float amount = inputAmountMoney();
        if (this.user.getAmount() < amount) {
            System.out.println("Not Enough Money");
            return;
        }

        if (this.checkConfirm()) {
            this.user.updateAmount(-amount);
            userRecive.updateAmount(amount);
            System.out.println("Success");
        }
    }

    public boolean deleteUser() {
        if (this.checkConfirm()) {
            Scanner sc = new Scanner(System.in);
            this.users.remove(user);
            System.out.println("Succes");
            return true;
        }

        return false;
    }
    
    public User getData(String str) {
        String[] words = str.split("\\s");
        return new User(Integer.parseInt(words[0]), words[1], Float.parseFloat(words[2]),words[3]);
    }

    private void loadFile() {
        try {
            File myObj = new File(urlFile);
            Scanner myReader = new Scanner(myObj);
            
            String data = myReader.nextLine();
            id = Integer.parseInt(data)+1;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                //System.out.println(data);
                users.add(getData(data));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred."+e);
        }
    }

    public void saveFile() {
        try {
            try (FileWriter myWriter = new FileWriter(urlFile)) {
                myWriter.write(users.get(users.size()-1).getId()+"\n");
                for (User user : users) {
                    myWriter.write(user.getId()+ " " +user.getUserName() + " " + user.getAmount()+ " " + user.getPassword()+ "\n");
                }
            }
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
           System.out.println("An error occurred."+e);
        }
    }
}
