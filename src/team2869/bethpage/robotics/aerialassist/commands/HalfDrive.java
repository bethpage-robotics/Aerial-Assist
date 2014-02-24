/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2869.bethpage.robotics.aerialassist.commands;

/**
 *
 * @author Harshil
 */
public class HalfDrive extends CommandBase {
    
    public HalfDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double x = operatorInterface.getCartesianX(),
               y = operatorInterface.getCartesianY(),
               r = operatorInterface.getRotation();
        driveTrain.mecanumDrive(x/2, y/2, r/2, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}