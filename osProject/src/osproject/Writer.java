/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osproject;

/**
 *
 * @author XinYi
 */
//**************************************************************
/*
* A writer to the database.
*/
class Writer implements Runnable{
    private RWLock database;
    private int writerNo;

    public Writer(int w, RWLock d) {
        writerNo = w;
        database = d;
    }

    public void run() {
        while (true){
            try { Thread.sleep( (int) (5 * Math.random() )*1000); }
            catch (InterruptedException e) {}

            System.out.println("Writer " + writerNo + " 請求編輯資料.");
            database.acquireWriteLock(writerNo);
            
            //writing
            try { Thread.sleep( (int) (5 * Math.random() )*1000); }
            catch (InterruptedException e) {}
  
            database.releaseWriteLock(writerNo);
        }
    }
}
