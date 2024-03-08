// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.team2641.robot2024.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Indexer;

public class Feed extends Command {
  private Indexer indexer;
  private int speed;

  /** Creates a new Flywheel. */
  public Feed(int speed) {
    indexer = Indexer.getInstance();
    this.speed = speed;
    addRequirements(indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (speed == 1)
      indexer.amp();
    else if (speed == 2)
      indexer.speaker();
    else if (speed == 3)
      indexer.trap();
    else if (speed == 4)
      indexer.intake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}