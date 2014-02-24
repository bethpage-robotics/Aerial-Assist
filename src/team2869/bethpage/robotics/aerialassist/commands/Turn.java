/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2869.bethpage.robotics.aerialassist.commands;

/**
 *
 * @author Harshil
 */
public class Turn extends CommandBase {
    
    private double timeout;
    private boolean direction;
    
    
    public Turn() {
        // Use requires() here to declare subsystem dependencies
        this(0.5, true);
    }
    
    public Turn(double timeout) {
        this(timeout, true);
    }
    
    public Turn(double timeout, boolean direction) {
        requires(driveTrain);
        this.setInterruptible(false);
        
        this.timeout = timeout;
        this.direction = direction;
        
        this.setTimeout(this.timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (direction)
            driveTrain.mecanumDrive(0, 0, 0.5, 0);
        else
            driveTrain.mecanumDrive(0, 0, -0.5, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}