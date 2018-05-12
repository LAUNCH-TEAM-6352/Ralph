package frc.team6352.commands;

import frc.team6352.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MovePowerCubeLiftToSwitch extends Command
{
    private String switchDistanceKey = null;
    private double switchDistance;

    public MovePowerCubeLiftToSwitch()
    {
        requires(Robot.powerCubeLiftPid);
    }

    public MovePowerCubeLiftToSwitch(String switchDistanceKey)
    {
        this();
        this.switchDistanceKey = switchDistanceKey;
    }

    public MovePowerCubeLiftToSwitch(double switchDistance)
    {
        this();
        this.switchDistance = switchDistance;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        if (switchDistanceKey != null)
        {
            switchDistance = SmartDashboard.getNumber(switchDistanceKey, 1);
        }

        // Disable the PID controller:
        Robot.powerCubeLiftPid.disable();

        // Set the set point in the PID controller then enable it:
        Robot.powerCubeLiftPid.setSetpoint(switchDistance);
        Robot.powerCubeLiftPid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        // Nothing to do, the PID controller is in control,
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Robot.powerCubeLiftPid.onTarget();
    }

    // Called once after isFinished returns true
    protected void end()
    {
        Robot.powerCubeLiftPid.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        end();
    }
}