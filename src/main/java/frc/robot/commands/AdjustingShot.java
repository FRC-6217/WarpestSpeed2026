// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Shooter;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AdjustingShot extends Command {
  CommandSwerveDrivetrain swerveDrivetrain;
  Shooter shooter;
  /** Creates a new AdjustingShot. */
  public AdjustingShot(CommandSwerveDrivetrain swerveDrivetrain, Shooter shooter) {
    this.swerveDrivetrain = swerveDrivetrain;
    this.shooter = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setShooter(-(0.0007234557 * Math.pow(swerveDrivetrain.distanceToHubMeters(),3) + (-0.03075449)*Math.pow(swerveDrivetrain.distanceToHubMeters(),2)+(0.1950242)*swerveDrivetrain.distanceToHubMeters()+(0.1443406)));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
