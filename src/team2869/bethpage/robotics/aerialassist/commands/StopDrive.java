/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2869.bethpage.robotics.aerialassist.commands;

/**
 *
 * @author Harshil
 */
public class StopDrive extends CommandBase {
    
    public StopDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        driveTrain.mecanumDrive(0, 0, 0, 0);
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