package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team2869.bethpage.robotics.aerialassist.commands.AutomateWind;

import team2869.bethpage.robotics.aerialassist.commands.HalfDrive;
import team2869.bethpage.robotics.aerialassist.commands.InvertedDrive;
import team2869.bethpage.robotics.aerialassist.commands.OperatorWind;
import team2869.bethpage.robotics.aerialassist.commands.ShootBall;
import team2869.bethpage.robotics.aerialassist.commands.SpinDrive;
import team2869.bethpage.robotics.aerialassist.commands.StartWind;
import team2869.bethpage.robotics.aerialassist.commands.StopDrive;
import team2869.bethpage.robotics.aerialassist.commands.TurnDrive;

/**
 * The operator interface class ties the commands that have been implemented
 * to the physical controls of the user. This allows the binding of commands to 
 * buttons. 
 * 
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869
 */
public class OI {
    //Create the joysticks and the buttons that have different functionalities.
    Joystick mecanumStick = new Joystick(RobotMap.MECANUM_STICK_PORT);
    Joystick rotateStick = new Joystick(RobotMap.ROTATE_STICK_PORT);
    
    Button rotateButton = new JoystickButton(mecanumStick, RobotMap.SPIN_BUTTON),
           stopButton = new JoystickButton(mecanumStick, RobotMap.STOP_BUTTON),
           halfButton = new JoystickButton(mecanumStick, RobotMap.HALF_BUTTON);
    Button turnRightButton = new JoystickButton(mecanumStick, RobotMap.TURN_RIGHT_BUTTON),
           turnLeftButton = new JoystickButton(mecanumStick, RobotMap.TURN_LEFT_BUTTON),
           turnBackButton = new JoystickButton(mecanumStick, RobotMap.TURN_BACK_BUTTON);
    Button invertedButton = new JoystickButton(mecanumStick, RobotMap.INVERTED_DRIVE_BUTTON);
    
    Button autoWindButton = new JoystickButton(mecanumStick, RobotMap.AUTOMATEWIND_BUTTON);
    Button startWindButton = new JoystickButton(mecanumStick, RobotMap.STARTWIND_BUTTON);
    Button operatorWindButton = new JoystickButton(mecanumStick, RobotMap.OPERATORWIND_BUTTON);
    Button shootButton = new JoystickButton(mecanumStick, RobotMap.SHOOTBALL_BUTTON);
   
    /**
     * Defines the commands each button will instantiate upon different user
     * interface actions, such as pressing, releasing, holding, and toggling.
     */
    public OI () {
        rotateButton.whileHeld(new SpinDrive());
        stopButton.toggleWhenPressed(new StopDrive());
        halfButton.toggleWhenPressed(new HalfDrive());
        
        turnRightButton.whenPressed(new TurnDrive(0.5, true));
        turnLeftButton.whenPressed(new TurnDrive(0.5, false));
        turnBackButton.whenPressed(new TurnDrive(1, true));
        
        invertedButton.toggleWhenPressed(new InvertedDrive());
        
        autoWindButton.toggleWhenPressed(new AutomateWind());
        startWindButton.whenPressed(new StartWind());
        operatorWindButton.whenPressed(new OperatorWind());
        shootButton.whenPressed(new ShootBall());
    }
    
    /**
     * Calculates the x value of the primary joystick, ranging from -1.0 (left
     * position) to +1.0 (right position).
     * 
     * @return The value of the primary joystick's x - axis.
     */
    public double getCartesianX() {
        return mecanumStick.getX();
    }
    
    /**
     * Calculates the y value of the primary joystick, ranging from -1.0 (up, or
     * forward position) to +1.0 (down, or backward position).
     * 
     * @return The value of the primary joystick's y - axis.
     */
    public double getCartesianY() {
        return mecanumStick.getY();
    }
    
    /**
     * Calculates the x value of the secondary joystick, ranging from -1.0 (left
     * position) to +1.0 (right position).
     * 
     * @return The value of the secondary joystick's x - axis.
     */
    public double getRotation() {
        return rotateStick.getX();
    }
}

