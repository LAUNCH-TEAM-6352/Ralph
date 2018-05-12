package frc.team6352.subsystems;

import frc.team6352.Robot;
import frc.team6352.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class PowerCubeLiftPid extends PIDSubsystem
{
    SpeedController motor;

    // PID parameters
    private final static double kP = 1.0;
    private final static double kI = 0.50;
    private final static double kD = 0;

    // Minimum and maximum allowable set point positions:
    private final static double minDistance = 0;
    private final static double maxDistance = 12;

    // Minimum and maximum allowable speeds:
    private final static double minSpeed = -0.75;
    private final static double maxSpeed = 1.0;

    // Tolerance for reaching set point:
    private final static double tolerance = 0.1;


    // Initialize your subsystem here
    public PowerCubeLiftPid()
    {
        // Set the parameters of the PID controller:
        super(kP, kI, kD);

        // Set ranges of PID controller:
        setInputRange(minDistance, maxDistance);
        setOutputRange(minSpeed, maxSpeed);
        setAbsoluteTolerance(tolerance);

        motor = new Spark(RobotMap.powerCubeLiftPwmChannel);
    }

    public void set(double speed)
    {
        motor.set(speed);
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
        return Robot.powerCubeLiftEncoder.getDistance();
    }

    protected void usePIDOutput(double output)
    {
        set(output);
    }
}