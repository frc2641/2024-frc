// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.robot2024.commands;

import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team2641.robot2024.subsystems.Drivetrain;
import frc.team2641.robot2024.subsystems.Indexer;

// NOTE: Consider using this command inline, rather than writing a subclass. For
// more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Feed extends ParallelCommandGroup {
  private Drivetrain drivetrain;
  private Indexer indexer;
  private IntegerSubscriber speedSub;

  /** Creates a new ShootHigh. */
  public Feed() {
    drivetrain = Drivetrain.getInstance();
    indexer = Indexer.getInstance();

    NetworkTable table = NetworkTableInstance.getDefault().getTable("state");

    speedSub = table.getIntegerTopic("shooterSpeed").subscribe(0);

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    if (speedSub.get() == 1) {
      addCommands(
        Commands.run(indexer::amp, indexer),
        Commands.sequence(
            Commands.waitSeconds(0.75),
            drivetrain.driveCommand(() -> 5.0, () -> 0, () -> 0, () -> false),
            Commands.waitSeconds(0.5),
            drivetrain.driveCommand(() -> -5.0, () -> 0, () -> 0, () -> false),
            Commands.waitSeconds(0.5),
            drivetrain.driveCommand(() -> 0, () -> 0, () -> 0, () -> false)));
    }
    else if (speedSub.get() == 2)
      addCommands(Commands.run(indexer::speaker, indexer));
    else if (speedSub.get() == 3)
      addCommands(Commands.run(indexer::trap, indexer));
    else if (speedSub.get() == 4)
      addCommands(Commands.run(indexer::intake, indexer));
    else {
      addCommands();
    }
  }
}