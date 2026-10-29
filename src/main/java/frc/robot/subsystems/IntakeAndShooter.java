// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.pathplanner.lib.config.RobotConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.EncoderConfigAccessor;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.RobotConstants;

public class IntakeAndShooter extends SubsystemBase {
   
  SparkMax inoterMoter = new SparkMax(44, MotorType.kBrushless);

  /** Creates a new Intake. */
  public IntakeAndShooter() {
    
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

  public void forwardIntakeOn() {
    inoterMoter.set(Constants.intakeSpeed);
  }

  public void backwardIntakeOn() {
    inoterMoter.set(-Constants.intakeSpeed);
  }

  public void stopIntake() {
    inoterMoter.stopMotor();
  }

  public void shooterOn() {
    inoterMoter.set(Constants.shooterSpeed);
  }
  public void shooterStop(){
    inoterMoter.stopMotor();
  }

  

}
