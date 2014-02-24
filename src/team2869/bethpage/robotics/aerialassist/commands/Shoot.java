package team2869.bethpage.robotics.aerialassist.commands;

import team2869.bethpage.robotics.aerialassist.RobotMap;

/**
 * Command that shoots the ball after winding has been completed.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class Shoot extends CommandBase {
    
    private double timeout;
    
    /**
     * Constructs a new Command with RobotMap default time to launch the shooter
     * mechanism.
     */
    public Shoot() {
        this(RobotMap.SHOOT_TIME);
    }
    
    /**
     * Constructs a new Command with a set timeout.
     * @param timeout Time in seconds that the Command will run for.
     */
    public Shoot(double timeout) {
        requires(driveTrain);
        requires(launcher);
        
        this.timeout = timeout;
        
        this.setTimeout(this.timeout);
        this.setInterruptible(false);
    }

    protected void initialize() {
    }

    /**
     * Stops the robot's movement, and if the launch subsystem is done winding,
     * then the launcher releases. If the launcher is not ready to shoot, then
     * the winding continues, and the default command is reinstantiated. The 
     * SmartDashboard is continually updated.
     */
    protected void execute() {
        driveTrain.mecanumDrive(0, 0, 0, 0);
        if (launcher.isDoneWinding()) {
            launcher.shoot();
        }
        else {
            launcher.windLaunchSystem();
            new WinderLaunch().start();
        }
        launcher.updateDashboard();
    }

    /**
     * If the time has elasped, the Command will terminate.
     * 
     * @return True if the command should terminate, and false if not.
     */
    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}