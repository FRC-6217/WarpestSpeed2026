// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.reflect.Parameter;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeAndShooter;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class IntakeCommand extends Command {
  IntakeAndShooter lebronJ;
  Indexer bronnyJ;

  /** Creates a new IntakeCommand. */
  public IntakeCommand(IntakeAndShooter lebronJ, Indexer bronnyJ) {
    this.lebronJ = lebronJ;
    this.bronnyJ = bronnyJ;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(lebronJ, bronnyJ);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() { 
    lebronJ.forwardIntakeOn();
    bronnyJ.idxMoterIn();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lebronJ.stopIntake();
    bronnyJ.idxMoterOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
