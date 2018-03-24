package org.usfirst.frc.team6352.robot.subsystems;

import org.usfirst.frc.team6352.robot.RobotMap;
import org.usfirst.frc.team6352.robot.commands.DriveWithGamepadController;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The subsystem that sucks in and spits out the power cube.
 */
public class PowerCubeIntake extends Subsystem
{
	SpeedController leftMotor;
	SpeedController rightMotor;
	
	public PowerCubeIntake()
	{
		if (RobotMap.isCompetitionRobot)
		{
			leftMotor = new WPI_TalonSRX(RobotMap.leftIntakeCanDeviceId);
			rightMotor = new WPI_TalonSRX(RobotMap.rightIntakeCanDeviceId);
		}
		else
		{
			leftMotor = new Spark(RobotMap.leftIntakePwmChannel);
			rightMotor = new Spark(RobotMap.rightIntakePwmChannel);
		}
		
		// Determine if any motors need to be set inverted:
//		rightMotor.setInverted(true);
//		leftMotor.setInverted(true);
	}

	// Stop both motors
	public void stop()
	{
		leftMotor.stopMotor();
		rightMotor.stopMotor();
	}
	
	// Suck in power cube
	public void suck()
	{
		leftMotor.set(1);
		rightMotor.set(1);
	}
	
	// Spit out powercube
	public void spit()
	{
		leftMotor.set(-1);
		rightMotor.set(-1);
	}
	
	public void set(double speed)
	{
		leftMotor.set(speed);
		rightMotor.set(speed);
	}
	
	public void initDefaultCommand()
	{
	}
}
