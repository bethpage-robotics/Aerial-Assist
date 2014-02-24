package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Often the robot may be positioned by the operator so that the target power
 * is between two integer click values. This Command drives the robot forward 
 * until the lower click bound is reached, to increase shot accuracy quite
 * tremendously.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class DriveToExactClick extends CommandBase {
    
    /**
     * Constructs a new Command.
     */
    public DriveToExactClick() {
        requires(driveTrain);
        requires(launcher);
        
        this.setInterruptible(false);
    }

    protected void initialize() {
    }

    /**
     * Drives the robot forward slowly (to minimize jerk) and continues to wind
     * the launch system if necessary, while updating the SmartDashboard.
     */
    protected void execute() {
        driveTrain.mecanumDrive(0, 0.25, 0, 0);
        launcher.windLaunchSystem();
        launcher.updateDashboard();
    }

    /**
     * If the shooting mechanism has wound to an absolute click value, that is 
     * much closer to a target integer value, then this command can stop
     * executing.
     * 
     * @return True if the command should terminate, and false if not.
     */
    protected boolean isFinished() {
        return launcher.isAbsoluteWound();
    }

   /**
    * Resets the driveTrain to stop moving because when the shot is made, the
    * driveTrain should not move and be inaccessible to the operator.
    */
    protected void end() {
        driveTrain.mecanumDrive(0, 0, 0, 0);
    }
    
    protected void interrupted() {
    }
}