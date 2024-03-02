package frc.team2641.swerve.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2641.swerve.Constants;
import com.ctre.phoenix6.hardware.TalonFX;

public class Feeder extends SubsystemBase {
  private static Feeder instance;

  public static Feeder getInstance() {
    if (instance == null)
      instance = new Feeder();
    return instance;
  }

  public TalonFX feederMotor;

  public Feeder() {
    feederMotor = new TalonFX(Constants.CAN.bottomShooterMotor);
    feederMotor.clearStickyFaults();
  }

  public void speaker() {
    feederMotor.set(Constants.MotorSpeeds.speakerSpeed);
  }

  public void amp() {
    feederMotor.set(Constants.MotorSpeeds.ampSpeed);
  }

  public void intake() {
    feederMotor.set(-Constants.MotorSpeeds.intakeSpeed);
  }

  public void trap() {
    feederMotor.set(Constants.MotorSpeeds.trapSpeed);
  }

  public void stop() {
    feederMotor.stopMotor();
  }

  @Override
  public void periodic() {
  }
}