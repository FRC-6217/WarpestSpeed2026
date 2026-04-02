// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.pathplanner.lib.config.RobotConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.EncoderConfigAccessor;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.RobotConstants;

public class Intake extends SubsystemBase {
    public static enum IntakeState{
    intakeUp,
    intakeDown,
    neutral,
  }

  TalonFX intakeMotor = new TalonFX(RobotConstants.intakeMotorId);
  TalonFX intakeMoverMotor = new TalonFX(RobotConstants.intakeMoverMotorId);
  DutyCycleEncoder intakeMoverEncoder = new DutyCycleEncoder(0);
  boolean runningForwardIntake = false;
  public IntakeState intakeState;



  /** Creates a new Intake. */
  public Intake() {
    SmartDashboard.putNumber("Intake speed ", RobotConstants.intakeMotorSpeed);
    SmartDashboard.putNumber("Intake mover speed ", RobotConstants.intakeMoverMotorSpeed);
    intakeState = IntakeState.neutral;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Intake is running ", runningForwardIntake);
    SmartDashboard.putNumber("Intake through encoder value", intakeMoverEncoder.get());

    if(intakeState == IntakeState.neutral) {
      intakeMoverMotor.stopMotor();
    } else if(intakeState == IntakeState.intakeUp) {
      if(intakeMoverEncoder.get() > RobotConstants.intakeUpEncoderValue) {
        intakeMoverMotor.set(RobotConstants.intakeMoverMotorSpeed);
      } else {
        intakeState =IntakeState.neutral;
      }
    } else if(intakeState == IntakeState.intakeDown) {
      if(intakeMoverEncoder.get() < RobotConstants.intakeDownEncoderValue) {
        intakeMoverMotor.set(-RobotConstants.intakeMoverMotorSpeed);
      } else {
        intakeState =IntakeState.neutral;
      }
    }
  }

  public void forwardIntakeOn() {
    intakeMotor.set(SmartDashboard.getNumber("Intake speed ", RobotConstants.intakeMotorSpeed));
  }

  public void backwardIntakeOn() {
    if(runningForwardIntake == false){
      intakeMotor.set(-RobotConstants.intakeMotorSpeed);
    }
  }

  public void stopIntake() {
    intakeMotor.stopMotor();
  }

  public void intakeUp() {
    // intakeMoverMotor.set(SmartDashboard.getNumber("Intake mover speed ", RobotConstants.intakeMoverMotorSpeed));
    intakeState = IntakeState.intakeUp;
  }

  public void intakeDown() {
    //intakeMoverMotor.set(-SmartDashboard.getNumber("Intake mover speed ", RobotConstants.intakeMoverMotorSpeed));
    intakeState = IntakeState.intakeDown;
  }

  public void stopIntakeMover() {
    intakeMoverMotor.stopMotor();
  }

  public void toggleIntake() {
    if (runningForwardIntake == true) {
      runningForwardIntake = false;
    } else {
      runningForwardIntake = true;
    }
  }
}
