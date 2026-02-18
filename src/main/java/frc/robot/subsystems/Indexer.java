// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.RobotConstants;

public class Indexer extends SubsystemBase {
  TalonFX indexerMotor = new TalonFX(RobotConstants.indexerMotorId);

  /** Creates a new Indexer. */
  public Indexer() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed) {
    indexerMotor.set(speed);
  }
  
  public void stop() {
    indexerMotor.stopMotor();
  }
}
