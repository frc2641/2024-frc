
package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Drivetrain;

public class AutoAlign extends Command {

    private Drivetrain drivetrain;
    private int targetAngle;
    private int oppositeAngle;
    BooleanPublisher alignmentPub;
    DoublePublisher angularVelocityPub;

    public AutoAlign(int element, int stageAngle) {
        drivetrain = Drivetrain.getInstance();

        NetworkTable table = NetworkTableInstance.getDefault().getTable("state");

        alignmentPub = table.getBooleanTopic("autoAlign").publish();
        alignmentPub.set(false);

        angularVelocityPub = table.getDoubleTopic("angularVelocity").publish();
        angularVelocityPub.set(0);

        if (element == 1) {
            targetAngle = -90;
            oppositeAngle = 90;
            // amp 
        }
        else if (element == 2) {
            targetAngle = 0;
            oppositeAngle = 180;
            // speaker 
        }
        else if (element == 3) {
            if (stageAngle == 1) {
                targetAngle = 120;
                oppositeAngle = -60;
            }
            else if (stageAngle == 2) {
                targetAngle = 0;
                oppositeAngle = 180;
            }
            else if (stageAngle == 3) {
                targetAngle = -120;
                oppositeAngle = 60;
            }
            // stage
        }
        else if (element == 4) {
            targetAngle = 120;
            oppositeAngle = -60;
            // source
        }

        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red) {
            targetAngle *= -1;
            oppositeAngle *= -1;
        }
    }

    public void initialize() {
        alignmentPub.set(true);

        if (targetAngle > 0) {
            if (drivetrain.getHeading().getDegrees() < targetAngle && drivetrain.getHeading().getDegrees() < oppositeAngle) {
                angularVelocityPub.set(-0.5);
            }
            else {
                angularVelocityPub.set(0.5);
            }
        }
        else {
            if (drivetrain.getHeading().getDegrees() > targetAngle && drivetrain.getHeading().getDegrees() < oppositeAngle) {
                angularVelocityPub.set(0.5);
            }
            else {
                angularVelocityPub.set(-0.5);
            }
        }
       }
    
    /* We need to get the yaw angle
     * Then set that closer to the current angle
     * Continue until it's close enough
     * Angular velocity is how fast the robot should rotate to the target angle
    */
    public void execute() {
    }

    public void end(boolean interrupted) {
        alignmentPub.set(false);
        angularVelocityPub.set(0);
    }

    public boolean isFinished() {
        return (drivetrain.getHeading().getDegrees()<(targetAngle+1) && drivetrain.getHeading().getDegrees()>(targetAngle-1));
    }
}
