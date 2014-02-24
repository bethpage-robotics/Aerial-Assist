package team2869.bethpage.robotics.aerialassist.commands;

import team2869.bethpage.robotics.aerialassist.RobotMap;

/**
 * Command that resets the launcher subsystem after a shot has been completed.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class ResetShoot extends CommandBase {
    
    private double timeout;
    
    /**
     * Constructs a new Command with RobotMap default time to reset the shooter
     * mechanism.
     */
    public ResetShoot() {
        this(RobotMap.RESET_TIME);
    }
    
    /**
     * Constructs a new Command with a set timeout.
     * @param timeout Time in seconds that the Command will run for.
     */
    public ResetShoot(double timeout) {
        requires(driveTrain);
        requires(launcher);
        
        this.timeout = timeout;
        
        this.setTimeout(this.timeout);
        this.setInterruptible(false);
    }

    protected void initialize() {
    }

    /**
     * Resets the launcher subsystem after a shoot while keeping the driveTrain
     * inaccessible and updating the SmartDashboard.
     */
    protected void execute() {
        driveTrain.mecanumDrive(0, 0, 0, 0);
        launcher.resetShoot();
        launcher.updateDashboard();
    }

    /**
     * If the time has elasped, the Command will terminate.
     * 
     * @return True if the command should terminate, and false if not.
     */
    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }
    
    protected void interrupted() {
    }
}