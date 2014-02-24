package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Command that inverts the orientation of the robot, and is quite a deal more
 * intuitive than spinning the robot around.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class InvertedDrive extends CommandBase {
    
    /**
     * Constructs a new Command.
     */
    public InvertedDrive() {
        requires(driveTrain);
        this.setInterruptible(false);
    }

    /**
     * Changes the orientation of the motor input, so that all signals are
     * flipped, artificially creating an inverted drive state.
     */
    protected void initialize() {
        driveTrain.setOrientationLeftWheels(true);
        driveTrain.setOrientationRightWheels(false);
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