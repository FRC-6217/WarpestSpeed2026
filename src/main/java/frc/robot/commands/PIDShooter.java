// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Shooter;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class PIDShooter extends Command {
  Shooter shooter;
  CommandSwerveDrivetrain swerveDrivetrain;

  /** Creates a new PIDShooter. */
  public PIDShooter(CommandSwerveDrivetrain swerveDrivetrain, Shooter shooter) {
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
    shooter.setShooter((-0.6483165)*Math.pow(swerveDrivetrain.distanceToHubMeters(), 6)+(12.91398)*Math.pow(swerveDrivetrain.distanceToHubMeters(), 5)+(-104.0777)*Math.pow(swerveDrivetrain.distanceToHubMeters(), 4)+(432.9310)*Math.pow(swerveDrivetrain.distanceToHubMeters(), 3)+(-976.9832)*Math.pow(swerveDrivetrain.distanceToHubMeters(), 2)+(1126.695)*swerveDrivetrain.distanceToHubMeters()+(-558.3791));
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
