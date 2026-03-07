// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.RobotConstants;

public class Indexer extends SubsystemBase {
  TalonFX indexerMotor = new TalonFX(RobotConstants.indexerMotorId);

  /** Creates a new Indexer. */
  public Indexer() {
    SmartDashboard.putNumber("IndexerSpeed ", RobotConstants.indexerMotorSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed) {
    indexerMotor.set(speed);
  }

  public void startIndexer() {
    indexerMotor.set(SmartDashboard.getNumber("IndexerSpeed ", 0));
  }

  public void backwardIndexer() {
    indexerMotor.set(-SmartDashboard.getNumber("IndexerSpeed ", 0));
  }
  
  public void stop() {
    indexerMotor.stopMotor();
  }
}
