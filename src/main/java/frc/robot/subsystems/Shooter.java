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
  TalonFX shooterMotor = new TalonFX(RobotConstants.shooterMotorId);
  boolean runningShooter = false;
  PIDController topPIDController = new PIDController(0, 0, 0);
  PIDController bottomPIDController = new PIDController(0, 0, 0);
  double shooterMotorP = 0;
  double shooterMotorI = 0;
  double shooterMotorD = 0;
  double shooterSetpoint = 0;

  /** Creates a new Shooter. */
  public Shooter() {
    SmartDashboard.putNumber("Shooter speed ", RobotConstants.shooterMotorSpeed);
    SmartDashboard.putNumber("Shooter Setpoint", 0);

    SmartDashboard.putNumber("Top Shooter P ", 0.01);
    SmartDashboard.putNumber("Top Shooter I ", 0.02);
    SmartDashboard.putNumber("Top Shooter D ", 0.001);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Shooter is running ", runningShooter);

    SmartDashboard.putNumber("Top Motor", shooterMotor.getVelocity().getValueAsDouble());

    if (SmartDashboard.getNumber("Top Shooter P ", 0) != shooterMotorP) {
      shooterMotorP = SmartDashboard.getNumber("Top Shooter P ", 0);
      topPIDController.setP(shooterMotorP);
    }

    if (SmartDashboard.getNumber("Top Shooter I ", 0) != shooterMotorI) {
      shooterMotorI = SmartDashboard.getNumber("Top Shooter I ", 0);
      topPIDController.setI(shooterMotorI);
    }

    if (SmartDashboard.getNumber("Top Shooter D ", 0) != shooterMotorD) {
      shooterMotorD = SmartDashboard.getNumber("Top Shooter D ", 0);
      topPIDController.setD(shooterMotorD);
    }
    
    if (shooterSetpoint != 0) {
      double topPIDOutput = topPIDController.calculate(shooterMotor.getVelocity().getValueAsDouble());
      shooterMotor.set(topPIDOutput);
    } else {
      topPIDController.reset();
      shooterMotor.stopMotor();
    }

  }

  public void setTargetSpeed(double topMotorSpeed, double bottomMotorSpeed) {
    bottomPIDController.setSetpoint(bottomMotorSpeed);
    topPIDController.setSetpoint(topMotorSpeed);

  }

  public void startShooter() {
    //runningShooter = true;
    shooterMotor.set(SmartDashboard.getNumber("Shooter speed ", RobotConstants.shooterMotorSpeed));
  }

  public void setShooter(double speed) {
    shooterSetpoint = speed;
    topPIDController.setSetpoint(speed);
  }

  public void stop() {
    shooterMotor.stopMotor();
    shooterSetpoint = 0;
    runningShooter = false;
  }
  
  public boolean shooterIsAtSpeed() {
    return Math.abs(shooterMotor.getVelocity().getValueAsDouble()) >= Math.abs(topPIDController.getSetpoint() * 0.95);
  }
}
