/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6352.robot;

import org.usfirst.frc.team6352.robot.commands.MovePowerCubeLiftDown;
import org.usfirst.frc.team6352.robot.commands.MovePowerCubeLiftUp;
import org.usfirst.frc.team6352.robot.commands.ResetLiftEncoder;
import org.usfirst.frc.team6352.robot.commands.SuckInOrSpitOutPowerCube;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

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

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	public XboxController gameController = new XboxController(0);
	
	public Joystick joystickLeft = new Joystick(2);
	public Joystick joystickRight = new Joystick(3);
	
	// Buttons:
	
	// Give meaningful names to the game controller buttons:
	public final static int gameControllerButtonA = 1;
	public final static int gameControllerButtonB = 2;
	public final static int gameControllerButtonX = 3;
	public final static int gameControllerButtonY = 4;
	public final static int gameControllerButtonBumperLeft = 5;
	public final static int gameControllerButtonBumperRight = 6;
	public final static int gameControllerButtonBack = 7;
	public final static int gameControllerButtonStart = 8;
	public final static int gameControllerButtonStickLeft = 9;
	public final static int gameControllerButtonStickRight = 10;
	
	Button powerCubeSuckButton = new JoystickButton(gameController, gameControllerButtonB);
	Button powerCubeSpitButton = new JoystickButton(gameController, gameControllerButtonX);
	
	Button powerCubeLiftUpButton   = new JoystickButton(gameController, gameControllerButtonY);
	Button powerCubeLiftDownButton = new JoystickButton(gameController, gameControllerButtonA);
	
	// SmartDashboard keys:
	public final static String dashboardPowerCubeIntakeSuckSpeed = "Cube Suck Speed";
	public final static String dashboardPowerCubeIntakeSpitSpeed = "Cube Spit Speed";

	public final static String dashboardPowerCubeLiftUpSpeed = "Cube Up Speed";
	public final static String dashboardPowerCubeLiftDownSpeed = "Cube Down Speed";

	public final static String dashboardSimpleAutoDriveSpeed = "Simple Auto Speed";
	public final static String dashboardSimpleAutoDriveCurve = "Simple Auto Curve";
	public final static String dashboardSimpleAutoDriveTimeout = "Simple Auto Timeout";
	
	public final static String dashboardLiftMaxDistance = "Lift Max Distance";
	public final static String dashboardLiftMinDistance = "Lift Min Distance";
	public final static String dashboardLiftSwitchDistance = "Lift Switch Distance";
	
	public final static String dashboardRumblePower = "Rumble Power";
	
	public final static String dashboardCameraFps = "Camera FPS";

	// Constructor:
	public OI()
	{
		// Bind buttons to commands:
		powerCubeSuckButton.whileHeld(new SuckInOrSpitOutPowerCube(dashboardPowerCubeIntakeSuckSpeed));
		powerCubeSpitButton.whileHeld(new SuckInOrSpitOutPowerCube(dashboardPowerCubeIntakeSpitSpeed));

		powerCubeLiftUpButton.whileHeld(new MovePowerCubeLiftUp(dashboardPowerCubeLiftUpSpeed, dashboardRumblePower, dashboardLiftMaxDistance));
		powerCubeLiftDownButton.whileHeld(new MovePowerCubeLiftDown(dashboardPowerCubeLiftDownSpeed, dashboardRumblePower, dashboardLiftMinDistance));

		// Put default values on SmartDashboard:
		SmartDashboard.putNumber(dashboardPowerCubeIntakeSuckSpeed, 0.8);
		SmartDashboard.putNumber(dashboardPowerCubeIntakeSpitSpeed, -1.0);
		
		SmartDashboard.putNumber(dashboardPowerCubeLiftUpSpeed, 1.0);
		SmartDashboard.putNumber(dashboardPowerCubeLiftDownSpeed, -0.75);
		
		SmartDashboard.putNumber(dashboardSimpleAutoDriveSpeed, -0.5);
		SmartDashboard.putNumber(dashboardSimpleAutoDriveCurve, 0.0);
		SmartDashboard.putNumber(dashboardSimpleAutoDriveTimeout, 1.5);
		
		SmartDashboard.putNumber(dashboardLiftMaxDistance,  11.5);
		SmartDashboard.putNumber(dashboardLiftMinDistance,  0.0);
		SmartDashboard.putNumber(dashboardLiftSwitchDistance,  2.5);
		
		SmartDashboard.putNumber(dashboardRumblePower, 0.5);
		
		SmartDashboard.putNumber(dashboardCameraFps, RobotMap.usbCameraFrameRate);
		
		SmartDashboard.putData(new ResetLiftEncoder());
	}
}
