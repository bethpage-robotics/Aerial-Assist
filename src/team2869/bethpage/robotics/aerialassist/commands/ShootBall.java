package team2869.bethpage.robotics.aerialassist.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * CommandGroup that creates a sequence of commands to run during teleoperated 
 * game mode.
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class ShootBall extends CommandGroup {
    
    /**
     * Constructor to schedule the following commands to run, as part of this
     * whole CommandGroup.
     */
    public ShootBall() {
        addSequential(new DriveToExactClick());
        addSequential(new Shoot());
        addSequential(new ResetShoot());
    }
}