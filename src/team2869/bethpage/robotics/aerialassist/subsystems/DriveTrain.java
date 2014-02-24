package team2869.bethpage.robotics.aerialassist.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import team2869.bethpage.robotics.aerialassist.PowerControlJaguar;
import team2869.bethpage.robotics.aerialassist.RobotMap;
import team2869.bethpage.robotics.aerialassist.commands.MecanumDrive;

/**
 * This class extends Subsystem, and controls the drivetrain mechanism on the
 * robot. It consists of four PowerControlJaguar instances for the four motors
 * on the physical drivetrain. The RobotDrive class is utilized to implement
 * the mecanum drive functionality easily. By default the orientation of the 
 * right wheels are set to be inverted, and the left wheels are not inverted.
 * The subsystem frequently updates to the SmartDashboard.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class DriveTrain extends Subsystem {

    RobotDrive drive;
    PowerControlJaguar frontLeft,
                frontRight,
                rearLeft,
                rearRight;
    
    /**
     * Constructs a new DriveTrain, with four PowerControlJaguars for each
     * wheel that are grouped into a RobotDrive class.
     */
    public DriveTrain() {
        super("DriveTrain");
        
        frontLeft = new PowerControlJaguar(RobotMap.FRONT_LEFT_PORT);
        frontRight = new PowerControlJaguar(RobotMap.FRONT_RIGHT_PORT);
        rearLeft = new PowerControlJaguar(RobotMap.REAR_LEFT_PORT);
        rearRight = new PowerControlJaguar(RobotMap.REAR_RIGHT_PORT);

        drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
    }
    
    /**
     * Initializes the default command that always runs on the DriveTrain, the
     * MecanumDrive Command.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new MecanumDrive());
    }
    
    /**
     * Implements the mecanumDrive function of the RobotDrive class to drive the
     * robot.
     * 
     * @param x The speed of the x directional movement.
     * @param y The speed of the y directional movement.
     * @param rotation The speed of the rotational movement.
     * @param gyroAngle Adjustment for field-centric driving. Should be zero for
     * normal mecanum driving.
     */
    public void mecanumDrive(double x, double y, double rotation, double gyroAngle) {
        drive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
    }
    
    /**
     * Sets the orientation of the right wheels, accounting for wiring issues.
     * 
     * @param motorDirection If true, the motors will be inverted, and if false
     * they will not be inverted.
     */
    public void setOrientationRightWheels(boolean motorDirection) {
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, motorDirection);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, motorDirection);
    }
    
    /**
     * Sets the orientation of the left wheels, accounting for wiring issues.
     * 
     * @param motorDirection If true, the motors will be inverted, and if false
     * they will not be inverted.
     */
    public void setOrientationLeftWheels(boolean motorDirection) {
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, motorDirection);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, motorDirection);
    }
    
    /**
     * Updates the SmartDashboard with joystick values and motor speeds.
     * 
     * @param cartesianX The primary joystick x value 
     * @param cartesianY The primary joystick y value
     * @param rotation The secondary joystick x value
     */
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