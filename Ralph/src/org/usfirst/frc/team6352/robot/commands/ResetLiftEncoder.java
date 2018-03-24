package org.usfirst.frc.team6352.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6352.robot.Robot;

/**
 * Resets the power cube lift encoder.
 */
public class ResetLiftEncoder extends Command
{
	public ResetLiftEncoder()
	{
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Robot.powerCubeLiftEncoder.reset();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		end();
	}
}
