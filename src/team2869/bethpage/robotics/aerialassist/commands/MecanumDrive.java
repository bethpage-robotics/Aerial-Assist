package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Default Command that constantly runs on the driveTrain subsytem until another
 * Command replaces it. Allows for regular mecanum drive functionality.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */

public class MecanumDrive extends CommandBase {
    
    /**
     * Constructs a new Command.
     */
    public MecanumDrive() {
        requires(driveTrain);
    }

    /**
     * Sets the orientation of the wheels, accounting for wiring differences.
     */
    protected void initialize() {
        driveTrain.setOrientationLeftWheels(false);
        driveTrain.setOrientationRightWheels(true);
    }

    /**
     * Drives the robot with regular mecanum style code, while accounting for
     * slight joystick changes. The SmartDashboard is also continually updated.
     */
    protected void execute() {
        double cartesianX = operatorInterface.getCartesianX(),
               cartesianY = operatorInterface.getCartesianY(),
               rotation = operatorInterface.getRotation();
        double x, y, r;
        
        if (Math.abs(cartesianX) < 0.1) {
            x = 0;
        } else {
            x = cartesianX;
        }
        
        if (Math.abs(cartesianY) < 0.1) {
            y = 0;
        } else {
            y = cartesianY;
        }
        
        if (Math.abs(rotation) < 0.1) {
            r = 0;
        } else {
            r = rotation;
        }
        
        driveTrain.mecanumDrive(x, y, r, 0);
        driveTrain.updateDashboard(cartesianX, cartesianY, rotation);
    }

    /**
     * Always returns false, since this is the default Command.
     * 
     * @return False
     */
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}