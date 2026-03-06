// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Shooter;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ShooterCommand extends Command {
  Shooter shooter;
  Agitator agitator;

  /** Creates a new ShooterCommand. */
  public ShooterCommand(Shooter shooter, Agitator agitator) {
    this.shooter = shooter;
    this.agitator = agitator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, agitator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.startShooter();
    agitator.startAgitator();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    agitator.stopAgitator();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
