// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.robot2024.commands;

import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team2641.robot2024.subsystems.Shooter;

// NOTE: Consider using this command inline, rather than writing a subclass. For more 
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Rev extends ParallelCommandGroup {
  private Shooter shooter;
  private IntegerPublisher speedPub;

  /** Creates a new ShootHigh. */
  public Rev(int speed) {
    shooter = Shooter.getInstance();
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable("state");

    speedPub = table.getIntegerTopic("shooterSpeed").publish();
    speedPub.set(0);
    
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    if (speed == 1) {
      addCommands(Commands.run(shooter::amp, shooter));
      speedPub.set(1);
    }
    else if (speed == 2) {
      addCommands(Commands.run(shooter::speaker, shooter));
      speedPub.set(2);
    }
    else if (speed == 3) {
      addCommands(Commands.run(shooter::trap, shooter));
      speedPub.set(3);
    }
    else if (speed == 4) {
      addCommands(Commands.run(shooter::intake, shooter));
      speedPub.set(4);
    }
  }
}
