// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.CubeShooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ma5951.utils.subsystem.MotorSubsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PortMap;

public class Cubeshotter extends SubsystemBase implements MotorSubsystem{
  /** Creates a new Cubeshotter. */
  private CANSparkMax master;
  private CANSparkMax slave;
  private static Cubeshotter instance;

  public Cubeshotter() {
    master = new CANSparkMax(PortMap.CubeShooter.LeftMotorID, MotorType.kBrushless);
    slave = new CANSparkMax(PortMap.CubeShooter.RightMototID, MotorType.kBrushless);

    master.setIdleMode(IdleMode.kBrake);
    slave.setIdleMode(IdleMode.kBrake);

    master.setInverted(false);
    slave.follow(master, true);
  }


  public static Cubeshotter getInstance() {
    if (instance == null) {
      instance = new Cubeshotter();
    }
    return instance;
  }

  @Override
  public void setVoltage(double voltage) {
      master.set(voltage / 12);
  }

  @Override
    public boolean canMove() {
        return true;
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
