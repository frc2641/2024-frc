// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.swerve;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.team2641.swerve.Constants.OperatorConstants;
import frc.team2641.swerve.subsystems.Drivetrain;
import frc.team2641.swerve.commands.ShootCommand;
import frc.team2641.swerve.commands.auto.LimelightTracking;
import frc.team2641.swerve.commands.auto.AutoShoot;
import frc.team2641.swerve.commands.auto.Creep;
import frc.team2641.swerve.commands.ClimbCommand;
// import frc.team2641.swerve.commands.auto.LimelightTracking;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain drivetrain = Drivetrain.getInstance();

  XboxController driverGamepad = new XboxController(0);
  XboxController operatorGamepad = new XboxController(1);
  // Joystick driverJoystick = new Joystick(1);

  private final SendableChooser<String> autoChooser = new SendableChooser<>();

  Command driveGamepad;
  Command driveJoystick;
  Command driveSim;

  BooleanSubscriber sub;
  BooleanPublisher pub;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // Applies deadbands and inverts controls because joysticks are back-right positive, while robot controls are front-left
    // positive. The left stick controls translation and the right stick controls the desired angle, NOT angular rotation.
    driveGamepad = drivetrain.driveCommand(
        () -> MathUtil.applyDeadband(-driverGamepad.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(-driverGamepad.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> -driverGamepad.getRightX());

    // driveJoystick = drivetrain.driveCommand(
    //     () -> MathUtil.applyDeadband(-driverJoystick.getY(), OperatorConstants.LEFT_Y_DEADBAND),
    //     () -> MathUtil.applyDeadband(-driverJoystick.getX(), OperatorConstants.LEFT_X_DEADBAND),
    //     () -> -driverJoystick.getTwist());

    driveSim = drivetrain.simDriveCommand(
        () -> MathUtil.applyDeadband(-driverGamepad.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(-driverGamepad.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverGamepad.getRawAxis(2));

    NetworkTable table = NetworkTableInstance.getDefault().getTable("state");
    pub = table.getBooleanTopic("driveMode").publish();
    pub.set(false);
    sub = table.getBooleanTopic("driveMode").subscribe(false);

    NamedCommands.registerCommand("shootHigh", new AutoShoot());
    NamedCommands.registerCommand("creep", new Creep());

    autoChooser.setDefaultOption("Shoot Creep", "Shoot Creep");
    // autoChooser.addOption("Top Start", "Top Start");
    // autoChooser.addOption("Center Start", "Center Start");
    autoChooser.addOption("Shoot Stationary", "Shoot Stationary");
    SmartDashboard.putData("Auto", autoChooser);

    drivetrain.setDefaultCommand(!RobotBase.isSimulation() ? driveGamepad : driveSim);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings() {
    new JoystickButton(driverGamepad, 8).onTrue((new InstantCommand(drivetrain::zeroGyro)));
    new JoystickButton(driverGamepad, 2).whileTrue((new LimelightTracking()));
    //new JoystickButton(driverGamepad, 6).whileTrue((new SniperMode()));
    // new JoystickButton(driverGamepad, 2).whileTrue(Commands.deferredProxy(() -> drivetrain.driveToPose(new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))));
    // new JoystickButton(driverGamepad, 3).whileTrue(new RepeatCommand(new InstantCommand(drivetrain::lock, drivetrain)));

    new JoystickButton(operatorGamepad, 3).whileTrue(new ShootCommand(4));
    new JoystickButton(operatorGamepad, 4).whileTrue(new ShootCommand(1));
    new JoystickButton(operatorGamepad, 5).whileTrue(new ShootCommand(3));
    new JoystickButton(operatorGamepad, 6).whileTrue(new ShootCommand(2));
    // new JoystickButton(driverGamepad, 2).onTrue(new LimelightTracking());
    new JoystickButton(operatorGamepad, 1).whileTrue(new ClimbCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return drivetrain.getAutonomousCommand(autoChooser.getSelected());
    // return new LimelightTracking();
  }

  /**
   * Toggles between gamepad and joystick control for driver tryouts
   */
  public void toggleDriveMode() {
    boolean driveMode = !getDriveMode();
    pub.set(driveMode);

    if (driveMode) drivetrain.setDefaultCommand(driveGamepad);
    else drivetrain.setDefaultCommand(driveJoystick);
  }

  /**
   * Gets whether robot is controlled by gamepad or joystick
   * 
   * @return true if controlled by joystick, false if controlled by gamepad
   */
  public boolean getDriveMode() {
    return sub.get();
  }

  /**
   * Sets the brake mode of the drivetrain motors
   * 
   * @param brake true to enable brake mode, false to disable brake mode
   */
  public void setMotorBrake(boolean brake) {
    drivetrain.setMotorBrake(brake);
  }
}
