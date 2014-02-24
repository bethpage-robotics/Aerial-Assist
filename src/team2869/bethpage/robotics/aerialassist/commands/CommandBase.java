package team2869.bethpage.robotics.aerialassist.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import team2869.bethpage.robotics.aerialassist.OI;
import team2869.bethpage.robotics.aerialassist.subsystems.DriveTrain;
import team2869.bethpage.robotics.aerialassist.subsystems.Launcher;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in the code, use CommandBase.subsystem
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public abstract class CommandBase extends Command {

    public static OI operatorInterface;
    
    // Create a single static instance of all subsystems
    public static DriveTrain driveTrain = new DriveTrain();
    public static Launcher launcher = new Launcher();
    
    /**
     * Initialize the operator interface and show which commands are running on
     * the various subsystems on the SmartDashboard.
     */
    public static void init() {
        // Instantiate the operator interface
        operatorInterface = new OI();

        // Show command the subsystem is running on the SmartDashboard
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(launcher);
    }
    
    /**
     * Constructor for the CommandBase class
     * @param name The name of the class.
     */
    public CommandBase(String name) {
        super(name);
    }
    
    /**
     * Default constructor for the CommandBase class.
     */
    public CommandBase() {
        super();
    }
}
