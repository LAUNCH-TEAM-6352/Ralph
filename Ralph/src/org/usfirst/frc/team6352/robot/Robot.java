/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6352.robot;

import org.usfirst.frc.team6352.robot.commands.DoAutonomousSimple;
import org.usfirst.frc.team6352.robot.commands.DoAutonomousSwitch;
import org.usfirst.frc.team6352.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6352.robot.subsystems.PowerCubeIntake;
import org.usfirst.frc.team6352.robot.subsystems.PowerCubeLift;
import org.usfirst.frc.team6352.robot.subsystems.PowerCubeLiftEncoder;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot
{
	// Keeps track of USB cameras:
	private UsbCamera usbCameras[];

	// Instantiate the various robot subsystems:
	public static final PowerCubeLiftEncoder powerCubeLiftEncoder = new PowerCubeLiftEncoder();
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final PowerCubeIntake powerCubeIntake = new PowerCubeIntake();
	public static final PowerCubeLift powerCubeLift = new PowerCubeLift();
	
	// Allows for access to operator interface components:
	public static OI oi;
	
	// The command to run in autonomous mode:
	private Command autonomousCommand = null;

	/**
	 * The following deal with the REV Digit Board:
	 */
	// The board itself:
	private REVDigitBoard digitBoard;

	// Keeps track on when to refresh the voltage display:
	private int voltageRefreshCounter = 0;
	private static final int voltageRefreshCount = 10;

	// Keeps track on when to refresh the POT display:
	private int potRefreshCounter = 0;
	private static final int potRefreshCount = 10;

	// REV digit board button states:
	private boolean buttonA = false;
	private boolean buttonB = false;

	// User options selected via the REV digit board:
	private static final String[] options = { "STAY", "SMPL", "LEFT", "RGHT" };
	private int optionIndex = 0;
	
	// Keeps track of game controller back button:
	private boolean backButton = false;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		oi = new OI();

		digitBoard = new REVDigitBoard();
		
		// Set up USB cameras.
		// Do not delete the following line!
		CameraServer.getInstance();
		initializeUsbCameras();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit()
	{

	}

	@Override
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * This method is called periodically as long as
	 * code is running on the roboRIO.
	 */
	@Override
	public void robotPeriodic()
	{
		/**
		 * The following code interacts with the REV Digit Board.
		 * By default, the current battery voltage is displayed
		 * on the digit board.
		 * Pushing the A button cycles through the autonomous options.
		 */
		if (digitBoard.getButtonA() != buttonA)
		{
			buttonA = !buttonA;
			if (buttonA)
			{
				if (++optionIndex >= options.length)
				{
					optionIndex = 0;
				}
			}
		}

		if (digitBoard.getButtonB() != buttonB)
		{
			buttonB = !buttonB;
		}

		if (buttonA)
		{
			digitBoard.display(options[optionIndex]);
		}
		else if (buttonB)
		{
			if (++potRefreshCounter > potRefreshCount)
			{
				potRefreshCounter = 0;
			}
			if (potRefreshCounter == 0)
			{
				digitBoard.display(digitBoard.getPot());
			}
		}
		else
		{
			if (++voltageRefreshCounter > voltageRefreshCount)
			{
				voltageRefreshCounter = 0;
			}
			if (voltageRefreshCounter == 0)
			{
				digitBoard.display(RobotController.getBatteryVoltage());
			}
		}
		
		/**
		 * The following code sets the camera frame rate when
		 * the controller back button is pressed.
		 */
		if (oi.gameController.getBackButton() != backButton)
		{
			backButton = !backButton;
			if (backButton)
			{
				int fps = (int) SmartDashboard.getNumber(OI.dashboardCameraFps, 20);
				for (UsbCamera camera : usbCameras)
				{
					camera.setFPS(fps);
				}
			}
		}
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit()
	{
		/**
		 * Get values that will determine what we do in autonomous.
		 *    option: the value selected via the REV Digit Board
		 *   message: the message from the FMS indicating switch and scale colors
		 */
		String option = options[optionIndex].toUpperCase();
		String message = DriverStation.getInstance().getGameSpecificMessage();
		message = ((message == null || message.isEmpty()) ? "xxx" : message).toUpperCase();
		System.out.printf("   Option: %s\n", option);
		System.out.printf("Game Data: %s\n", message);
		System.out.flush();
		
		// Determine autonomous command based upon user selected option and game specific message:
		switch (option)
		{
			case "STAY":
				// "Ralph. Stay."
				// Primarily for testing. There should be no reason to stay in competition.
				autonomousCommand = null;
				break;
			
			case "SMPL":
				autonomousCommand = new DoAutonomousSimple();
				break;
			
			default:
				// If first letter of user selected option matches our switch location,
				// deposit power cube on switch; otherwise just do simple autonomous:
				autonomousCommand = option.startsWith(message.substring(0, 1))
					? new DoAutonomousSwitch()
					: new DoAutonomousSimple();
				break;
		}

		// Schedule the autonomous command:
		if (autonomousCommand != null)
		{
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit()
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
		{
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic()
	{
	}
	
	/**
	 * Starts up USB cameras, starting capture on each one.
	 */
	private void initializeUsbCameras()
	{
		UsbCameraInfo infos[] = UsbCamera.enumerateUsbCameras();
		usbCameras = new UsbCamera[infos.length];
		for (int i = 0; i < usbCameras.length; i++)
		{
			usbCameras[i] = new UsbCamera("USB" + i, infos[i].path);
			boolean setRes = usbCameras[i].setResolution(RobotMap.usbCameraImageWidth, RobotMap.usbCameraImageHeight);
			boolean setFps = usbCameras[i].setFPS((int) SmartDashboard.getNumber(OI.dashboardCameraFps, 20));
			System.out.println("Created USB camera " + i + ": " + usbCameras[i].getPath() + ", setRes=" + setRes + ", setFps=" + setFps);
			CameraServer.getInstance().startAutomaticCapture(usbCameras[i]);
		}
	}
}
