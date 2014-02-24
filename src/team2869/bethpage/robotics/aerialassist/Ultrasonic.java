package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * This Ultrasonic class stores the linear regression to transform voltages into
 * distances.
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class Ultrasonic extends AnalogChannel {

    private double distanceSlope = RobotMap.VOLT_DISTANCE_SLOPE;
    private double distanceIntercept = RobotMap.VOLT_DISTANCE_INTERCEPT;

    /**
     * Constructs an Ultrasonic rangefinder that is wired at the specific
     * channel
     *
     * @param channel The analog breakout board channel that the rangefinder is
     * connected to.
     */
    public Ultrasonic(int channel) {
        super(channel);
    }

    /**
     * Constructs an Ultrasonic rangefinder that is wired at the specific module
     * number and channel.
     *
     * @param moduleNumber The module of the analog breakout board.
     * @param channel The analog breakout board channel that the rangefinder is
     * connected to.
     */
    public Ultrasonic(int moduleNumber, int channel) {
        super(moduleNumber, channel);
    }
    
    /**
     * Finds the voltage of the Ultrasonic rangefinder. Voltage scales with
     * increasing distance.
     * 
     * @return A voltage from 0V to approximately 2.5V.
     */
    public double getVoltage() {
        return super.getVoltage();
    }
    
    /**
     * Calculates the distance in inches or centimeters based on a linear
     * regression developed after testing the ultrasonic rangefinder.
     * 
     * @return Distance in inches.
     */
    public double getDistance() {
        return getVoltage() * distanceSlope + distanceIntercept;
    }
}
