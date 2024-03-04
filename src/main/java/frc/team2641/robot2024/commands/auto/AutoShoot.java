package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Shooter;
import frc.team2641.robot2024.subsystems.Indexer;

public class AutoShoot extends Command {
  private Shooter shooter;
  private Indexer indexer;
  private Timer timer;

  public AutoShoot() {
    this.shooter = Shooter.getInstance();
    this.indexer = Indexer.getInstance();
    timer = new Timer();

    addRequirements(shooter, indexer);
  }

  @Override
  public void initialize() {
    timer.reset();
    shooter.speaker();
    Timer.delay(2);
    indexer.speaker();
  }

  @Override
  public void execute() {
    if (timer.hasElapsed(3))
      end(false);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    indexer.stop();
    timer.stop();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(3);
  }
}