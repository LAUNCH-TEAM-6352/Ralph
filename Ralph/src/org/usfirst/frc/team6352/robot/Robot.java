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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot
{
	// The following deal with the power cube lift encoder:
	public static final PowerCubeLiftEncoder powerCubeLiftEncoder = new PowerCubeLiftEncoder();
	
	// The following instantiates the drive train:
	public static final DriveTrain driveTrain = new DriveTrain();
	//public static final DriveTrain driveTrain = null;
	
	public static final PowerCubeIntake powerCubeIntake = new PowerCubeIntake();
	//public static final PowerCubeIntake powerCubeIntake = null;
	
	public static final PowerCubeLift powerCubeLift = new PowerCubeLift();
	//public static final PowerCubeLift powerCubeLift = null;
	
	public static OI oi;

	private REVDigitBoard digitBoard;

	private int voltageRefreshCounter = 0;
	private static final int voltageRefreshCount = 10;

	private int potRefreshCounter = 0;
	private static final int potRefreshCount = 10;

	private boolean buttonA = false;
	private boolean buttonB = false;

	private static final String[] options = { "STAY", "SMPL", "LEFT", "RGHT" };
	private int optionIndex = 0;

	Command m_autonomousCommand = null;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		oi = new OI();
		//m_chooser.addDefault("Default Auto", new ControlNidecMotorWithGamepad());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", m_chooser);

		digitBoard = new REVDigitBoard();
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
		} else if (buttonB)
		{
			if (++potRefreshCounter > potRefreshCount)
			{
				potRefreshCounter = 0;
			}
			if (potRefreshCounter == 0)
			{
				digitBoard.display(digitBoard.getPot());
			}
		} else
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
		String message = DriverStation.getInstance().getGameSpecificMessage().toUpperCase();
		System.out.printf("   Option: %s\n", option);
		System.out.printf("Game Data: %s\n", message);
		System.out.flush();
		
		/**
		 * Determine autonomous command based upon user selected option and game specific message
		 */
		switch (option)
		{
			case "STAY":
				// "Ralph. Stay."
				// Primarily for testing. No reason to stay in competition.
				m_autonomousCommand = null;
				break;
			
			case "SMPL":
				m_autonomousCommand = new DoAutonomousSimple();
				break;
			
			default:
				// If first letter of user selected option matches our switch location,
				// deposit power cube on switch; otherwise just do simple autonomous:
				m_autonomousCommand = option.startsWith(message.substring(0, 1))
					? new DoAutonomousSwitch()
					: new DoAutonomousSimple();
				break;
		}

		// Schedule the autonomous command:
		if (m_autonomousCommand != null)
		{
			m_autonomousCommand.start();
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
		if (m_autonomousCommand != null)
		{
			m_autonomousCommand.cancel();
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
}
