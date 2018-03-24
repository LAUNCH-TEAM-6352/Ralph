/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6352.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	// The following indicates if we are running the competition robot:
	public static final boolean isCompetitionRobot = false;

	// The PWM channels for the drive system on the practice robot:
	public static final int leftDrivePwmChannel = 1;
	public static final int rightDrivePwmChannel = 0;

	// The CAN device ids for the drive system on the competition robot:
	public static final int leftDriveCanDeviceId = 1;
	public static final int rightDriveCanDeviceId = 2;
	public static final int centerDriveCanDeviceId = 3;
	
	// The PWM channels for the power cube intake on the practice robot:
	public static final int leftIntakePwmChannel = 4;
	public static final int rightIntakePwmChannel = 3;
	
	// The CAN device ids for the power cube intake on the competition robot:
	public static final int leftIntakeCanDeviceId = 4;
	public static final int rightIntakeCanDeviceId = 5;
	
	// The PWM channel for the power cube lift for both practice and competition robots:
	public static final int powerCubeLiftPwmChannel = 2;
	
	// The digital channels and other stuff dealing with the lift Encoder:
	public static final int liftEncoderChannelA = 9;
	public static final int liftEncoderChannelB = 8;
	public static final int liftEncoderChannelX = 7;
	public static final int liftEncoderPulsesPerRev = 2048;
	public static final double liftEncoderMaxDistance = 5000.0;
	public static final double liftEncoderMinDistance = -5000.0;
	public static final boolean liftEncoderIsReversed = true;
	
	// The rumble type to use:
	public static final RumbleType rumbleType = RumbleType.kRightRumble;
}
