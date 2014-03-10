package team2869.bethpage.robotics.aerialassist;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import java.io.PrintStream;
import java.util.Date;
import java.util.Calendar;

/**
 * The Logger handles sending output to either the console screen or some text
 * file.
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - LEON LASPINA
 */
public class Logger {

    /**
     * Singleton pattern - only one instance of this Logger can be created and
     * used.
     */
    private static Logger log = null;
    private PrintStream out = null;
    private DataOutputStream dataOut = null;

    /* Logging levels from least important (most detailed) to most important
     * messages only.
     */
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
     *
     * @return Logger instance
     */
    public static Logger getLogger() {
        if (log == null) {
            return new Logger();
        } else {
            return log;
        }
    }

    /**
     * Removes a previously selected PrintStream, so for instance, data will no
     * longer get logged to the console.
     */
    public void removeOutputStream() {
        out = null;
    }

    public void sendOutputTo(PrintStream stream) {
        out = stream;
    }

    /**
     * Sends all logging output to a file with an auto-generated filename based
     * on the date. The basename is used as the start of the filename where a
     * date string is appended after it along with a file extension.
     *
     * @param basename is a filename without a file extension (and no path
     * indicated)
     */
    public void sendOutputToDateFile(String basename) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String filename = basename + calendar.get(Calendar.HOUR) + "-"
                + calendar.get(Calendar.MINUTE);
        sendOutputToFile(filename + ".log");
    }

    /**
     * Specify a filename for logging to be sent to and this logger will direct
     * all output to this file. Note that if the file exists, it will be
     * replaced with the new file. If a file was previously open for logging
     * output, this file is closed first.
     *
     * @param filename should end in .txt or .log and will be stored in the root
     * directory on the cRIO.
     */
    public void sendOutputToFile(String filename) {
        close();
        try {
            FileConnection fc = (FileConnection) Connector.open("file:///"
                    + filename, Connector.WRITE);
            fc.create();
            dataOut = fc.openDataOutputStream();
        } catch (IOException ex) {
            System.out.println("Unable to open file for logging: " + filename);
            System.out.println(ex);
        }
    }

    /**
     * Closes the underlying DataOutputStream for the text file. After this is
     * done, further calls to log will not output to the file.
     */
    public void close() {
        try {
            if (dataOut != null) {
                dataOut.close();
            }
        } catch (IOException exception) {
        }
        dataOut = null;
    }

    public void log(String message, int level) {
        if (level >= Logger.currentLevel) {
            print(message);
        }
    }

    public void log(Exception exception) {
        print(exception.getMessage());
    }

    public void log(Class source, String message, int level) {
        if (level >= Logger.currentLevel) {
            print(source.getName() + " : " + message);
        }
    }

    public void log(Class source, String message) {
        log(source, message, 9);
    }

    /**
     * Used by various logging methods to push data onto the specified output
     * stream.
     *
     * @param message The string to print
     */
    private void print(String message) {
        Date date = new Date();
        String datePrint = date.toString();
        if (out != null) {
            out.print(datePrint + ": " + message);
        }
        if (dataOut != null) {
            try {
                dataOut.writeUTF(datePrint + " : " + message + "\n");
            } catch (IOException exception) {
            }
        }
    }
}
