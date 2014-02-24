package team2869.bethpage.robotics.aerialassist;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // Joysticks: This is the mapping of the joystick ports in the computer.
    public static final int MECANUM_STICK_PORT = 1,
                            ROTATE_STICK_PORT = 2;
    
    
    // Motors: This is the mapping of motors ports marked PWM on the DIO board
    public static final int FRONT_LEFT_PORT = 2,
                            FRONT_RIGHT_PORT = 1,
                            REAR_LEFT_PORT = 4,
                            REAR_RIGHT_PORT = 3;
    
    public static final int WIND_LEFT_PORT = 5,
                            WIND_RIGHT_PORT = 6;
    
    // Sensors: This is the mapping of the analog ports on the analog module
    // to the relevant sensors.
    public static final int ULTRASONIC_RANGEFINDER = 1,
                            ENCODER_LEFT = 7,
                            ENCODER_RIGHT = 8;
    
    // Buttons: This is the mapping of the buttons on the main joystick.
    public static final int SPIN_BUTTON = 2,
                            STOP_BUTTON = 3,
                            HALF_BUTTON = 4;
    
    public static final int TURN_RIGHT_BUTTON = 5,
                            TURN_LEFT_BUTTON = 6,
                            TURN_BACK_BUTTON = 7;
    
    public static final int INVERTED_DRIVE_BUTTON = 8;
                            
}
