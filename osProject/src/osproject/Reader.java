/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osproject;

import java.util.ArrayList;

/**
 *
 * @author XinYi
 */
//***********************************************************
/*
* A reader to the database.
*/

class Reader implements Runnable{

    private RWLock database;
    private int readerNo;

    public Reader(int readerNo, RWLock database) {
        this.readerNo = readerNo;
        this.database = database;
    }

    public void run() {
        while (true) {
            try { Thread.sleep( (int) (5 * Math.random() )*1000); }
            catch (InterruptedException e) {}

            System.out.println("Reader " + readerNo + " 請求讀取資料.");
            database.acquireReadLock(readerNo);
            
            // reading
            try { Thread.sleep( (int) (5 * Math.random() )*1000); }
            catch (InterruptedException e) {}
            
            database.releaseReadLock(readerNo);
        }
    };

}

