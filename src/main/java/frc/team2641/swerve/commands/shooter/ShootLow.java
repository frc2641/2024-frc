// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.swerve.commands.shooter;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team2641.swerve.subsystems.Drivetrain;

// NOTE: Consider using this command inline, rather than writing a subclass. For
// more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootLow extends ParallelCommandGroup {
  private Drivetrain drivetrain;

  /** Creates a new ShootHigh. */
  public ShootLow() {
    drivetrain = Drivetrain.getInstance();
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new Shoot(1),
        Commands.sequence(
            Commands.waitSeconds(0.5),
            new Feed(1),
            Commands.waitSeconds(0.75),
            drivetrain.driveCommand(() -> 5.0, () -> 0, () -> 0, () -> false),
            Commands.waitSeconds(0.5),
            drivetrain.driveCommand(() -> -5.0, () -> 0, () -> 0, () -> false),
            Commands.waitSeconds(0.5),
            drivetrain.driveCommand(() -> 0, () -> 0, () -> 0, () -> false)));
  }
}
