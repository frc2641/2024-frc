// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.robot2024;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.team2641.robot2024.Constants.OperatorConstants;
import frc.team2641.robot2024.commands.*;
import frc.team2641.robot2024.commands.auto.*;
import frc.team2641.robot2024.commands.shifts.*;
import frc.team2641.robot2024.subsystems.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic
 * methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and
 * trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain drivetrain = Drivetrain.getInstance();

  CommandXboxController driverGamepad = new CommandXboxController(0);
  CommandXboxController operatorGamepad = new CommandXboxController(1);

  private final SendableChooser<String> autoChooser = new SendableChooser<>();

  Command driveCommand;
  Command driveSim;

  BooleanPublisher alignmentPub;
  BooleanSubscriber alignmentSub;

  BooleanPublisher sniperPub;
  BooleanSubscriber sniperSub;

  BooleanPublisher robotPub;
  BooleanSubscriber robotSub;

  DoublePublisher angularVelocityPub;
  DoubleSubscriber angularVelocitySub;

  IntegerPublisher stagePub;
  IntegerSubscriber stageSub;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    NetworkTable table = NetworkTableInstance.getDefault().getTable("state");

    alignmentPub = table.getBooleanTopic("autoAlign").publish();
    alignmentPub.set(false);
    alignmentSub = table.getBooleanTopic("autoAlign").subscribe(false);

    angularVelocityPub = table.getDoubleTopic("angularVelocity").publish();
    angularVelocityPub.set(0);
    angularVelocitySub = table.getDoubleTopic("angularVelocity").subscribe(0);

    stagePub = table.getIntegerTopic("stageAngle").publish();
    stagePub.set(0);
    stageSub = table.getIntegerTopic("stageAngle").subscribe(0);

    sniperPub = table.getBooleanTopic("sniperMode").publish();
    sniperPub.set(false);
    sniperSub = table.getBooleanTopic("sniperMode").subscribe(false);

    robotPub = table.getBooleanTopic("robotRelative").publish();
    robotPub.set(false);
    robotSub = table.getBooleanTopic("robotRelative").subscribe(false);

    driveCommand = drivetrain.driveCommand(
        () -> MathUtil.applyDeadband(sniperSub.get() ? -driverGamepad.getLeftY() * 0.5 : -driverGamepad.getLeftY(),
            OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(sniperSub.get() ? -driverGamepad.getLeftX() * 0.5 : -driverGamepad.getLeftX(),
            OperatorConstants.LEFT_X_DEADBAND),
        () -> alignmentSub.get() ? angularVelocitySub.get() : sniperSub.get() ? -driverGamepad.getRightX() * 0.5 : -driverGamepad.getRightX(),
        () -> robotSub.get());

    NamedCommands.registerCommand("shootHigh", new AutoShoot());
    NamedCommands.registerCommand("creep", new Creep());

    autoChooser.setDefaultOption("Shoot Creep", "Shoot Creep");
    autoChooser.addOption("Shoot Stationary", "Shoot Stationary");
    SmartDashboard.putData("Auto", autoChooser);

    drivetrain.setDefaultCommand(driveCommand);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary predicate, or via the
   * named factories in
   * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses
   * for
   * {@link CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick
   * Flight joysticks}.
   */
  private void configureBindings() {
    driverGamepad.a().whileTrue(new AlignmentChooser(1));
    driverGamepad.b().whileTrue(new AlignmentChooser(2));
    driverGamepad.y().whileTrue(new AlignmentChooser(3));
    driverGamepad.x().whileTrue(new AlignmentChooser(4));
    driverGamepad.leftBumper().whileTrue(new LimelightTracking());
    driverGamepad.leftTrigger().whileTrue(new SniperMode());
    driverGamepad.rightTrigger().whileTrue(new RobotRelative());
    driverGamepad.start().onTrue(new InstantCommand(drivetrain::zeroGyro));

    operatorGamepad.y().whileTrue(new Climb());
    operatorGamepad.a().whileTrue(new RevChooser(1));
    operatorGamepad.b().whileTrue(new RevChooser(2));
    operatorGamepad.x().whileTrue(new RevChooser(3));
    operatorGamepad.leftTrigger().whileTrue(new Intake());
    operatorGamepad.rightTrigger().whileTrue(new FeedChooser());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return drivetrain.getAutonomousCommand(autoChooser.getSelected());
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
