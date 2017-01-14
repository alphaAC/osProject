package osProject_1051.os;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author XinYi
 */

import java.util.concurrent.Semaphore;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class main_os{
    public static final int readers = 10;
    public static final int writers = 2;
    
    public static final ArrayList readArray=new ArrayList();
    public static final ArrayList writArray=new ArrayList();
    public static final ArrayList accessingArray=new ArrayList();

    public static void main(String args[]){
        
        //GUI
        JFrame demo = new JFrame();
        demo.setSize(600, 450);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setLocationRelativeTo(null);
         
       
        demo.setVisible(true);
        
        
        //
        RWLock database = new Database();
        
        Thread[] listReader = new Thread[readers];
        Thread[] listWriter = new Thread[writers];

        for (int i = 0; i < readers; i++) {
            listReader[i] = new Thread(new Reader(i, database));
            listReader[i].setDaemon(true); //Daemon
            listReader[i].start();

        }

        for (int i = 0; i < writers; i++) {
            listWriter[i] = new Thread(new Writer(i, database));
            listReader[i].setDaemon(true); //Daemon
            listWriter[i].start();

        }
    }
}

