package frc.team6352.subsystems;

import frc.team6352.OI;
import frc.team6352.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.NidecBrushless;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem that lifts the power cube.
 */
public class PowerCubeLift extends Subsystem
{
    SpeedController motor;

    public PowerCubeLift()
    {
        motor = new Spark(RobotMap.powerCubeLiftPwmChannel);
        stop();
    }

    // Stop the motor
    public void stop()
    {
        motor.stopMotor();
    }

    public void set(double speed)
    {
        motor.set(speed);
    }

    @Override
    protected void initDefaultCommand()
    {
        // TODO Auto-generated method stub
    }
}