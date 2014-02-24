/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.aerialassist.commands;

/**
 *
 * @author Harshil
 */
public class AutonomousDrive extends CommandBase {
    private double drivepoint;
    
    public AutonomousDrive(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
        drivepoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        driveTrain.setSetpoint(drivepoint);
        driveTrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(driveTrain.getPosition() - drivepoint) < .02;
    }

    // Called once after isFinished returns true
    protected void end() {
        driveTrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        driveTrain.disable();
    }
}