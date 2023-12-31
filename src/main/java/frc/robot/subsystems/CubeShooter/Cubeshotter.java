// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.CubeShooter;

import com.revrobotics.CANSparkMax;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController.ArbFFUnits;
import com.ma5951.utils.MAShuffleboard;
import com.ma5951.utils.MAShuffleboard.pidControllerGainSupplier; 
import com.ma5951.utils.subsystem.MotorSubsystem;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PortMap;

public class Cubeshotter extends SubsystemBase implements MotorSubsystem{
  /** Creates a new Cubeshotter. */
  private CANSparkMax master;
  private CANSparkMax slave;
  private static Cubeshotter instance;
  private SparkMaxPIDController pidController;
  private MAShuffleboard board;
  private pidControllerGainSupplier pidSupplier;
  private double setPoint;
  private SimpleMotorFeedforward feedforward;

  private SparkMaxPIDController anglePidController;
  private pidControllerGainSupplier anglePidSupplier;
  private double angleSetPoint;
  private SimpleMotorFeedforward angleFeedforward;

  private CANSparkMax angleMotor;

  public Cubeshotter() {
    master = new CANSparkMax(PortMap.CubeShooter.LeftMotorID, MotorType.kBrushless);
    slave = new CANSparkMax(PortMap.CubeShooter.RightMototID, MotorType.kBrushless);

    angleMotor = new CANSparkMax(PortMap.CubeShooter.AngleMotorID, MotorType.kBrushless);

    master.setIdleMode(IdleMode.kBrake);
    slave.setIdleMode(IdleMode.kBrake);

    angleMotor.setIdleMode(IdleMode.kBrake);

    master.setInverted(false);
    slave.follow(master, true);

    feedforward = new SimpleMotorFeedforward(ShooterConstants.Ks, ShooterConstants.Kv);

    pidController = master.getPIDController();
    board = new MAShuffleboard("CubeShooter");
    pidSupplier = board.getPidControllerGainSupplier(
      ShooterConstants.Kp,
      ShooterConstants.Ki,
      ShooterConstants.Kd
    );

    pidController.setP(ShooterConstants.Kp);
    pidController.setI(ShooterConstants.Ki);
    pidController.setD(ShooterConstants.Kd);



    angleFeedforward = new SimpleMotorFeedforward(ShooterConstants.angleKs, ShooterConstants.angleKv);
    
    anglePidController = angleMotor.getPIDController();
    anglePidSupplier = board.getPidControllerGainSupplier(
      ShooterConstants.Kp,
      ShooterConstants.Ki,
      ShooterConstants.Kd
    );

    anglePidController.setP(ShooterConstants.angleKp);
    anglePidController.setI(ShooterConstants.angleKi);
    anglePidController.setD(ShooterConstants.angleKd);
  }

  public double getVelocity() {
    return (master.getEncoder().getVelocity() + slave.getEncoder().getVelocity()) / 2;
  }

  public double getAngleMotorVelocity() {
    return angleMotor.getEncoder().getVelocity();
  }

  
  public void calculate(double setPoint) {
    pidController.setReference(
      setPoint, CANSparkMax.ControlType.kVelocity , 0 , 
        feedforward.calculate(setPoint), ArbFFUnits.kVoltage);
  }

  public void angleCalculate(double setPoint) {
    anglePidController.setReference(
      setPoint, CANSparkMax.ControlType.kVelocity , 0 , 
        feedforward.calculate(setPoint), ArbFFUnits.kVoltage);
  }
  
  public void setSetPoint(double setPoint) {
    this.setPoint = setPoint;
  }

  public void setAngleSetPoint(double setPoint) {
    this.angleSetPoint = angleSetPoint;
  }
  
  public double getSetPoint() {
    return setPoint;
  }

  public double getAngleSetPoint() {
    return angleSetPoint;
  }

  public void setAngleMotorVoltage(double Voltage) {
    angleMotor.set(Voltage / 12);
  }

  @Override
  public void setVoltage(double voltage) {
    master.set(voltage / 12);
  }

  @Override
    public boolean canMove() {
        return true;
    }

  public static Cubeshotter getInstance() {
    if (instance == null) {
      instance = new Cubeshotter();
    }
    return instance;
  }

  @Override
  public void periodic() {
    // pidController.setP(pidSupplier.getKP() ,0);
    // pidController.setI(pidSupplier.getKI(), 0);
    // pidController.setD(pidSupplier.getKD(), 0);
    
    // board.addNum("", setPoint);
    // board.addNum("Shooter RPM", getVelocity());
    // board.addNum("Shooter RPMG", getVelocity());
    // board.addNum("SetPoint", getSetPoint());

    
    anglePidController.setP(pidSupplier.getKP() ,0);
    anglePidController.setI(pidSupplier.getKI(), 0);
    anglePidController.setD(pidSupplier.getKD(), 0);
    
    board.addNum("", angleSetPoint);
    board.addNum("angle motor RPM", getAngleMotorVelocity());
    board.addNum("angle motor RPMG", getAngleMotorVelocity());
    board.addNum("angle SetPoint", getAngleSetPoint());
  }
}
