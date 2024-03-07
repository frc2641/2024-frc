
package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Drivetrain;

public class AutoAlign extends Command {

    public int element;
    private Drivetrain drivetrain;
    public static double angularVelocity;
    private int targetAngle;
    BooleanPublisher alignmentPub;

    public AutoAlign(int element) {
        drivetrain = Drivetrain.getInstance();
        this.element = element;

        NetworkTable table = NetworkTableInstance.getDefault().getTable("state");

        alignmentPub = table.getBooleanTopic("autoAlign").publish();
        alignmentPub.set(false);
    }

    public void initialize() {
        alignmentPub.set(true);

        // probably wrong numbers, also I'm not sure if it's correct in theory.
        /*

        if(element == 1){
            targetVelocity = -90; 
            // low goal 
        }
        else(element == 2){
            targetVelocity = 180; 
            // high goal 
        }
        else(element == 3){
            targetVelocity = 150; 
            // source 
        } 
        */
       }
    
    /*We need to get the yaw angle
     * Then set that closer to the current angle
     * Continue until it's close enough
     */
    public void execute() {
        if((drivetrain.getYaw()>(targetAngle+1)||drivetrain.getYaw()<(targetAngle-1))) {
            angularVelocity = 0;
            end(false);
        }
        else {
            //angular velocity is how much stuff should move. 
            angularVelocity = (targetAngle-drivetrain.getYaw())/180;
        }
    }

    public void end(boolean interrupted) {
        alignmentPub.set(false);
    }

    public boolean isFinished() {
        return false;
    }
}
