
package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Drivetrain;

public class AutoAngle extends Command {

    private Drivetrain drivetrain;
    private int targetAngle;
    private int oppositeAngle;
    private int element;
    BooleanPublisher alignmentPub;
    DoublePublisher angularVelocityPub;
    IntegerPublisher stagePub;
    IntegerSubscriber stageSub;

    public AutoAngle(int element) {
        drivetrain = Drivetrain.getInstance();
        this.element = element;

        NetworkTable table = NetworkTableInstance.getDefault().getTable("state");

        stagePub = table.getIntegerTopic("stageAngle").publish();
        stagePub.set(0);
        stageSub = table.getIntegerTopic("stageAngle").subscribe(0);

        alignmentPub = table.getBooleanTopic("autoAlign").publish();
        alignmentPub.set(false);

        angularVelocityPub = table.getDoubleTopic("angularVelocity").publish();
        angularVelocityPub.set(0);
    }

    public void initialize() {
        alignmentPub.set(true);

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
            if (stageSub.get() < 3)
                stagePub.set(stageSub.get()+1);
            else
                stagePub.set(1);

            if (stageSub.get() == 1) {
                targetAngle = -120;
                oppositeAngle = 60;
            }
            else if (stageSub.get() == 2) {
                targetAngle = 0;
                oppositeAngle = -180;
            }
            else if (stageSub.get() == 3) {
                targetAngle = 120;
                oppositeAngle = -60;
            }
            // stage instantiations
        }
        else if (element == 4) {
            targetAngle = 120;
            oppositeAngle = -60;
            // source
        }

        if (DriverStation.getAlliance().get().equals(DriverStation.Alliance.Red)) {
            if (element != 3) {
            targetAngle *= -1;
            oppositeAngle *= -1;
            }
        }
    }
    
    /* We need to get the yaw angle
     * Then set that closer to the current angle
     * Continue until it's close enough
     * Angular velocity is how fast the robot should rotate to the target angle
    */
    public void execute() {
        if (targetAngle < 0) {
            if (drivetrain.getHeading().getDegrees() > targetAngle && drivetrain.getHeading().getDegrees() < oppositeAngle) {
                if (drivetrain.getHeading().getDegrees() < targetAngle+20 && drivetrain.getHeading().getDegrees() > 0)
                    angularVelocityPub.set(-0.4);
                else
                    angularVelocityPub.set(-0.65);
            }
            else {
                if (drivetrain.getHeading().getDegrees() > targetAngle-20 && drivetrain.getHeading().getDegrees() < 0)
                    angularVelocityPub.set(0.4);
                else
                    angularVelocityPub.set(0.65);
            }
        }
        else {
            if (drivetrain.getHeading().getDegrees() < targetAngle && drivetrain.getHeading().getDegrees() > oppositeAngle) {
                if (drivetrain.getHeading().getDegrees() > targetAngle-20 && drivetrain.getHeading().getDegrees() < 0)
                    angularVelocityPub.set(0.4);
                else
                    angularVelocityPub.set(0.65);
            }
            else {
                if (drivetrain.getHeading().getDegrees() < targetAngle+20 && drivetrain.getHeading().getDegrees() > 0)
                    angularVelocityPub.set(-0.4);
                else
                    angularVelocityPub.set(-0.65);
            }
        }
        System.out.println("angle: " + drivetrain.getHeading().getDegrees());
    }

    public void end(boolean interrupted) {
        alignmentPub.set(false);
        angularVelocityPub.set(0);
    }

    public boolean isFinished() {
        return (drivetrain.getHeading().getDegrees()<(targetAngle+1) && drivetrain.getHeading().getDegrees()>(targetAngle-1));
    }
}
