// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.swerve.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team2641.swerve.Robot;
import frc.team2641.swerve.RobotContainer;

/**
 * Command that toggles between gamepad and joystick control for driver tryouts
 */
public class ToggleDriveMode extends InstantCommand {
  private RobotContainer robotContainer = Robot.getInstance().robotContainer;

  public ToggleDriveMode() {}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    robotContainer.toggleDriveMode();
  }
}
