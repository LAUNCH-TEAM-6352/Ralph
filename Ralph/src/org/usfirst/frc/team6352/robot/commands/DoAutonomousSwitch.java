package org.usfirst.frc.team6352.robot.commands;

import org.usfirst.frc.team6352.robot.OI;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Perform autonomous that puts a power cube on
 * the switch just in front of the robot.
 */
public class DoAutonomousSwitch extends CommandGroup
{
	public DoAutonomousSwitch()
	{
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		
		addSequential(new DriveAutonomous(
				OI.dashboardSimpleAutoDriveSpeed,
				OI.dashboardSimpleAutoDriveCurve,
				OI.dashboardSimpleAutoDriveTimeout));
		
		addSequential(new MovePowerCubeLiftToSwitch(
				OI.dashboardLiftSwitchDistance));
		
		addSequential(new SpitOutPowerCube(
				OI.dashboardPowerCubeIntakeSpitSpeed,
				OI.dashboardSpitPowerCubeTimeout));
	}
}
