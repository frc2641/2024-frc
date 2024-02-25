package frc.team2641.swerve.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.swerve.subsystems.Drivetrain;
import frc.team2641.swerve.subsystems.Shooter;

public class ShootCommand extends Command {
  private Shooter shooter;
  private Drivetrain drivetrain;
  private int speed;

  public ShootCommand(int speed) {
    this.shooter = Shooter.getInstance();
    this.drivetrain = Drivetrain.getInstance();
    this.speed = speed;

    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    if (speed == 1) {
      shooter.amp();
      Timer.delay(0.75);
      drivetrain.drive(new Translation2d(5.0, 0), 0, true);
      Timer.delay(0.5);
      drivetrain.drive(new Translation2d(-5.0, 0), 0, true);
      Timer.delay(0.5);
      drivetrain.drive(new Translation2d(0, 0), 0, true);
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