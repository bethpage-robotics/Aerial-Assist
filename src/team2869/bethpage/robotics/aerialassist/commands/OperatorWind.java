package team2869.bethpage.robotics.aerialassist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2869.bethpage.robotics.aerialassist.RobotMap;

/**
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class OperatorWind extends CommandBase {
    private int operatorClicks;
    private double timeout;
    
    public OperatorWind() {
        this(RobotMap.OPERATORWIND_TIMEOUT_TIME, 
                (int) SmartDashboard.getNumber("Operator Clicks", 0));
    }
     
    public OperatorWind(double timeout) {
        this(timeout, (int) SmartDashboard.getNumber("Operator Clicks", 0));
    }
    
    public OperatorWind(double timeout, int operatorClicks) {
        requires(launcher);
        
        this.operatorClicks = operatorClicks;
        this.timeout = timeout;
        this.setTimeout(this.timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        launcher.startFixedWind(operatorClicks);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        launcher.windToTargetClick();
        launcher.updateDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || operatorClicks == 0;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}