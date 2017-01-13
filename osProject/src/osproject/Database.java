/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osproject;

import java.util.concurrent.Semaphore;

/**
 *
 * @author XinYi
 */
//****************************************************************

/**
* An interface for reader-writer locks.
*/

interface RWLock{
   public abstract void acquireReadLock(int readerNo);
   public abstract void acquireWriteLock(int writerNo);
   public abstract void releaseReadLock(int readerNo);
   public abstract void releaseWriteLock(int writerNo);
}

//****************************************************************

/**
* This class contains the methods the readers and writers will use
* to coordinate access to the database. Access is coordinated using semaphores.
*/


class Database implements RWLock{
    private int readcount, writecount;  // the number of active readers
    // controls access to readcount
    private Semaphore mutex1,mutex2,mutex3,w,r;
    public Database() {
        readcount = 0;
        writecount = 0;
        mutex1 = new Semaphore(1);
        mutex2 = new Semaphore(1);
        mutex3 = new Semaphore(1);
        w = new Semaphore(1);
        r = new Semaphore(1);
    }

    public void acquireReadLock(int readerNo) {
        try{
        //mutual exclusion for readcount 
            mutex3.acquire();
        }
        catch (InterruptedException e) {}

        
        try{
        //mutual exclusion for readcount 
            r.acquire();
            ++readcount;
            System.out.println("Reader " + readerNo + " 正在瀏覽資料. Reader count = " + readcount);
            mutex1.acquire();
        }
        catch (InterruptedException e) {}
        
        // if I am the first reader tell all others
        // that the database is being read
        if (readcount == 1){
            try{
                w.acquire();
            }catch (InterruptedException e) {
            System.out.println("problme w.acquire()");
            }
        }
       
        mutex1.release();
        r.release();
        mutex3.release();
    }

   public void releaseReadLock(int readerNo) {
        try{
        //mutual exclusion for readcount
           mutex1.acquire();
        }
            catch (InterruptedException e) {}

        --readcount;
        System.out.println("Reader " + readerNo + " 瀏覽完畢. Reader count = " + readcount);

     // if I am the last reader tell all others
     // that the database is no longer being read
        if (readcount == 0){
           w.release();
        }

       
       //mutual exclusion for readcount
        mutex1.release();
    }

    public void acquireWriteLock(int writerNo) {
        try{
        //mutual exclusion for readcount 
            mutex2.acquire();
        }
        catch (InterruptedException e) {}

        ++writecount;
        
        // if I am the first reader tell all others
        // that the database is being read
        if (writecount == 1){
            try{
                r.acquire();
            }
            catch (InterruptedException e) {}
        }
        //mutual exclusion for readcount
        mutex2.release();
        
        try{
        //mutual exclusion for readcount 
            w.acquire();
        }
        catch (InterruptedException e) {}
        System.out.println("Writer " + writerNo + " 正在編輯資料. Writer  = " + writecount);
    }

    public void releaseWriteLock(int writerNo) {
        w.release();      
        try{
        //mutual exclusion for readcount
           mutex2.acquire();
        }catch (InterruptedException e) {}

        --writecount;
        System.out.println("Writer " + writerNo + " 編輯完畢. Writer count = " + writecount);

     // if I am the last reader tell all others
     // that the database is no longer being read
        if (writecount == 0){
           r.release();
       }

       //mutual exclusion for readcount
           mutex2.release();
    }
}

