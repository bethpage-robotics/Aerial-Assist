
package edu.wpi.first.aerialassist;

import edu.wpi.first.aerialassist.commands.HalfDrive;
import edu.wpi.first.aerialassist.commands.SpinDrive;
import edu.wpi.first.aerialassist.commands.StopDrive;
import edu.wpi.first.aerialassist.commands.TurnBack;
import edu.wpi.first.aerialassist.commands.TurnLeft;
import edu.wpi.first.aerialassist.commands.TurnRight;
import edu.wpi.first.aerialassist.commands.InvertedDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
    Joystick mecanumStick = new Joystick(RobotMap.MECANUMSTICK_PORT);
    Joystick rotateStick = new Joystick(RobotMap.ROTATESTICK_PORT);
    
    Button spinButton = new JoystickButton(mecanumStick, RobotMap.SPIN_BUTTON);
    Button stopButton = new JoystickButton(mecanumStick, RobotMap.STOP_BUTTON);
    Button halfButton = new JoystickButton(mecanumStick, RobotMap.HALF_BUTTON);
    Button turnRightButton = new JoystickButton(mecanumStick, RobotMap.RIGHT_BUTTON);
    Button turnLeftButton = new JoystickButton(mecanumStick, RobotMap.LEFT_BUTTON);
    Button turnBackButton = new JoystickButton(mecanumStick, RobotMap.BACK_BUTTON);
    Button invertedButton = new JoystickButton(mecanumStick, RobotMap.INVERTED_BUTTON);
    
    public OI() {
        spinButton.whileHeld(new SpinDrive());
        stopButton.toggleWhenPressed(new StopDrive());
        halfButton.toggleWhenPressed(new HalfDrive());
        turnRightButton.whenPressed(new TurnRight());
        turnLeftButton.whenPressed(new TurnLeft());
        turnBackButton.whenPressed(new TurnBack());
        invertedButton.toggleWhenPressed(new InvertedDrive());
        
        this.updateDashboard();
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
   
    private void updateDashboard() {
        SmartDashboard.putNumber("X-Axis", this.getCartesianX());
        SmartDashboard.putNumber("Y-Axis", this.getCartesianY());
        SmartDashboard.putNumber("SPIN-Axis", this.getRotation());
    }
}

