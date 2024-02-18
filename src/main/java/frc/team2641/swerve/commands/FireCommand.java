package frc.team2641.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.swerve.subsystems.Shooter;

public class FireCommand extends Command {
  private Shooter shooter;
  private int speed;

  public FireCommand(int speed) {
    this.shooter = Shooter.getInstance();
    this.speed = speed;

    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    if (speed == 1) {
      shooter.amp();
    } else if (speed == 2) {
      shooter.speaker();
    } else if (speed == 3) {
      shooter.intake();
    } else if (speed == 4) {
      shooter.trap();
    }
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}