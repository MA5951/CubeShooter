// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Automations.CubeShooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PortMap.CubeShooter;
import frc.robot.subsystems.CubeShooter.Cubeshotter;
import frc.robot.subsystems.CubeShooter.ShooterConstants;

public class SetPrecntPID extends CommandBase {
  /** Creates a new SetPrecntPID. */
  private double Precent;
  public SetPrecntPID(double precent) {
    Cubeshotter.getInstance().setSetPoint(ShooterConstants.MaxRPM * precent);
    Precent = precent;
    
    addRequirements(Cubeshotter.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Cubeshotter.getInstance().calculate(ShooterConstants.MaxRPM * Precent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Cubeshotter.getInstance().setVoltage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
