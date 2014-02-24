package team2869.bethpage.robotics.aerialassist.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import team2869.bethpage.robotics.aerialassist.OI;
import team2869.bethpage.robotics.aerialassist.subsystems.DriveTrain;
import team2869.bethpage.robotics.aerialassist.subsystems.ExampleSubsystem;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in the code, use CommandBase.exampleSubsystem
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public abstract class CommandBase extends Command {

    public static OI operatorInterface;
    
    // Create a single static instance of all subsystems
    public static ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    public static DriveTrain driveTrain = new DriveTrain();

    public static void init() {
        SmartDashboard.putBoolean("Checkpoint B", true);
        // Instantiate the operator interface
        operatorInterface = new OI();

        // Show command the subsystem is running on the SmartDashboard
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(exampleSubsystem);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
