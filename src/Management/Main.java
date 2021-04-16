/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

/**
 *
 * @author NHT
 */
public class Main {

    public static void main(String[] args) {
        Process process = new Process();
        Controller ui = new Controller(process);
        ui.auth();;
    }
}
