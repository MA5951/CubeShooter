// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.CubeShooter;

import com.ma5951.utils.subsystem.MotorSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.PortMap;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class arm extends SubsystemBase implements MotorSubsystem {
  /** Creates a new arm. */
  private CANSparkMax motor;

  private static arm instance;

  private arm() {
    motor = new CANSparkMax(PortMap.CubeShooter.AngleMotorID, MotorType.kBrushless);

    motor.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public boolean canMove() {
    return true;
  }

  @Override
  public void setVoltage(double voltage) {
    motor.set((voltage / 12) * 0.3);
  }

  public static arm getInstance() {
    if (instance == null) {
      instance = new arm();
    }
    return instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
