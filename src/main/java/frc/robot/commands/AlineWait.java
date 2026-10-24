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
public class AlineWait extends Command {
  /** Creates a new AlinedDrive. */
  private SwerveRequest.FieldCentricFacingAngle allineRot = new SwerveRequest.FieldCentricFacingAngle();
  public CommandSwerveDrivetrain swerveDrivetrain;
  public double direction;
  public Optional<Alliance> alliance;
   public final SlewRateLimiter tranXSlewRateLimiter = new SlewRateLimiter(6.3);
    public final SlewRateLimiter tranYSlewRateLimiter = new SlewRateLimiter(6.3);
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    

  public AlineWait(CommandSwerveDrivetrain swerveDrivetrain) {
    this.swerveDrivetrain = swerveDrivetrain;
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
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs((swerveDrivetrain.allignedAngle() + 180) - (swerveDrivetrain.getPose().getRotation().getDegrees() + 180)) <= 1 || Math.abs((swerveDrivetrain.allignedAngle() + 180) - (swerveDrivetrain.getPose().getRotation().getDegrees() + 180)) >= 359;
  }
}
