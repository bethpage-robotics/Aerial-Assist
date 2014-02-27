package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * This Ultrasonic class stores the linear regression to transform voltages into
 * distances.
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class MaxbotixUltrasonic extends AnalogChannel {
    
    /**
     * This class stores the Unit to return when the getDistance() method is 
     * called.
     */
    public static class Unit {
        
        public final int value;
        static final int kInches_val = 0;
        static final int kCentimeters_val = 1;

        public static final Unit kInches = new Unit(kInches_val);

        public static final Unit kCentimeter = new Unit(kCentimeters_val);
        
        /**
         * Constructs the Unit for this Ultrasonic rangefinder.
         * 
         * @param value Zero represents inches, and one represents millimeters.
         */
        private Unit(int value) {
            this.value = value;
        }
    }
    private double distanceSlope = RobotMap.VOLT_DISTANCE_SLOPE;
    private double distanceIntercept = RobotMap.VOLT_DISTANCE_INTERCEPT;
    
    private Unit unit = Unit.kInches;

    /**
     * Constructs an Ultrasonic rangefinder that is wired at the specific
     * channel
     *
     * @param channel The analog breakout board channel that the rangefinder is
     * connected to.
     */
    public MaxbotixUltrasonic(int channel) {
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
    public MaxbotixUltrasonic(int moduleNumber, int channel) {
        super(moduleNumber, channel);
    }

    public Unit getUnits() {
        return unit;
    }

    public void setUnits(Unit unit) {
        this.unit = unit;
    }

    /**
     * Calculates the distance in inches or centimeters based on a linear
     * regression developed after testing the ultrasonic rangefinder.
     *
     * @return Distance based on the value of the unit.
     */
    public double getDistance() {
        switch (unit.value) {
            case Unit.kInches_val:
                return getRangeInches();
            case Unit.kCentimeters_val:
                return getRangeCentimeters();
            default:
                return 0.0;
        }
    }
    
    /**
     * Calculates the distance in inches or centimeters based on a linear
     * regression developed after testing the ultrasonic rangefinder.
     * 
     * @return Distance in inches regardless of the value of the unit.
     */
    public double getRangeInches() {
        return getVoltage() * distanceSlope + distanceIntercept;
    }
    
    /**
     * Calculates the distance in inches or centimeters based on a linear
     * regression developed after testing the ultrasonic rangefinder.
     * 
     * @return Distance in centimeters regardless of the value of the unit.
     */
    public double getRangeCentimeters() {
        return RobotMap.INCHES_CM_CONVERSION * getRangeInches();
    }
}
