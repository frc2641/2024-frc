// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.swerve.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.swerve.subsystems.Feeder;

public class Feed extends Command {
  private Feeder feeder;
  private int speed;

  /** Creates a new Feed. */
  public Feed(int speed) {
    feeder = Feeder.getInstance();
    this.speed = speed;
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (speed == 1)
      feeder.amp();
    else if (speed == 2)
      feeder.speaker();
    else if (speed == 3)
      feeder.intake();
    else if (speed == 4)
      feeder.trap();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    feeder.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
