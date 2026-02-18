// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotConstants;

public class Shooter extends SubsystemBase {
  TalonFX topShooterMotor = new TalonFX(RobotConstants.topShooterMotorId);
  TalonFX bottomShooterMotor = new TalonFX(RobotConstants.bottomIntakeMotorId);

  /** Creates a new Shooter. */
  public Shooter() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double topMotorSpeed, double bottomMotorSpeed) {
    topShooterMotor.set(topMotorSpeed);
    bottomShooterMotor.set(bottomMotorSpeed);
  }

  public void stop(){
    topShooterMotor.stopMotor();
    bottomShooterMotor.stopMotor();
  }
}
