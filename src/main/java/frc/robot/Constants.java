// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class Constants {
   public static class OperatorConstants {
      public static final int kDriverControllerPort = 1;
      public static final int kOperatorControllerPort = 2;
      public static final int kLeftBackButton = 7;
      public static final int kRightBackButton = 8;
      public static final int leftTriggerAxis = 2;
      public static final int rightTriggerAxis = 3;
      public static final double kFastInc = 0.1;
      public static final double kSlowInc = 0.01;
      public static final double kDefaultFast = 1;
      public static final double kDefaultSlow = 0.3;
      public static final double debounceTimeForButton = 0.1;
  }
  
  public static final String frontLimelightName = "limelight-one";
  public static final String leftLimelightName = "limelight-left";
  public static final String rightLimelightName = "limelight-right";

  public class RobotConstants {
   //Motor id
   public static final int climberMotorId = 32;
   public static final int indexerMotorId = 36;
   public static final int intakeMotorId = 15;
   public static final int intakeMoverMotorId = 35;
   public static final int shooterMotorId = 16;
   public static final int agitatorMotorId = 0;

   //Motor speed
   public static final double intakeMotorSpeed = -0.6;
   public static final double intakeMoverMotorSpeed = 0.1;
   public static final double shooterMotorSpeed= -0.40;
   public static final double indexerMotorSpeed = -0.5;
   public static final double climberMotorSpeed = -0.5;
   public static final double agitatorMotorSpeed = 0.4;

   public static final double maxKrakenMotorSpeedRps = 100;
   public static final double intakeUpEncoderValue = 0;
   public static final double intakeDownEncoderValue = 0;
  }

   public static final double blueHubX = 4.625;
   public static final double blueHubY = 4.117;
   public static final double redHubX = 11.915;
   public static final double redHubY = 4.117;
}
