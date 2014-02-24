/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.aerialassist.commands;

/**
 *
 * @author Harshil
 */
public class InvertedDrive extends CommandBase {
    
    public InvertedDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        driveTrain.setOrientation(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double cartesianX = operatorInterface.getCartesianX(),
               cartesianY = operatorInterface.getCartesianY(),
               rotation = operatorInterface.getRotation();
        boolean zeroX = Math.abs(cartesianX) < 0.1, 
                zeroY = Math.abs(cartesianY) < 0.1, 
                zeroSpin = Math.abs(rotation) < 0.1;
        if (zeroX && zeroY && zeroSpin) {
            driveTrain.mecanumDrive(0, 0, 0, 0);
        } else if (!zeroX && zeroY && zeroSpin) {
            driveTrain.mecanumDrive(cartesianX, 0, 0, 0);
        } else if (zeroX && !zeroY && zeroSpin) {
            driveTrain.mecanumDrive(0, cartesianY, 0, 0);
        } else if (zeroX && zeroY && !zeroSpin) {
            driveTrain.mecanumDrive(0, 0, rotation, 0);
        } else if (!zeroX && !zeroY && zeroSpin) {
            driveTrain.mecanumDrive(cartesianX, cartesianY, 0, 0);
        } else if (!zeroX && zeroY && !zeroSpin) {
            driveTrain.mecanumDrive(cartesianX, 0, rotation, 0);
        } else if (zeroX && !zeroY && !zeroSpin) {
            driveTrain.mecanumDrive(0, cartesianY, rotation, 0);
        } else {
            driveTrain.mecanumDrive(cartesianX, cartesianY, rotation, 0);
        }
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