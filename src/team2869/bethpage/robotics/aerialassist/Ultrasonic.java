package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * This Ultrasonic class stores the linear regression to transform voltages into
 * distances.
 * NOTE: Consider renaming this class to avoid confusion with the WPILib class with the same name.
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class Ultrasonic extends AnalogChannel {

    private double distanceSlope = RobotMap.VOLT_DISTANCE_SLOPE;
    private double distanceIntercept = RobotMap.VOLT_DISTANCE_INTERCEPT;
    private edu.wpi.first.wpilibj.Ultrasonic.Unit units = edu.wpi.first.wpilibj.Ultrasonic.Unit.kInches;

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

   public edu.wpi.first.wpilibj.Ultrasonic.Unit getUnits() {
      return units;
   }

   public void setUnits(edu.wpi.first.wpilibj.Ultrasonic.Unit units) {
      if(this.units == units)
         return;
      //we are changing units, so adjust the slope to convert from cm to inches or incshes to cm.
      if(this.units == edu.wpi.first.wpilibj.Ultrasonic.Unit.kInches) 
         distanceSlope *= 2.54; //now we are in cm.
      else
         distanceSlope /= 2.54; //now we are in inches.
      
      this.units = units;
   }
    
    /**
     * Calculates the distance in inches or centimeters based on a linear
     * regression developed after testing the ultrasonic rangefinder.
     * 
     * @return Distance in the whichever units the distanceSlope and distanceIntercept have been calibrated for.
     */
    public double getDistance() {
        return getVoltage() * distanceSlope + distanceIntercept;
    }
}
