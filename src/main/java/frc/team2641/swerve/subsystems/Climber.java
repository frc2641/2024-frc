package frc.team2641.swerve.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2641.swerve.Constants;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class Climber extends SubsystemBase {
  private static Climber instance;

  public static Climber getInstance() {
    if (instance == null)
      instance = new Climber();
    return instance;
  }

  public TalonFX climberMotor;

  public Climber() {
    climberMotor = new TalonFX(Constants.Motors.climberMotor);
    climberMotor.clearStickyFaults();
    climberMotor.setNeutralMode(NeutralModeValue.Brake);
    climberMotor.setPosition(0);
  }

  public void up() {
    climberMotor.set(Constants.MotorSpeeds.climbSpeed);
  }

  public void down() {
    climberMotor.set(-Constants.MotorSpeeds.climbSpeed);
  }

  public void stop() {
    climberMotor.stopMotor();
  }

  public double getPosition() {
    return climberMotor.getPosition().getValue();
  }

  @Override
  public void periodic() {
  }
}