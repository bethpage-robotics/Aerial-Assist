package team2869.bethpage.robotics.aerialassist.subsystems;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import team2869.bethpage.robotics.aerialassist.RobotMap;
import team2869.bethpage.robotics.aerialassist.commands.WinderLaunch;

/**
 * This class extends Subsystem, and controls the launcher mechanism on the
 * robot. It consists of two Talon instances for the two winding motors on the 
 * physical launcher, as well as a release relay to shoot the ball. A limit 
 * switch regulates the winding and unwinding of this subsystem. The subsystem 
 * frequently updates to the SmartDashboard.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class Launcher extends Subsystem {
    
    SpeedController winderL, winderR;
    Relay releaseMotor;
    AnalogChannel rangefinder;
    DigitalInput limitSwitch;
    Counter counter;
    
    private boolean rangeUnit = true;
    private double rangeSlope = RobotMap.VOLT_DISTANCE_SLOPE;
    private double rangeIntercept = RobotMap.VOLT_DISTANCE_INTERCEPT;
   
    private double windQuadratic = RobotMap.DISTANCE_CLICK_QUADRATIC;
    private double windSlope = RobotMap.DISTANCE_CLICK_SLOPE;
    private double windIntercept = RobotMap.DISTANCE_CLICK_INTERCEPT;
    
    private final double inchesToCentimeters = RobotMap.INCHES_CM_CONVERSION;
    
    private double maxShootDistance = RobotMap.MAX_SHOOT_DISTANCE;
    private double minShootDistance = RobotMap.MIN_SHOOT_DISTANCE;
   
    private int currentClick = 0; 
    private int targetClick = 0;
    
    /**
     * Constructs a new Launcher, with two Talons for winding, a release
     * relay, and a limit switch for winding regulation based on input from
     * a Maxbotix Ultrasonic rangefinder.
     */
    public Launcher () {
        super("Launcher");
       
        winderL = new Talon(RobotMap.WIND_LEFT_PORT);
        winderR = new Talon(RobotMap.WIND_RIGHT_PORT);
        
        releaseMotor = new Relay(RobotMap.RELAY_PORT);
        
        rangefinder = new AnalogChannel(RobotMap.ULTRASONIC_RANGEFINDER);
        
        limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH);
        counter = new Counter(limitSwitch);
        
        counterInit(counter);
    }
    
    /**
     * Initializes the counter object.
     * @param counter The counter reference.
     */
    private void counterInit(Counter counter) {
        counter.start();
        counter.reset();
    }
    
    /**
     * Initializes the default command that always runs on the Launcher, the
     * WinderLaunch Command.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new WinderLaunch());
    }
    
    public double getVoltage() {
        return rangefinder.getVoltage();
    }

    public boolean getRangeUnit() {
        return rangeUnit;
    }

    public void setRangeUnit(boolean rangeUnit) {
        this.rangeUnit = rangeUnit;
    }

    public int getTargetClick() {
        return targetClick;
    }

    public void setTargetClick(int targetClick) {
        this.targetClick = targetClick;
    }
    
    public int getCurrentClick() {
        return currentClick;
    }
    
    /**
     * Shoots the ball out of the winding mechanism, by turning the release
     * mechanism off.
     */
    public void shoot() {
        setWindMotors(0);
        releaseMotor.set(Relay.Value.kOn);
    }
    
    /**
     * Resets everyhting for the next shoot.
     */
    public void resetShoot() {
        setWindMotors(0);
        releaseMotor.set(Relay.Value.kOff);
        counter.reset();
        
        currentClick = 0;
        targetClick = 0;
    }
        
    /**
     * Calculates the distance in inches or centimeters based on a linear
     * regression developed after testing the ultrasonic rangefinder.
     * 
     * @return Distance in inches, if rangeUnit is true or centimeters, if 
     * rangeUnit is false.
     */
    public double calculateDistance() {
        double distance = getVoltage()*rangeSlope + rangeIntercept;
        if (rangeUnit)
            return distance;
        else
            return inchesToCentimeters*distance;
    }
    
    /**
     * Calculates exact decimal number of target clicks. Used in isAbsoluteWound
     * method to drive robot until an 'acceptable' error is attained.
     * 
     * @return Exact decimal number of target clicks.
     */
    private double calculateExactTargetClick() {
        double distance;
        
        if (rangeUnit)
            distance = calculateDistance();
        else
            distance = calculateDistance()/inchesToCentimeters;
        
        return windQuadratic*MathUtils.pow(distance, 2) + 
               windSlope*distance + windIntercept;
    }
    
    /**
     * Calculates target clicks.
     * 
     * @return Floor of the exact number of target clicks, will result in 
     * undershooting.
     */
    private int calculateTargetClick() {
        return (int) calculateExactTargetClick();
    }
    
    /**
     * Decides if the exact target clicks and the currently wound click has an 
     * acceptable error, to increase shooting percentage. The DriveToExactClick
     * Command will move the robot forwards until the error is minimized.
     * 
     * @return True if there is an acceptable error, and false if there is not.
     */
    public boolean isAbsoluteWound () {
        if (calculateExactTargetClick() - currentClick > 0.2)
            return false;
        else
            return true;
    }
    
    /**
     * Decides if it safe to wind the mechanism. If the distance is too far,
     * or the release relay is on currently, then winding might cause mechanical
     * burnout or issues.
     * 
     * @return True if it is safe, and false if it is not.
     */
    private boolean isSafeToWind () {
        double maxDistance, minDistance;
        
        if (rangeUnit) {
            maxDistance = maxShootDistance;
            minDistance = minShootDistance;
        } else {
            maxDistance = maxShootDistance*inchesToCentimeters;
            minDistance = minShootDistance*inchesToCentimeters;
        }
        
        if (calculateDistance() > maxDistance)
            return false;
        if (calculateDistance() < minDistance)
            return false;
        if (releaseMotor.get() == Relay.Value.kOn) 
            return false;
        
        return true;
    }
    
    /**
     * Constantly winds and unwinds the system by regulating motor speeds based
     * on the current clicks in comparison to target clicks. Called by the
     * default Command, WinderLaunch, and other commands as well.
     */
    public void windLaunchSystem() {
        isSwitchPressed(counter);
        setTargetClick(calculateTargetClick());
        
        if (isSafeToWind()) {
            if (currentClick < targetClick)
                setWindMotors(1);
            else if (currentClick > targetClick)
                setWindMotors(-1);
            else
                setWindMotors(0);
        }
    }
    
    /**
     * Determines the action of the drivetrain system during autonomous 
     * game mode.
     * 
     * @param autonomousDistance The distance, in inches, that the robot should 
     * be from the goal before shooting.
     * @return A positive value to indicate forward drive, zero to indicate stop 
     * (target range achieved) and a negative value to indicate backward drive.
     */
    public int autonomousDrive(double autoDistance) {
        double autonomousDistance;
        
        if (rangeUnit)
            autonomousDistance = autoDistance;
        else
            autonomousDistance = autoDistance*inchesToCentimeters;
        
        if (calculateDistance() - autonomousDistance > 3)
            return 1;
        else if (calculateDistance() - autonomousDistance < -3)
            return -1;
        else 
            return 0;
    }
    
    /**
     * Determines which of the two goals is currently lit (at 0 seconds), and 
     * proceeds to the other goal. Method is currently incomplete.
     * 
     * @return A positive value to indicate that to proceed to the right goal,
     * and a negative value to proceed to the left goal.
     */
    public int autonomousDirection() {
        return 1;
    }
    
    /**
     * Decides if the winders are moving by analyzing if they are within an 
     * acceptable speed range.
     * 
     * @return True if either of the winders are moving, and false if not.
     */
    private boolean winderMoving () {
        if (winderL.get() > 0.1 && winderL.get() < -0.1)
            return true;
        else if (winderR.get() > 0.1 && winderR.get() < -0.1)
            return true;
        else
            return false;
    }    
    /**
     * Decides if the mechanism is done winding. The winding motors must be set
     * to zero, if currentClicks equals targetClicks. Safety check, since if the
     * shooting mechanism is called, the other Commands would have successfully
     * wound the mechanism by now.
     * 
     * @return True if the mechanism is ready to shoot, and false if it is not.
     */
    public boolean isDoneWinding() {
        if (currentClick == targetClick && !winderMoving())
            return true;
        else
            return false;
    }
    
    /**
     * Sets the winder motor values.
     * @param value The motor speed to set both motors to.
     */
    private void setWindMotors(double value) {
        winderL.set(value);
        winderR.set(value);
    }
    
    /**
     * Measures a physical switch press and translates it to virtual meaning. If
     * the winder motor is moving in the negative direction, the mechanism is 
     * unwinding and so the switch press count should decrease, and if the motor
     * is moving in the positive direction, the mechanism is winding, and thus 
     * the switch press count should increase.
     * 
     * @param counter The Counter object that represents the physical limit
     * switch virtually. A physical switch press increments the counter value
     * twice: once on the press (by 4 - 10) and once on the release (by 1 - 3).
     * The code only acknowledges the presses by ignoring all increases that
     * are less than 4.
     */
    private void isSwitchPressed(Counter counter) {
        if (counter.get() > 3) {
            if (winderL.get() <= -0.1) {
                currentClick--;
            }
            else if (winderL.get() >= 0.1) {
                currentClick++;
            }
            else {
                System.out.println("The switch should not have been pressed.");
            }
            counter.reset();
        }
    }
    
    /**
     * Allows the printing of the relay's status to SmartDashboard by returning
     * a String
     * @return The state of the relay: On or Off.
     */
    private String relayStatus() {
        if (releaseMotor.get() == Relay.Value.kOn) {
            return "On";
        }
        else {
            return "Off";
        }
    }
    
    /**
     * Updates the SmartDashboard with winder motor values, relay status, the
     * distance to wall, and the state of the launcher mechanism.
     */
    public void updateDashboard() {
        SmartDashboard.putNumber("Winder left", winderL.get());
        SmartDashboard.putNumber("Winder right", winderR.get());
        SmartDashboard.putString("Relay", relayStatus());
        
        SmartDashboard.putNumber("Rangefinder voltage", getVoltage());
        SmartDashboard.putNumber("Rangefinder distance", calculateDistance());
        
        SmartDashboard.putNumber("Current clicks", getCurrentClick());
        SmartDashboard.putNumber("Exact target clicks", calculateExactTargetClick());
        SmartDashboard.putNumber("Target clicks", calculateTargetClick());
    }
}