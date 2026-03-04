// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotConstants;

public class Shooter extends SubsystemBase {
  TalonFX topShooterMotor = new TalonFX(RobotConstants.topShooterMotorId);
  TalonFX bottomShooterMotor = new TalonFX(RobotConstants.bottomShooterMotorId);
  boolean runningShooter = false;
  PIDController topPIDController = new PIDController(0, 0, 0);
  PIDController bottomPIDController = new PIDController(0, 0, 0);
  double topShooterMotorP = 0;
  double bottomShooterMotorP = 0;
  /** Creates a new Shooter. */
  public Shooter() {
    SmartDashboard.putNumber("Top shooter speed ", RobotConstants.topShooterMotorSpeed);
    SmartDashboard.putNumber("Bottom shooter speed ", RobotConstants.bottomShooterMotorSpeed);

    SmartDashboard.putNumber("Top Shooter P ", 0);
    SmartDashboard.putNumber("Bottom Shooter P ", 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Shooter is running ", runningShooter);

    SmartDashboard.putNumber("Top Motor", topShooterMotor.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Bottom Motor", bottomShooterMotor.getVelocity().getValueAsDouble());

    if (SmartDashboard.getNumber("Top Shooter P ", 0) != topShooterMotorP) {
      topShooterMotorP = SmartDashboard.getNumber("Top Shooter P ", 0);
      topPIDController.setP(topShooterMotorP);
    }

    if (SmartDashboard.getNumber("Bottom Shooter P ", 0) != bottomShooterMotorP) {
      bottomShooterMotorP = SmartDashboard.getNumber("Bottom Shooter P ", 0);
      bottomPIDController.setP(bottomShooterMotorP);
    }

    if (runningShooter == true) {
      double topPIDOutput = topPIDController.calculate(topShooterMotor.getVelocity().getValueAsDouble());
      topShooterMotor.set(topPIDOutput);

      double bottomPIDOutput = bottomPIDController.calculate(bottomShooterMotor.getVelocity().getValueAsDouble());
      bottomShooterMotor.set(bottomPIDOutput);
    } else {
      topShooterMotor.stopMotor();
      bottomShooterMotor.stopMotor();
    }

    if (SmartDashboard.getNumber("Top shooter speed ", 0) !=  topPIDController.getSetpoint()) {
      topPIDController.setSetpoint(SmartDashboard.getNumber("Top shooter speed ", 0));
    }

    if (SmartDashboard.getNumber("Bottom shooter speed ", 0) !=  bottomPIDController.getSetpoint()) {
      bottomPIDController.setSetpoint(SmartDashboard.getNumber("Top shooter speed ", 0));
    }
  }

  public void setTargetSpeed(double topMotorSpeed, double bottomMotorSpeed) {
    bottomPIDController.setSetpoint(bottomMotorSpeed);
    topPIDController.setSetpoint(topMotorSpeed);
  }

  public void startShooter() {
    runningShooter = true;
  }

  public void stop() {
    topShooterMotor.stopMotor();
    bottomShooterMotor.stopMotor();
    runningShooter = false;
  }
  
  public boolean shooterIsAtSpeed() {
    return Math.abs(topShooterMotor.getVelocity().getValueAsDouble() * 60) >= Math.abs(RobotConstants.maxKrakenMotorSpeedRpm * RobotConstants.topShooterMotorSpeed * 0.9) 
    && Math.abs(bottomShooterMotor.getVelocity().getValueAsDouble() * 60) >= Math.abs(RobotConstants.maxKrakenMotorSpeedRpm * RobotConstants.bottomShooterMotorSpeed * 0.9);
  }
}
