package team2869.bethpage.robotics.aerialassist.commands;

import team2869.bethpage.robotics.aerialassist.RobotMap;

/**
 * Stops all of the robot's functions, in order to act as a timeout during
 * autonomous.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class StopRobot extends CommandBase {
    
    private double timeout;
    
    /**
     * Constructs a new Command with the default autonomous wait time.
     */
    public StopRobot() {
        this(RobotMap.AUTONOMOUS_WAIT_TIME);
    }
    
    /**
     * Constructs a new command with a set timeout.
     * @param timeout Time in seconds that the Command will run for.
     */
    public StopRobot(double timeout) {
        requires(driveTrain);
        requires(launcher);
        
        this.timeout = timeout;
        this.setTimeout(this.timeout);
    }

    protected void initialize() {
    }

    /**
     * Stops all subsystems.
     */
    protected void execute() {
        driveTrain.mecanumDrive(0, 0, 0, 0);
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