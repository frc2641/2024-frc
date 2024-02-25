package frc.team2641.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;
// import frc.team2641.swerve.Constants;
import frc.team2641.swerve.subsystems.Climber;

public class ClimbCommand extends Command {
  private Climber climber;

  public ClimbCommand() {
    this.climber = Climber.getInstance();
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    climber.toggle();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}