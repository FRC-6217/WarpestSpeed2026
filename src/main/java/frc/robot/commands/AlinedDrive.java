// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static edu.wpi.first.units.Units.MetersPerSecond;

import java.util.Optional;

import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AlinedDrive extends Command {
  /** Creates a new AlinedDrive. */
  private SwerveRequest.FieldCentricFacingAngle allineRot = new SwerveRequest.FieldCentricFacingAngle();
  public CommandSwerveDrivetrain swerveDrivetrain;
  public CommandXboxController m_driverController;
  public double direction;
  public Optional<Alliance> alliance;
   public final SlewRateLimiter tranXSlewRateLimiter = new SlewRateLimiter(6.3);
    public final SlewRateLimiter tranYSlewRateLimiter = new SlewRateLimiter(6.3);
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    public Command defaultCommand;
    

  public AlinedDrive(CommandSwerveDrivetrain swerveDrivetrain, CommandXboxController driveController, Command defaultCommand) {
    this.swerveDrivetrain = swerveDrivetrain;
    this.m_driverController = driveController;
    this.defaultCommand = defaultCommand;
    addRequirements(swerveDrivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    alliance = DriverStation.getAlliance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(alliance.get() == DriverStation.Alliance.Blue) {
      direction = Math.toDegrees(Math.atan((swerveDrivetrain.getPose().getX() - Constants.blueHubX)/(swerveDrivetrain.getPose().getY() - Constants.blueHubY)));
    } else if(alliance.get() == DriverStation.Alliance.Red) {
      direction = Math.toDegrees(Math.atan((swerveDrivetrain.getPose().getX() - Constants.redHubX)/(swerveDrivetrain.getPose().getY() - Constants.redHubY)));
      if(direction > 0) {
        direction -= 180;
      } else {
        direction += 180;
      }
    }
    direction = Math.toRadians(direction);
    swerveDrivetrain.applyRequest(() -> allineRot.withTargetDirection(new Rotation2d(direction))
                    .withVelocityX(-tranXSlewRateLimiter.calculate(m_driverController.getLeftY() * MaxSpeed * swerveDrivetrain.governor.getGovernor())) // Drive forward with negative Y (forward)
                    .withVelocityY(-tranYSlewRateLimiter.calculate(m_driverController.getLeftX() * MaxSpeed * swerveDrivetrain.governor.getGovernor())) // Drive left with negative X (left)
                    .withHeadingPID(1, 0, 0));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
