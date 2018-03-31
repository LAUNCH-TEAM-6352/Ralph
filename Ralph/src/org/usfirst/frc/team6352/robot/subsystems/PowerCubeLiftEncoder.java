package org.usfirst.frc.team6352.robot.subsystems;

import org.usfirst.frc.team6352.robot.RobotMap;
import org.usfirst.frc.team6352.robot.commands.ReportLiftEncoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Wraps up the power cube lift encoder as a subsystem.
 */
public class PowerCubeLiftEncoder extends Subsystem
{
	private Encoder encoder;

	public PowerCubeLiftEncoder()
	{
		// Create the encoder:
		encoder = new Encoder(
				RobotMap.liftEncoderChannelA,
				RobotMap.liftEncoderChannelB,
				RobotMap.liftEncoderIsReversed);
		
		// This sets each unit of distance to one revolution:
		encoder.setDistancePerPulse(1.0 / RobotMap.liftEncoderPulsesPerRev);
		
		// Reset the encoder:
		reset();
	}
	
	public void initDefaultCommand()
	{
		setDefaultCommand(new ReportLiftEncoder());
	}
	
	/**
	 * Reports status to the smart dashboard.
	 */
	public void report()
	{
		SmartDashboard.putNumber("Lift scale:", encoder.getEncodingScale());
		SmartDashboard.putNumber("Lift count:", get());
		SmartDashboard.putNumber(" Lift dist:", getDistance());

	}
	
	/**
	 * Returns the current count.
	 */
	public double get()
	{
		return encoder.get();
	}
	
	/**
	 * Returns the current distance.
	 */
	public double getDistance()
	{
		return encoder.getDistance();
	}
	
	/**
	 * Resets (zeroes out) the encoder.
	 */
	public void reset()
	{
		encoder.reset();
		report();
	}
	
}
