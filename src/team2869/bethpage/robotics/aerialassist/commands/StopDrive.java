package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Stops the movement of the robot's driveTrain. Not necessary to instantiate
 * this when shooting due to inbuilt stop mechanism.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class StopDrive extends CommandBase {
    
    /**
     * Constructs a new Command.
     */
    public StopDrive() {
        requires(driveTrain);
    }

    protected void initialize() {
    }

    /**
     * Controls the driveTrain by stopping it, regardless of joystick input, 
     * while continually updating the SmartDashboard.
     */
    protected void execute() {
        driveTrain.mecanumDrive(0, 0, 0, 0);
        driveTrain.updateDashboard(operatorInterface.getCartesianX(), 
            operatorInterface.getCartesianY(), operatorInterface.getRotation());
    }
    
    /**
     * If the toggle button that controls the execution of this Command is
     * toggled off, then this Command will be terminated.
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