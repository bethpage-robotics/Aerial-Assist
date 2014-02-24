/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2869.bethpage.robotics.aerialassist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Harshil
 */

public class MecanumDrive extends CommandBase {
    
    public MecanumDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        SmartDashboard.putBoolean("Checkpoint F", true);
        driveTrain.setOrientationLeftWheels(false);
        driveTrain.setOrientationRightWheels(true);
    }

    // Called repeatedly when this Command is scheduled to run
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