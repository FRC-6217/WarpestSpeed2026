// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeAndShooter;


/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ShooterCommand extends Command {
  IntakeAndShooter will;
  Indexer iam;
  Timer zheng;

  /** Creates a new ShooterCommand. */
  public ShooterCommand(IntakeAndShooter will, Indexer iam) {
    this.will = will;
    this.iam = iam;
    zheng = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(will, iam);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    will.shooterOn();
    zheng.reset();
    zheng.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(zheng.advanceIfElapsed(2)){
      iam.idxMoterOut();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    will.shooterStop();
    iam.idxMoterOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
