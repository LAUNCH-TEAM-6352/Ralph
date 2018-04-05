package org.usfirst.frc.team6352.robot.commands;

import org.usfirst.frc.team6352.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SpitOutPowerCube extends Command
{
	private double speed;
	private double timeout;
	
	private String speedKey = null;
	private String timeoutKey = null;
	
	public SpitOutPowerCube()
	{
		requires(Robot.powerCubeLiftPid);
	}
	
	public SpitOutPowerCube(double speed, double timeout)
	{
		this();
		this.speed = speed;
		this.timeout = timeout;
	}
	
	public SpitOutPowerCube(String speedKey, String timeoutKey)
	{
		this();
		this.speedKey = speedKey;
		this.timeoutKey = timeoutKey;
	}

	// Called just before this Command runs the first time
	protected void initialize()
	{
		if (speedKey != null)
		{
			speed = SmartDashboard.getNumber(speedKey, -1.0);
		}
		if (timeoutKey != null)
		{
			timeout = SmartDashboard.getNumber(timeoutKey, 5.0);
		}
		
		setTimeout(timeout);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{
		Robot.powerCubeIntake.set(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end()
	{
		Robot.powerCubeIntake.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted()
	{
		end();
	}
}
