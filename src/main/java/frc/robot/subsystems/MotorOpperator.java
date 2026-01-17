// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.lang.ProcessBuilder.Redirect.Type;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorOpperator extends SubsystemBase {
  /** Creates a new MotorOpperator. */
  SparkMax motor;
  int canID;

  public MotorOpperator(int canID) {
    this.canID = canID;
    motor = new SparkMax(canID, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void setSpeed(double speed){
    motor.set(speed);
  }
  public void stop(){
    motor.set(0);
  }

}