package team2869.bethpage.robotics.aerialassist.commands;

import team2869.bethpage.robotics.aerialassist.RobotMap;

/**
 * Command that drives the robot forward, at a slight diagonal, in autonomous 
 * mode, while winding the shooter mechanism.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class AutonomousDrive extends CommandBase {
    
    private int direction;
    
    private double autoDistance;
    private double autoX;
    private double autoY;
    
    /**
     * Constructs a new Command with RobotMap default distance, x direction
     * speed, and y direction speed of the robot.
     */
    public AutonomousDrive() {
        this(RobotMap.AUTONOMOUS_DISTANCE, RobotMap.AUTONOMOUS_X_SPEED,
                RobotMap.AUTONOMOUS_Y_SPEED);
    }
    
    /**
     * Constructs a new Command with autoDistance, autoX, and autoY
     * @param autoDistance The distance the robot must be from the wall upon
     * drive completion.
     * @param autoX The speed the robot moves in the x direction.
     * @param autoY The speed the robot moves in the y direction.
     */
    public AutonomousDrive(double autoDistance, double autoX, double autoY) {
        requires(launcher);
        requires(driveTrain);
        
        this.autoDistance = autoDistance;
        this.autoX = autoX;
        this.autoY = autoY;
    }

    /*
     * Determines the direction that the robot must move in. A positive value
     * indicates that the robot will move right, and a negative value indicates
     * that the robot will move left.
     */
    protected void initialize() {
        direction = launcher.autonomousDirection();
    }
    
    /**
     * Drives the robot until the distance from the alliance wall is about the
     * same as autoDistance, in the direction determined by the initialize 
     * method. Shooting mechanism is also wound.
     */
    protected void execute() {
        if (direction > 0) {
            if (launcher.autonomousDrive(autoDistance) > 0)
                driveTrain.mecanumDrive(autoX, autoY, 0, 0);
            else if (launcher.autonomousDrive(autoDistance) < 0)
                driveTrain.mecanumDrive(-autoX, -autoY, 0, 0);
            else
                driveTrain.mecanumDrive(0, 0, 0, 0);
        }
        else {
            if (launcher.autonomousDrive(autoDistance) > 0)
                driveTrain.mecanumDrive(-autoX, autoY, 0, 0);
            else if (launcher.autonomousDrive(autoDistance) < 0)
                driveTrain.mecanumDrive(autoX, -autoY, 0, 0);
            else
                driveTrain.mecanumDrive(0, 0, 0, 0);
        }
        launcher.constantWind();
    }

    /**
     * If the shooting mechanism is done winding and the target distance has
     * been reached, then this command can stop executing.
     * 
     * @return True if the command should terminate, and false if not.
     */
    protected boolean isFinished() {
        return (launcher.isDoneWinding() && 
                launcher.autonomousDrive(autoDistance) == 0);
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}