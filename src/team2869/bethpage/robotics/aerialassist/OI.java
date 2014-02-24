
package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import team2869.bethpage.robotics.aerialassist.commands.HalfDrive;
import team2869.bethpage.robotics.aerialassist.commands.InvertedDrive;
import team2869.bethpage.robotics.aerialassist.commands.SpinDrive;
import team2869.bethpage.robotics.aerialassist.commands.StopDrive;
import team2869.bethpage.robotics.aerialassist.commands.Turn;

/**
 * The operator interface class ties the commands that have been implemented
 * to the physical controls of the user. This allows the binding of the same
 * command that executes autonomous to a button. 
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public class OI {
    //Create the joysticks and the buttons
    Joystick mecanumStick = new Joystick(RobotMap.MECANUM_STICK_PORT);
    Joystick rotateStick = new Joystick(RobotMap.ROTATE_STICK_PORT);
    
    Button rotateButton = new JoystickButton(mecanumStick, RobotMap.SPIN_BUTTON),
           stopButton = new JoystickButton(mecanumStick, RobotMap.STOP_BUTTON),
           halfButton = new JoystickButton(mecanumStick, RobotMap.HALF_BUTTON);
    Button turnRightButton = new JoystickButton(mecanumStick, RobotMap.TURN_RIGHT_BUTTON),
           turnLeftButton = new JoystickButton(mecanumStick, RobotMap.TURN_LEFT_BUTTON),
           turnBackButton = new JoystickButton(mecanumStick, RobotMap.TURN_BACK_BUTTON);
    Button invertedButton = new JoystickButton(mecanumStick, RobotMap.INVERTED_DRIVE_BUTTON);
    
    public OI () {
        SmartDashboard.putBoolean("Checkpoint C", true);
        
        rotateButton.whileHeld(new SpinDrive());
        stopButton.toggleWhenPressed(new StopDrive());
        halfButton.toggleWhenPressed(new HalfDrive());
        
        turnRightButton.whenPressed(new Turn(0.5, true));
        turnLeftButton.whenPressed(new Turn(0.5, false));
        turnBackButton.whenPressed(new Turn(1, true));
        
        invertedButton.toggleWhenPressed(new InvertedDrive());
    }
    
    public double getCartesianX() {
        return mecanumStick.getX();
    }
    
    public double getCartesianY() {
        return mecanumStick.getY();
    }
    
    public double getRotation() {
        return rotateStick.getX();
    }
}

