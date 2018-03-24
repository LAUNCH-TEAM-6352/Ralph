package org.usfirst.frc.team6352.robot.commands;

import org.usfirst.frc.team6352.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A command that drives the robot in the autonomous period.
 */
public class DriveAutonomous extends Command
{
	private String speedKey = null;
	private String curveKey = null;
	private String timeoutKey = null;
	
	private double speed;
	private double curve;
	private double timeout;
	
	/**
	 * Default constructor is private to force outside use of other constructors.
	 */
	private DriveAutonomous()
	{
		requires(Robot.driveTrain);
	}
	
	/**
	 * Constructs an instance that uses values from the SmartDashboard.
	 * @param speedKey
	 * @param curveKey
	 * @param timeoutKey
	 */
	public DriveAutonomous(String speedKey, String curveKey, String timeoutKey)
	{
		this();
		this.speedKey = speedKey;
		this.curveKey = curveKey;
		this.timeoutKey = timeoutKey;
	}

	/**
	 * Constructs an instance that uses fixed values.
	 * @param speed
	 * @param curve
	 * @param timeout
	 */
	public DriveAutonomous(double speed, double curve, double timeout)
	{
		this();
		this.speed = speed;
		this.curve = curve;
		this.timeout = timeout;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		System.out.println("Hello from DriveAutonomous.initialize()!");
		if (speedKey != null)
		{
			// Get values from SmartDashboard:
			speed = SmartDashboard.getNumber(speedKey, 0.5);
			curve = SmartDashboard.getNumber(curveKey, 0.0);
			timeout = SmartDashboard.getNumber(timeoutKey, 5.0);
		}
		
		setTimeout(timeout);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Robot.driveTrain.drive(speed, curve);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return isTimedOut() || isCanceled();
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		Robot.driveTrain.stop();
		System.out.println("Goodbye from DriveAutonomous.end()!");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		end();
	}
}
