package frc.team2641.swerve.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2641.swerve.Constants;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix6.hardware.TalonFX;

public class Shooter extends SubsystemBase {

  private static Shooter instance;

  public static Shooter getInstance() {
    if (instance == null)
      instance = new Shooter();
    return instance;
  }

  public TalonFX topShooterMotor, bottomShooterMotor;

  public Shooter() {
    topShooterMotor = new TalonFX(Constants.Motors.topShooterMotor);
    bottomShooterMotor = new TalonFX(Constants.Motors.bottomShooterMotor);

    topShooterMotor.clearStickyFaults();
    bottomShooterMotor.clearStickyFaults();
  }

  public void speaker() {
    topShooterMotor.set(Constants.MotorSpeeds.speakerSpeed);
    Timer.delay(1.5);
    bottomShooterMotor.set(Constants.MotorSpeeds.speakerSpeed);
  }

  public void amp() {
    topShooterMotor.set(Constants.MotorSpeeds.ampSpeed);
    Timer.delay(0.5);
    bottomShooterMotor.set(Constants.MotorSpeeds.ampSpeed);
  }

  public void intake() {
    topShooterMotor.set(-Constants.MotorSpeeds.intakeSpeed);
    bottomShooterMotor.set(-Constants.MotorSpeeds.intakeSpeed);
  }

  public void trap() {
    topShooterMotor.set(Constants.MotorSpeeds.trapSpeed);
    Timer.delay(1.5);
    bottomShooterMotor.set(Constants.MotorSpeeds.trapSpeed);
  }

  public void stop() {
    bottomShooterMotor.stopMotor();
    topShooterMotor.stopMotor();
  }

  @Override
  public void periodic() {}
}