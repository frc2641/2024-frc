package frc.team2641.robot2024;

import com.pathplanner.lib.config.PIDConstants;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

public final class Constants {
  public static final double ROBOT_MASS = 47; // 32lbs * kg per pound
  public static final Matter CHASSIS = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME = 0.13; // s, 20ms + 110ms spark max velocity lag

  public static final class AutoConstants {
    public static final PIDConstants TRANSLATION_PID = new PIDConstants(1.25, 0, 0);
    public static final PIDConstants ANGLE_PID = new PIDConstants(6, 0, 0);
  }//   >14.5, 17, 16 kP, 12.5__5
  //    100, 10, 50, 5

  public static final class DriveConstants {
    public static final double WHEEL_LOCK_TIME = 10; // seconds
    public static final double MAX_SPEED = 10; // m/s
  }

  public static class OperatorConstants {
    public static final double LEFT_X_DEADBAND = 0.1;
    public static final double LEFT_Y_DEADBAND = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT = 6;
  }

  public static final class CAN {
    public static final int shooterMotor = 14;
    public static final int indexerMotor = 13;
    public static final int pdh = 20;
    public static final int ph = 21;
  }

  public static final class MotorSpeeds {
    public static final double speakerSpeed = 1;
    public static final double ampSpeed = 0.1;
    public static final double trapSpeed = 0.5;
    public static final double intakeSpeed = 0.1;
  }
}
