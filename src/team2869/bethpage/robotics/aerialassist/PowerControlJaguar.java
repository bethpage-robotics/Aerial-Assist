package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.Jaguar;

/**
 * This part of the "real world" solution for RobotDrive. A PowerControlJaguar
 * puts out less power to allow us to compensate for one wheel spinning faster
 * than another. The PowerControlJaguar also has a built in ramp-up / ramp-down
 * function that is disabled by default.
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public class PowerControlJaguar extends Jaguar {

    double reduction;
    boolean rampUp;
    double rampstep = 0.08;
    double reduceTable[];

    /**
     * Constructs a PowerControlJaguar that is wired at the specified channel, 
     * with no specified reduction (defaults to 1.0).
     * 
     * @param channel The PWM channel on the digital module that the Jaguar is
     * attached to. This functions like a regular jaguar with no power
     * reduction.
     */
    public PowerControlJaguar(int channel) {
        this(channel, 1);
        this.rampUp = false;
        reduceTable = null; //reduction factors for 10 intervals, each of length 0.2.
    }

    /**
     * Constructs a PowerControlJaguar that is wired at the specified channel, 
     * with a set reduction factor to account for mechanical differences.
     * 
     * @param channel The PWM channel on the digital module that the Jaguar is
     * attached to.
     * @param factor The amount we wish to multiply the maximum output by. For
     * instance, if factor = 0.9 then the maximum output is reduced by 10%
     */
    public PowerControlJaguar(int channel, double factor) {
        super(channel);
        this.rampUp = false;
        reduceTable = null;
        reduction = factor;
    }

    /**
     * Constructs a PowerControlJaguar that is wired at the specified channel,
     * with an array of reduction factors at different speeds.
     * 
     * @param channel The PWM channel on the digital module that the Jaguar is
     * attached to.
     * @param reduceFactors should have length 10 for the reduction factors to
     * apply for the intervals [0,0.1], [0.1, 0.2], ... [0.9, 1]
     */
    public PowerControlJaguar(int channel, double[] reduceFactors) {
        super(channel);
        this.rampUp = false;
        reduceTable = reduceFactors;
    }

    /**
     * Set the PWM value to the given input, less the reduction factor. The PWM
     * value is set using a range of -1.0 to 1.0, appropriately scaling the
     * value for the FPGA.
     *
     * @param speed The speed value between -1.0 and 1.0 to set.
     */
    public void set(double speed) {
        double v;
        int index;
        if (reduceTable == null) {
            v = reduction * speed;
        } else {
            index = (int) (Math.abs(speed) * 9.99);
            v = this.reduceTable[index] * speed;
        }
        double currentVoltage = this.get();
        if (rampUp) {
            if (Math.abs(v - currentVoltage) < 0.1) {
                super.set(v);
            } else {
                if (v > currentVoltage) {
                    currentVoltage += rampstep;
                } else {
                    currentVoltage -= rampstep;
                }
                super.set(currentVoltage);
            }
        } else {
            super.set(v);
        }
    }
    
    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public double[] getReduceTable() {
        return reduceTable;
    }

    public void setReduceTable(double[] reduceTable) {
        this.reduceTable = reduceTable;
    }
}
