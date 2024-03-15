// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Drivetrain;

public class Creep extends Command {
  private Drivetrain drivetrain;
  private boolean isAngular;
  private int position;
  private int targetAngle;

  /** Creates a new Creep. */
  public Creep(int position, boolean isAngular) {
    drivetrain = Drivetrain.getInstance();
    this.isAngular = isAngular;
    this.position = position;
    this.targetAngle = targetAngle;
    
    addRequirements(drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (DriverStation.getAlliance().get().equals(DriverStation.Alliance.Blue))
      targetAngle = 120;
    else
      targetAngle = -120;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!isAngular) {
      if (DriverStation.getAlliance().get().equals(DriverStation.Alliance.Blue)) {
        if (position == 1)
          drivetrain.drive(new Translation2d(0.8, 0), -0.1, false);
        else
          drivetrain.drive(new Translation2d(0.8, 0), 0, false);
      }
      else {
        if (position == 1)
          drivetrain.drive(new Translation2d(0.8, 0), 0.1, false);
        else
          drivetrain.drive(new Translation2d(0.8, 0), 0, false);
      }
    }
    else {
      if (drivetrain.getHeading().getDegrees() < targetAngle+2 && drivetrain.getHeading().getDegrees() > targetAngle-2)
        drivetrain.drive(new Translation2d(0, 0), 0, false);
      else
        drivetrain.drive(new Translation2d(0, 0), 0.5, false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(new Translation2d(0, 0), 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
