// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.robot2024.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Shooter;

public class Rev extends Command {
  private Shooter shooter;
  private int speed;
  public static boolean revvingAmp;
  public static boolean revvingSpeaker;


  /** Creates a new Flywheel. */
  public Rev(int speed) {
    shooter = Shooter.getInstance();
    this.speed = speed;
    revvingAmp = false;
    revvingSpeaker = false;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (speed == 1) {
      shooter.amp();
      revvingAmp = true;
    }
    else if (speed == 2) {
      shooter.speaker();
      revvingSpeaker = true;
    }
    else if (speed == 3) {
      shooter.trap();
    }
    else if (speed == 4)
      shooter.intake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    revvingAmp = false;
    revvingSpeaker = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
