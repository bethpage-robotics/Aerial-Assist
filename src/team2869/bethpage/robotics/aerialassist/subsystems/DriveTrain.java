/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2869.bethpage.robotics.aerialassist.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import team2869.bethpage.robotics.aerialassist.PowerControlJaguar;
import team2869.bethpage.robotics.aerialassist.RobotMap;
import team2869.bethpage.robotics.aerialassist.commands.MecanumDrive;

/**
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public class DriveTrain extends Subsystem {

    RobotDrive drive;
    PowerControlJaguar frontLeft,
                frontRight,
                rearLeft,
                rearRight;

    public DriveTrain() {
        super("DriveTrain");
        
        SmartDashboard.putBoolean("Checkpoint D", true);

        frontLeft = new PowerControlJaguar(RobotMap.FRONT_LEFT_PORT);
        frontRight = new PowerControlJaguar(RobotMap.FRONT_RIGHT_PORT);
        rearLeft = new PowerControlJaguar(RobotMap.REAR_LEFT_PORT);
        rearRight = new PowerControlJaguar(RobotMap.REAR_RIGHT_PORT);

        drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
    }

    public void initDefaultCommand() {
        SmartDashboard.putBoolean("Checkpoint E", true);
        setDefaultCommand(new MecanumDrive());
    }

    public void mecanumDrive(double x, double y, double rotation, double gyroAngle) {
        SmartDashboard.putBoolean("Checkpoint G", true);
        drive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
    }

    public void setOrientationRightWheels(boolean motorDirection) {
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, motorDirection);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, motorDirection);
    }

    public void setOrientationLeftWheels(boolean motorDirection) {
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, motorDirection);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, motorDirection);
    }
    
    public void updateDashboard(double cartesianX, double cartesianY, double rotation) {
        SmartDashboard.putNumber("X-AXIS", cartesianX);
        SmartDashboard.putNumber("Y-AXIS", cartesianY);
        SmartDashboard.putNumber("SPIN-AXIS", rotation);
        
        if (this.getCurrentCommand().getName().equals("InvertedDrive")) {
            SmartDashboard.putNumber("Front left", -frontLeft.get());
            SmartDashboard.putNumber("Front right", -frontRight.get());
            SmartDashboard.putNumber("Rear left", -rearLeft.get());
            SmartDashboard.putNumber("Rear right", -rearRight.get());
        } else {
            SmartDashboard.putNumber("Front left", frontLeft.get());
            SmartDashboard.putNumber("Front right", frontRight.get());
            SmartDashboard.putNumber("Rear left", rearLeft.get());
            SmartDashboard.putNumber("Rear right", rearRight.get());
        }
    }
}