package team2869.bethpage.robotics.util;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import java.io.PrintStream;
import java.util.Date;

/**
 * The Logger handles sending output to either the console screen or some text file.
 * @author LaSpina
 */
public class Logger {
   
   /**
    * Singleton pattern - only one instance of this Logger can be created and used.
    */
   private static Logger log = null;
   private PrintStream out = null;
   private DataOutputStream dout = null;
   
   //Logging levels from least important (most detailed) to most important messages only.
   public static int INFO = 1;
   public static int WARNING = 2;
   public static int EEROR = 3;
   
   static int currentLevel = 1;
   
   private Logger() {
      out = System.out;
      log = this;
   }
   
/**
 * Use this to obtain a reference to the Logger object.
 * @return 
 */
   public static Logger getLogger() {
      if(log==null)
         return new Logger();
      else
         return log;
   }
   
   /**
    * Removes a previously selected PrintStream, so for instance, data will no longer get logged to the console.
    */
   public void removeOutputStream() {
      out = null;
   }
   
   public void sendOutputTo(PrintStream stream) {
      out = stream;
   }
   
   public void sendOutputToFile(String filename) {
      try {
         FileConnection fc = (FileConnection)Connector.open("file:///" + filename,Connector.WRITE);
         fc.create();
         dout = fc.openDataOutputStream();         
      } catch (IOException ex) {
         System.out.println("Unable to open file for logging: " + filename);
         System.out.println(ex);
      }
   }
   
   /**
    * Closes the underlying DataOutputStream for the text file. After this is done, 
    * further calls to log will not output to the file.
    */
   public void close() {
      try {
         if(dout!=null) {
            dout.close();
         }
      }
      catch(IOException whatever) { }
      dout = null;
   }
   
   public void log(String msg, int level) {
      if(level > Logger.currentLevel)
         print(msg);
   }
   
   public void log(Exception utoh) {
      print(utoh.getMessage());
   }
   
   public void log(Class source, String msg) {
      print(source.getName() + " : " + msg);
   }
   /**
    * Used by various logging methods to push data onto the specified output stream.
    * @param s 
    */
   private void print(String s) {
      Date now = new Date();
      //JavaME took away my DateFormat! Maybe some formatting
      String d = now.toString();
      if(out!=null) {         
         out.print(d + ": " + s);
      }
      if(dout!=null) {
         try {
            dout.writeUTF(d + " : " + s + "\n");
         } catch (IOException ex) {  }
      }
   }
}
