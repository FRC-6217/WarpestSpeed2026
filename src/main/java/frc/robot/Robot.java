// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.HootAutoReplay;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.LimelightHelpers;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;
    boolean kUseLimelight = false;

    private final RobotContainer m_robotContainer;

    /* log and replay timestamp and joystick data */
    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    public Robot() {
        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run(); 

        if (kUseLimelight) {
      var driveState = m_robotContainer.swerveDrivetrain.getState();
      double headingDeg = driveState.Pose.getRotation().getDegrees();
      double omegaRps = Units.radiansToRotations(driveState.Speeds.omegaRadiansPerSecond);
      double translationMps = Math.sqrt(Math.pow((driveState.Speeds.vxMetersPerSecond), 2) 
                                        + Math.pow((driveState.Speeds.vyMetersPerSecond), 2)) ;

      LimelightHelpers.SetRobotOrientation(Constants.frontLimelightName, headingDeg, 0, 0, 0, 0, 0);

      //LimelightHelpers.SetIMUMode(Constants.frontLimelightName,3);

      var llMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.frontLimelightName);
      if (llMeasurement != null && llMeasurement.tagCount > 0 && Math.abs(omegaRps) < 2.0 && translationMps < 1.25) {
        m_robotContainer.swerveDrivetrain.addVisionMeasurement(llMeasurement.pose, llMeasurement.timestampSeconds);
      }
    }
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {
        LimelightHelpers.SetIMUMode(Constants.frontLimelightName,1);
    }

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(m_autonomousCommand);
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}
}
