package team2869.bethpage.robotics.aerialassist;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring and also makes 
 * checking the wiring easier.
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
    
    public static final int RELAY_PORT = 1;
    
    // Sensors: This is the mapping of the analog ports on the analog module
    // to the relevant sensors.
    public static final int ULTRASONIC_RANGEFINDER = 1,
                            LIMIT_SWITCH = 1;
    
    // Buttons: This is the mapping of the buttons on the main joystick.
    public static final int SPIN_BUTTON = 2,
                            STOP_BUTTON = 3,
                            HALF_BUTTON = 4;
    
    public static final int TURN_RIGHT_BUTTON = 5,
                            TURN_LEFT_BUTTON = 6,
                            TURN_BACK_BUTTON = 7;
    
    public static final int INVERTED_DRIVE_BUTTON = 8;
    
    public static final int AUTOMATEWIND_BUTTON = 9;
    public static final int STARTWIND_BUTTON = 10;
    public static final int OPERATORWIND_BUTTON = 11;
    public static final int SHOOTBALL_BUTTON = 12;
    
    // Constants: This is the mapping of all required constants.
    public static final double VOLT_DISTANCE_SLOPE = .0096560282;
    public static final double VOLT_DISTANCE_INTERCEPT = -.0015728398;
    
    public static final double DISTANCE_CLICK_QUADRATIC = .5;
    public static final double DISTANCE_CLICK_SLOPE = .5;
    public static final double DISTANCE_CLICK_INTERCEPT = .5;
    
    public static final double INCHES_CM_CONVERSION = 2.54;
    
    public static final double AUTONOMOUS_DISTANCE = 72;
    public static final double AUTONOMOUS_X_SPEED = 0.1;
    public static final double AUTONOMOUS_Y_SPEED = 0.5;
    public static final double AUTONOMOUS_WAIT_TIME = 2;
    
    public static final double MAX_SHOOT_DISTANCE = 192;
    public static final double MIN_SHOOT_DISTANCE = 48;
    
    public static final int MAX_CLICKS = 12;
    public static final double [] MAX_CLICK_INTERVAL = {0.5,0.6,0.7,0.8,0.9,1};
    
    public static final double SHOOT_TIME = 3;
    public static final double RESET_TIME = 0.5;
    
    public static final double STARTWIND_TIMEOUT_TIME = 5;
    public static final double OPERATORWIND_TIMEOUT_TIME = 5;
    
    // Image constants: All constants used in image processing.
    
    public static final int Y_IMAGE_RES = 480;
    public static final double VIEW_ANGLE = 49;
    public static final int RECTANGULARITY_LIMIT = 40;
    public static final int ASPECT_RATIO_LIMIT = 55;
    public static final int TAPE_WIDTH_LIMIT = 40;
    public static final int VERTICAL_SCORE_LIMIT = 50;
    public static final int LR_SCORE_LIMIT = 50;
    public static final int AREA_MINIMUM = 150;
    public static final int MAX_PARTICLES = 8;
                            
}
