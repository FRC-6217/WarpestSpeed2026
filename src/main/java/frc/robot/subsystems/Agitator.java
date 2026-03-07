// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotConstants;

public class Agitator extends SubsystemBase {
  /** Creates a new Agitator. */
  SparkMax agitatorMotor = new SparkMax(RobotConstants.agitatorMotorId, MotorType.kBrushless);

  public Agitator() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void startAgitator() {
    agitatorMotor.set(RobotConstants.agitatorMotorSpeed);
  }

  public void backwardAgitator() {
    agitatorMotor.set(-RobotConstants.agitatorMotorSpeed);
  }

  public void stopAgitator() {
    agitatorMotor.stopMotor();
  }
}
