package frc.team2641.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotRelativeMode extends Command {
    public static boolean robotRelative = false;

    public RobotRelativeMode() {}

    public static boolean getRobotRelative()
    {
        return robotRelative;
    }

  @Override
  public void initialize() {
    robotRelative = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    robotRelative = false;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}