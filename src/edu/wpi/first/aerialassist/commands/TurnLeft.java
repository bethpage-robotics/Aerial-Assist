/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.aerialassist.commands;

/**
 *
 * @author Harshil
 */
public class TurnLeft extends CommandBase {
    
    public TurnLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
         requires(driveTrain);
         setTimeout(0.4);
        this.setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        driveTrain.mecanumDrive(0, 0, -1, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}