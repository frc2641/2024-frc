package frc.team2641.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class SniperMode extends Command {
    public static boolean sniperMode = false;

    public SniperMode() {}

    public static boolean getSniperMode()
    {
        return sniperMode;
    }

  @Override
  public void initialize() {
    sniperMode = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    sniperMode = false;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}