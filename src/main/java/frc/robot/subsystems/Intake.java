// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.RobotConstants;

public class Intake extends SubsystemBase {
  SparkMax topIntakeMotor = new SparkMax(RobotConstants.topIntakeMotorId, MotorType.kBrushless);
  TalonFX bottomIntakeMotor = new TalonFX(RobotConstants.bottomIntakeMotorId);
  boolean runningForwardIntake = false;

  /** Creates a new Intake. */
  public Intake() {}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void forwardIntakeOn() {
    topIntakeMotor.set(RobotConstants.topIntakeMotorSpeed);
    bottomIntakeMotor.set(RobotConstants.bottomIntakeMotorSpeed);
  }

  public void backwardIntakeOn() {
    if(runningForwardIntake == false){
      topIntakeMotor.set(-RobotConstants.topIntakeMotorSpeed);
      bottomIntakeMotor.set(-RobotConstants.bottomIntakeMotorSpeed);
    }
  }

  public void stopIntake() {
    topIntakeMotor.stopMotor();
    bottomIntakeMotor.stopMotor();
  }

  public void toggleIntake(){
    if (runningForwardIntake == true) {
      stopIntake();
      runningForwardIntake = false;
    } else {
      forwardIntakeOn();
      runningForwardIntake = true;
    }
  }
}
