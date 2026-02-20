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
  public static final String backLimelightName = "limelight-twoplus";

  public class RobotConstants {
   //Motor id
   public static final int climberMotorId = 0;
   public static final int indexerMotorId = 0;
   public static final int topIntakeMotorId = 0;
   public static final int bottomIntakeMotorId =0;
   public static final int topShooterMotorId = 0;
   public static final int bottomShooterMotorId = 0;

   //Motor speed
   public static final double topIntakeMotorSpeed = 0;
   public static final double bottomIntakeMotorSpeed = 0;
   public static final double topShooterMotorSpeed= 0;
   public static final double bottomShooterMotorSpeed = 0;
   public static final double indexerMotorSpeed = 0;

   public static final double topShooterMotorRPM = 4500;
  }
}
