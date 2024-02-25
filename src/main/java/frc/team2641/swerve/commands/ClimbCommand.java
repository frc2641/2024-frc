package frc.team2641.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;
// import frc.team2641.swerve.Constants;
import frc.team2641.swerve.subsystems.Climber;

public class ClimbCommand extends Command {
  private Climber climber;
  private int direction;

  public ClimbCommand(int direction) {
    this.climber = Climber.getInstance();
    this.direction = direction;
    addRequirements(climber);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (direction == 1) {
      // if (climber.getPosition() < Constants.CLIMBER_STOP) {
        climber.down();
      // }
      // else {
        // climber.stop();
      // }
    } else if (direction == 2) {
      // if (climber.getPosition() > 0) {
        climber.up();
      // }
      // else {
        // climber.stop();
      // }
    }
  }

  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}