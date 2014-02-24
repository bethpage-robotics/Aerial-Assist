package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Command that allows the robot to spin without the use of the secondary
 * joystick's x axis, by converting the primary joystick's x axis into the
 * rotation axis. All other horizontal and vertical movement is inhibited,
 * however.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class SpinDrive extends CommandBase {
    
    /**
     * Constructs a new Command.
     */
    public SpinDrive() {
        requires(driveTrain);
        this.setInterruptible(false);
    }

    protected void initialize() {
    }
    
    /**
     * Controls the mecanum driveTrain using rotation only, while continually
     * updating the SmartDashboard.
     */
    protected void execute() {
        driveTrain.mecanumDrive(0, 0, operatorInterface.getCartesianX(), 0);
        driveTrain.updateDashboard(operatorInterface.getCartesianX(), 
            operatorInterface.getCartesianY(), operatorInterface.getRotation());
    }

    /**
     * If the hold button that controls the execution of this Command is
     * released, then this Command will be terminated.
     * 
     * @return True if the command should terminate, and false if not.
     */
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}