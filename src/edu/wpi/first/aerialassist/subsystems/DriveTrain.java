package edu.wpi.first.aerialassist.subsystems;

import edu.wpi.first.aerialassist.RobotMap;
import edu.wpi.first.aerialassist.commands.MecanumDrive;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public class DriveTrain extends PIDSubsystem {

    private static final double Kp = 0.0;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    private SpeedController frontL = new Jaguar(RobotMap.FRONT_LEFT);
    private SpeedController rearL = new Jaguar(RobotMap.REAR_LEFT);
    private SpeedController frontR = new Jaguar(RobotMap.FRONT_RIGHT);
    private SpeedController rearR = new Jaguar(RobotMap.REAR_RIGHT);
    private RobotDrive drive = new RobotDrive(frontL, rearL, frontR, rearR);
    private Ultrasonic rangefinder = new Ultrasonic(RobotMap.ULTRASONIC_PING_CHANNEL, RobotMap.ULTRASONIC_ECHO_CHANNEL);
    private Gyro gyroscope = new Gyro(RobotMap.ANALOG_GYRO_CHANNEL);

    public DriveTrain() {
        super("DriveTrain", Kp, Ki, Kd);
        rangefinder.setEnabled(true);
        rangefinder.setAutomaticMode(true);
        rangefinder.setDistanceUnits(Ultrasonic.Unit.kInches);
        gyroscope.reset();
    }

    public void initDefaultCommand() {
        setDefaultCommand(new MecanumDrive());
    }

    protected double returnPIDInput() {
        return rangefinder.getRangeInches();
    }

    protected void usePIDOutput(double output) {
        this.mecanumDrive(0, output, 0, 0);
    }

    public void setAllWheels(double speed) {
        frontL.set(speed);
        rearL.set(speed);
        frontR.set(speed);
        rearR.set(speed);
    }

    public void mecanumDrive(double x, double y, double rotation, double gyroAngle) {
        drive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
    }

    public void setOrientation(boolean motorDirection) {
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, motorDirection);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, motorDirection);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, motorDirection);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, motorDirection);
    }

    public void updateDashboard() {
        if (this.getCurrentCommand().getName().equals("InvertedDrive")) {
            SmartDashboard.putNumber("Front left", -frontL.get());
            SmartDashboard.putNumber("Front right", -frontR.get());
            SmartDashboard.putNumber("Rear left", -rearL.get());
            SmartDashboard.putNumber("Rear right", -rearR.get());
        } else {
            SmartDashboard.putNumber("Front left", frontL.get());
            SmartDashboard.putNumber("Front right", frontR.get());
            SmartDashboard.putNumber("Rear left", rearL.get());
            SmartDashboard.putNumber("Rear right", rearR.get());
        }
        SmartDashboard.putNumber("Ultrasonic", rangefinder.getRangeInches());
    }
}