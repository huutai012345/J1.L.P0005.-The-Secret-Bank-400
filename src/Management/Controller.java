/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import java.util.Scanner;

/**
 *
 * @author NHT
 */
public class Controller {

    private Process process;

    public Controller(Process process) {
        this.process = process;
    }

    public void menuAuth() {
        System.out.println("\t\t MENU \n");
        System.out.println("1.  Create New Account");
        System.out.println("2.  Login");
        System.out.println("0.  Exit");
        System.out.print("Chose: ");
    }

    public void menuMain() {
        System.out.println("\t\t MENU \n");
        System.out.println("1.  Withdraw");
        System.out.println("2.  Deposit");
        System.out.println("3.  Transfer");
        System.out.println("4.  Remove Account");
        System.out.println("5.  Logout");
        System.out.println("0.  Exit");
        System.out.print("Chose: ");
    }

    public void auth() {
        Scanner sc = new Scanner(System.in);
        int luaChon = 0;
        while (true) {
            menuAuth();
            luaChon = sc.nextInt();
            switch (luaChon) {
                case 1:
                    if (this.process.addUser()) {
                        this.run();
                    }
                    break;
                case 2: {
                    if (this.process.login()) {
                        this.run();
                    }
                    break;
                }
                case 0: {
                    this.process.saveFile();
                    System.exit(0);
                }

                default:
                    System.out.println("Input invail");
                    break;
            }
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        int luaChon = 0;
        while (true) {
            menuMain();
            luaChon = sc.nextInt();
            switch (luaChon) {
                case 1: {
                    this.process.withdraw();
                    break;
                }
                case 2: {
                    this.process.deposit();
                    break;
                }
                case 3: {
                    this.process.transfer();
                    break;
                }
                case 4: {
                    if (this.process.deleteUser()) {
                        return;
                    }
                    break;
                }
                case 5: {
                    this.process.logout();
                    return;
                }
                case 6: {
                    this.process.print();
                    break;
                }
                case 0: {
                    this.process.saveFile();
                    System.exit(0);
                }

                default:
                    System.out.println("Input invail");
                    break;
            }
        }
    }
}
