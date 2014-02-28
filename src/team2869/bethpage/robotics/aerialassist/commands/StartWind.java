package team2869.bethpage.robotics.aerialassist.commands;

import team2869.bethpage.robotics.aerialassist.RobotMap;

/**
 * D
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class StartWind extends CommandBase {
    private double timeout;
    
    public StartWind() {
        this(RobotMap.STARTWIND_TIMEOUT_TIME);
    }
    
    public StartWind(double timeout) {
        requires(launcher);
        
        this.timeout = timeout;
        this.setTimeout(this.timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        launcher.startFixedWind(launcher.calculateTargetClick());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        launcher.windToTargetClick();
        launcher.updateDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }
    
    protected void interrupted() {
    }
}