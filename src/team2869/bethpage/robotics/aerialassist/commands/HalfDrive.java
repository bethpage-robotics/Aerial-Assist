package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Command that drives the robot at half speed, in any direction, and this may
 * be used for careful maneuvering of the robot. 
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class HalfDrive extends CommandBase {
    
    /**
     * Constructs a new Command.
     */
    public HalfDrive() {
        requires(driveTrain);
        setInterruptible(false);
    }

    protected void initialize() {
    }
    
    /**
     * Controls the mecanum driveTrain at half speed by halving all joystick
     * input parameters, while continually updating the SmartDashboard.
     */
    protected void execute() {
        double x = operatorInterface.getCartesianX(),
               y = operatorInterface.getCartesianY(),
               r = operatorInterface.getRotation();
        driveTrain.mecanumDrive(x/2, y/2, r/2, 0);
        driveTrain.updateDashboard(x, y, r);
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