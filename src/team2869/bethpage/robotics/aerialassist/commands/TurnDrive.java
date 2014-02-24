package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Command that implements all set turn functionalities for the robot, by 
 * allowing it to turn in any direction and for a set amount of time.
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class TurnDrive extends CommandBase {

    private double timeout;
    private boolean direction;

    /*
     * Constructs a new command with the default turn time of 0.5 seconds.
     */
    public TurnDrive() {
        this(0.5, true);
    }

    /**
     * Constructs a Turn Command with a set timer that will turn the robot 
     * clockwise, by default.
     *
     * @param timeout Time in seconds that this Command will run for.
     */
    public TurnDrive(double timeout) {
        this(timeout, true);
    }

    /**
     * Constructs  a Turn Command with a set timer that will turn the robot in 
     * a certain direction.
     *
     * @param timeout Time in seconds that the Command will run for.
     * @param direction The direction of the turn. A value of true indicates a
     * clockwise turn, and false indicates a counterclockwise turn.
     */
    public TurnDrive(double timeout, boolean direction) {
        requires(driveTrain);
        this.setInterruptible(false);

        this.timeout = timeout;
        this.direction = direction;

        this.setTimeout(this.timeout);
    }

    protected void initialize() {
    }

    /**
     * Turns the robot in the set direction, while continually updating the
     * SmartDashboard.
     */
    protected void execute() {
        if (direction) {
            driveTrain.mecanumDrive(0, 0, 0.5, 0);
        } else {
            driveTrain.mecanumDrive(0, 0, -0.5, 0);
        }
        driveTrain.updateDashboard(operatorInterface.getCartesianX(), 
            operatorInterface.getCartesianY(), operatorInterface.getRotation());
    }

    /**
     * If the time has elasped, the Command will terminate.
     * 
     * @return True if the command should terminate, and false if not.
     */
    protected boolean isFinished() {
        return this.isTimedOut();
    }
    
    protected void end() {
    }

    protected void interrupted() {
    }
}