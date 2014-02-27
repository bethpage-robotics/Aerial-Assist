package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import team2869.bethpage.robotics.aerialassist.commands.AutonomousShootBall;
import team2869.bethpage.robotics.aerialassist.commands.CommandBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public class AerialAssist extends IterativeRobot {

    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code. The autonomous command is instantiated.
     */
    public void robotInit() {
        // Initialize all subsystems
        CommandBase.init();
        // Instantiate the command used for the autonomous period.
        autonomousCommand = new AutonomousShootBall();
    }

    public void autonomousInit() {
        // Schedule the autonomous command.
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous. This is all
     * handled by the commands.
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function initializes the teleoperated mode. The default commands
     * for each subsystem are set to run, if not already running.
     */
    public void teleopInit() {
        autonomousCommand.cancel();
        
        if (!CommandBase.driveTrain.getCurrentCommand().getName().equals("MecanumDrive")) {
            CommandBase.driveTrain.initDefaultCommand();
        }
        if (!CommandBase.launcher.getCurrentCommand().getName().equals("WinderLaunch")) {
            CommandBase.launcher.initDefaultCommand();
        }
    }

    /**
     * This function is called periodically during operator control. This is all
     * handled by commands and operator interface control.
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode. This is not called
     * during the official game.
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
