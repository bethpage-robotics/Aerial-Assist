package team2869.bethpage.robotics.aerialassist.subsystems;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import team2869.bethpage.robotics.aerialassist.RobotMap;
import team2869.bethpage.robotics.aerialassist.MaxbotixUltrasonic;
import team2869.bethpage.robotics.aerialassist.commands.InactiveLauncher;

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
    MaxbotixUltrasonic rangefinder;
    DigitalInput limitSwitch;
    Counter counter;
    
    private double windQuadratic = RobotMap.DISTANCE_CLICK_QUADRATIC;
    private double windSlope = RobotMap.DISTANCE_CLICK_SLOPE;
    private double windIntercept = RobotMap.DISTANCE_CLICK_INTERCEPT;
    
    private double maxShootDistance = RobotMap.MAX_SHOOT_DISTANCE;
    private double minShootDistance = RobotMap.MIN_SHOOT_DISTANCE;
    
    private int currentClick = 0;
    private int targetClick = 0;
    
    private int maxClicks = RobotMap.MAX_CLICKS;
    private double clickTime = Timer.getFPGATimestamp();
    private double[] maxClickInterval = RobotMap.MAX_CLICK_INTERVAL;

    /**
     * Constructs a new Launcher, with two Talons for winding, a release relay,
     * and a limit switch for winding regulation based on input from a
     * MaxbotixUltrasonic rangefinder.
     */
    public Launcher() {
        super("Launcher");

        winderL = new Talon(RobotMap.WIND_LEFT_PORT);
        winderR = new Talon(RobotMap.WIND_RIGHT_PORT);

        releaseMotor = new Relay(RobotMap.RELAY_PORT);

        rangefinder = new MaxbotixUltrasonic(RobotMap.ULTRASONIC_RANGEFINDER);

        limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH);
        counter = new Counter(limitSwitch);

        counterInit(counter);
    }

    /**
     * Initializes the counter object.
     *
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
        setDefaultCommand(new InactiveLauncher());
    }

    public double getVoltage() {
        return rangefinder.getVoltage();
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
        releaseMotor.set(Relay.Value.kForward);
    }

    /**
     * Resets everything for the next shoot.
     */
    public void resetShoot() {
        setWindMotors(0);
        releaseMotor.set(Relay.Value.kReverse);
        counter.reset();

        currentClick = 0;
        targetClick = 0;
    }
    
    /**
     * Turns the release motor off.
     */
    public void setReleaseMotorStop() {
        releaseMotor.set(Relay.Value.kOff);
    }

    /**
     * Calculates the distance in inches or centimeters based on a linear
     * regression developed after testing the ultrasonic rangefinder.
     *
     * @return Distance in inches, if rangeUnit is true or centimeters, if
     * rangeUnit is false.
     */
    public double calculateDistance() {
        return rangefinder.getDistance();
    }

    /**
     * Calculates exact decimal number of target clicks. Used in isAbsoluteWound
     * method to drive robot until an 'acceptable' error is attained.
     *
     * @return Exact decimal number of target clicks.
     */
    private double calculateExactTargetClick() {
        double distance = rangefinder.getRangeInches();
        return windQuadratic * MathUtils.pow(distance, 2)
                + windSlope * distance + windIntercept;
    }

    /**
     * Calculates target clicks.
     *
     * @return Floor of the exact number of target clicks, will result in
     * undershooting.
     */
    public int calculateTargetClick() {
        return (int) calculateExactTargetClick();
    }

    /**
     * Decides if the exact target clicks and the currently wound click has an
     * acceptable error, to increase shooting percentage. The DriveToExactClick
     * Command will move the robot forwards until the error is minimized.
     *
     * @return True if there is an acceptable error, and false if there is not.
     */
    public boolean isAbsoluteWound() {
        if (calculateExactTargetClick() - currentClick > 0.2) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Decides if it safe to wind the mechanism. If the distance is too far, or
     * the release relay is on currently, or if the winder motors are unable to
     * wind to the next click, then winding might cause mechanical burnout or
     * issues.
     *
     * @return True if it is safe, and false if it is not.
     */
    private boolean isSafeToWind() {
        if (rangefinder.getRangeInches() > maxShootDistance) {
            return false;
        }
        if (rangefinder.getRangeInches() < minShootDistance) {
            return false;
        }

        if (releaseMotor.get() == Relay.Value.kOn) {
            return false;
        }
        
        int index;
        if (currentClick <= maxClicks) {
            index = (currentClick - 1) / 2;
        } else {
            index = maxClickInterval.length - 1;
        }

        if (winderMoving() && 
                Timer.getFPGATimestamp() - clickTime > maxClickInterval[index] &&
                currentClick != 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Winds and unwinds the system by regulating motor speeds based on the
     * current clicks in comparison to target clicks. The target clicks are set
     * once, so for an accurate shot, the robot should not move after the target
     * clicks have been calculated.
     * 
     * @param targetClicks The number of clicks to wind the robot to.
     */
    public void startFixedWind(int targetClicks) {
        setTargetClick(targetClicks);
        windToTargetClick();
    }

    /**
     * Constantly winds and unwinds the system by regulating motor speeds based
     * on the current clicks in comparison to target clicks. The target clicks
     * are constantly updated allowing for constant winding. Called by the
     * default Command, WinderLaunch, and other commands as well. Has the same
     * method body as the startdFixeWind() method, but this method is called by
     * the execute method of the commands, so the targetClicks are constantly
     * reset based on distance. This method is therefore distinguished in name
     * to improve code readibility.
     */
    public void constantWind() {
        setTargetClick(calculateTargetClick());
        windToTargetClick();
    }

    /**
     * Called by the startFixedWind() and constantWind() methods, and this
     * actually conducts the winding, based on comparison of current clicks to
     * target clicks.
     */
    public void windToTargetClick() {
        isSwitchPressed(counter);
        if (isSafeToWind()) {
            if (currentClick < targetClick) {
                setWindMotors(1);
            } else if (currentClick > targetClick) {
                setWindMotors(-1);
            } else {
                setWindMotors(0);
            }
        } else {
            setWindMotors(0);
        }
    }

    /**
     * Determines the action of the drivetrain system during autonomous game
     * mode.
     *
     * @param autonomousDistance The distance, in inches, that the robot should
     * be from the goal before shooting.
     * @return A positive value to indicate forward drive, zero to indicate stop
     * (target range achieved) and a negative value to indicate backward drive.
     */
    public int autonomousDrive(double autoDistance) {
        if (rangefinder.getRangeInches() - autoDistance > 3) {
            return 1;
        } else if (rangefinder.getRangeInches() - autoDistance < -3) {
            return -1;
        } else {
            return 0;
        }
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
    private boolean winderMoving() {
        if (winderL.get() > 0.1 || winderL.get() < -0.1) {
            return true;
        } else if (winderR.get() > 0.1 || winderR.get() < -0.1) {
            return true;
        } else {
            return false;
        }
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
        if (currentClick == targetClick && !winderMoving()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the winder motor values.
     *
     * @param value The motor speed to set both motors to.
     */
    public void setWindMotors(double value) {
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
     * The code only acknowledges the presses by ignoring all increases that are
     * less than 4.
     */
    private void isSwitchPressed(Counter counter) {
        if (counter.get() > 3) {
            if (winderL.get() <= -0.1) {
                currentClick--;
            } else if (winderL.get() >= 0.1) {
                currentClick++;
            } else {
                System.out.println("The switch should not have been pressed.");
            }

            clickTime = Timer.getFPGATimestamp();
        }
        counter.reset();
    }

    /**
     * Allows the printing of the relay's status to SmartDashboard by returning
     * a String
     *
     * @return The state of the relay: On or Off.
     */
    private String relayStatus() {
        if (releaseMotor.get() == Relay.Value.kOn) {
            return "On";
        } else {
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
        SmartDashboard.putBoolean("Range of shot", isSafeToWind());

        SmartDashboard.putNumber("Current clicks", getCurrentClick());
        SmartDashboard.putNumber("Exact target clicks", calculateExactTargetClick());
        SmartDashboard.putNumber("Target clicks", calculateTargetClick());
    }
}
