package frc.team2641.robot2024.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Climber;

public class Climb extends Command {
  private Climber climber;

  public Climb() {
    this.climber = Climber.getInstance();
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    climber.toggle();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}