// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Indexer extends SubsystemBase {
  /** Creates a new Indexer. */

  SparkMax idxMoter = new SparkMax(42, MotorType.kBrushless);

  public Indexer() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void idxMoterIn(){
    idxMoter.set(Constants.idxSpeed);
  }
  public void idxMoterOut(){
    idxMoter.set(-Constants.idxSpeed);
  }
  public void idxMoterOff(){
    idxMoter.stopMotor();
  }
}
