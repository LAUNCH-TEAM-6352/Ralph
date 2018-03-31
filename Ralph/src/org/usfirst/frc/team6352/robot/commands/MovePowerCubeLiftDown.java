package org.usfirst.frc.team6352.robot.commands;

import org.usfirst.frc.team6352.robot.Robot;
import org.usfirst.frc.team6352.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A command for moving the power cube lift down.
 */
public class MovePowerCubeLiftDown extends Command
{
	private String motorSpeedKey = null;
	private String rumblePowerKey = null;
	private String minDistanceKey = null;
	
	private double motorSpeed;
	private double rumblePower;
	private double minDistance;
	
	
	public MovePowerCubeLiftDown()
	{
		requires(Robot.powerCubeLift);
	}
	
	public MovePowerCubeLiftDown(String motorSpeedKey, String rumblePowerKey, String minDistanceKey)
	{
		this();
		this.motorSpeedKey = motorSpeedKey;
		this.rumblePowerKey = rumblePowerKey;
		this.minDistanceKey = minDistanceKey;
	}
	
	public MovePowerCubeLiftDown(double motorSpeed, double rumblePower, double minDistance)
	{
		this();
		this.motorSpeed = motorSpeed;
		this.rumblePower = rumblePower;
		this.minDistance = minDistance;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		if (motorSpeedKey != null)
		{
			motorSpeed = SmartDashboard.getNumber(motorSpeedKey, 1.0);
		}
		if (rumblePowerKey != null)
		{
			rumblePower = SmartDashboard.getNumber(rumblePowerKey, 1.0);
		}
		if (minDistanceKey != null)
		{
			minDistance = SmartDashboard.getNumber(minDistanceKey, 12.0);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{
		// This will not let us move the lift lower than the min distance:
		if (Robot.powerCubeLiftEncoder.getDistance() > minDistance
				|| Robot.oi.gameController.getBumper(Hand.kLeft))
		{
			Robot.powerCubeLift.set(motorSpeed);
			Robot.oi.gameController.setRumble(RobotMap.rumbleType, 0);
		}
		else
		{
			Robot.powerCubeLift.stop();
			Robot.oi.gameController.setRumble(RobotMap.rumbleType, rumblePower);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		return isCanceled();
	}

	// Called once after isFinished returns true
	protected void end()
	{
		Robot.powerCubeLift.stop();
		Robot.oi.gameController.setRumble(RobotMap.rumbleType, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted()
	{
		end();
	}
}
