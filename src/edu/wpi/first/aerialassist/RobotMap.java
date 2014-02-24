package edu.wpi.first.aerialassist;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int FRONT_LEFT = 1;
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    public static final int REAR_LEFT = 2;
    public static final int FRONT_RIGHT = 3;
    public static final int REAR_RIGHT = 4;
    public static final int MECANUMSTICK_PORT = 1;
    public static final int ROTATESTICK_PORT = 2;
    public static final int SPIN_BUTTON = 3;
    public static final int STOP_BUTTON = 4;
    public static final int HALF_BUTTON = 5;
    public static final int LEFT_BUTTON = 6, RIGHT_BUTTON = 7, BACK_BUTTON = 8;
    public static final int ULTRASONIC_PING_CHANNEL = 1;
    public static final int ULTRASONIC_ECHO_CHANNEL = 2;
    public static final int ANALOG_GYRO_CHANNEL = 1;
    public static final int INVERTED_BUTTON = 9;
}
