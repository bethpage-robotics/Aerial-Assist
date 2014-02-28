package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Command that constantly runs on the launcher subsystem until anothe Command 
 * replaces it. Allows for constant winding functionality.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class AutomateWind extends CommandBase {
    
    public AutomateWind() {
        requires(launcher);
    }

    protected void initialize() {
    }
    
    /**
     * Winds the launcher while continually updating the SmartDashboard.
     */
    protected void execute() {
        launcher.constantWind();
        launcher.updateDashboard();
    }

    /**
     * Always returns false, since this is the default Command.
     * 
     * @return false
     */
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}