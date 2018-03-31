package org.usfirst.frc.team6352.robot.subsystems;

import org.usfirst.frc.team6352.robot.Robot;
import org.usfirst.frc.team6352.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class PowerCubeLiftPid extends PIDSubsystem
{
	SpeedController motor;

	// Initialize your subsystem here
	public PowerCubeLiftPid()
	{
		// Use these to get going:
		// setSetpoint() - Sets where the PID controller should move the system
		// to
		// enable() - Enables the PID controller.
		super("Lift", 2.0, 0.0, 0.0);
		motor = new Spark(RobotMap.powerCubeLiftPwmChannel);
		stop();
	}

	// Stop the motor
	public void stop()
	{
		motor.stopMotor();
	}

	public void initDefaultCommand()
	{
		// Not used.
	}

	protected double returnPIDInput()
	{
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		return Robot.powerCubeLiftEncoder.getDistance();
	}

	protected void usePIDOutput(double output)
	{
		// Use output to drive your system, like a motor
		// e.g. yourMotor.set(output);
	}
}
