
package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team2641.robot2024.subsystems.Drivetrain;

public class AutoAlign extends Command {
    //variables
    public int element;
    private Drivetrain drivetrain;
    public static double angularVelocity;
    private int targetvelocity;
    BooleanPublisher alignmentPub;

    public AutoAlign(int element) {
        drivetrain = Drivetrain.getInstance();
        this.element = element;

        NetworkTable table = NetworkTableInstance.getDefault().getTable("state")
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
        if((drivetrain.getYaw()>(targetVelocity+1)||drivetrain.getYaw()<(targetVelocity-1))) {
            angularVelocity = 0;
            public void end(boolean interrupted) {
             alignmentPub.set(false);
            }
        }
        else {
            //angular velocity is how much stuff should move. 
            angularVelocity = (targetVelocity-drivetrain.getYaw())/180;
        }
       }
   
       public boolean isFinished() {
        return false;
       }
}
