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

  public TalonFX climberMotor1;
  public TalonFX climberMotor2;

  public Climber() {
    climberMotor1 = new TalonFX(Constants.Motors.leftClimberMotor);
    climberMotor1.clearStickyFaults();
    climberMotor1.setNeutralMode(NeutralModeValue.Brake);
    climberMotor1.setPosition(0);
    
    climberMotor2 = new TalonFX(Constants.Motors.rightClimberMotor);
    climberMotor2.clearStickyFaults();
    climberMotor2.setNeutralMode(NeutralModeValue.Brake);
    climberMotor2.setPosition(0);
  }

  public void up() {
    climberMotor1.set(-Constants.MotorSpeeds.climbSpeed);
    climberMotor2.set(Constants.MotorSpeeds.climbSpeed);
  }

  public void down() {
    climberMotor1.set(Constants.MotorSpeeds.climbSpeed);
    climberMotor2.set(-Constants.MotorSpeeds.climbSpeed);
  }

  public void stop() {
    climberMotor1.stopMotor();
    climberMotor2.stopMotor();
  }

  public double getPosition() {
    return climberMotor1.getPosition().getValue();
  }

  @Override
  public void periodic() {
  }
}