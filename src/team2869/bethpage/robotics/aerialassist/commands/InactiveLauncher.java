package team2869.bethpage.robotics.aerialassist.commands;

/**
 * Default Command that runs on the Launcher subsystem, that ensures that the
 * launcher subsystem is not winding until other commands are instantiated.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class InactiveLauncher extends CommandBase {
    
    public InactiveLauncher() {
        requires(launcher);
    }

    /**
     * Acts as a secondary reset after a shot, by stopping the release motors
     * and ensuring that the winding motors are off.
     */
    protected void initialize() {
        launcher.setReleaseMotorStop();
        launcher.setWindMotors(0);
    }

    protected void execute() {
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