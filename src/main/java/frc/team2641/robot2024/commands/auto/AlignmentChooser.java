package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AlignmentChooser extends ParallelCommandGroup {
    
    private IntegerSubscriber stageSub;
    private IntegerPublisher stagePub;
    
    public AlignmentChooser(int element) {
        if (element == 1)
            addCommands(new AutoAlign(1, 0));
        else if (element == 2)
            addCommands(new AutoAlign(2, 0));
        else if (element == 3) {
            NetworkTable table = NetworkTableInstance.getDefault().getTable("state");
            stagePub = table.getIntegerTopic("stageAngle").publish();
            stageSub = table.getIntegerTopic("stageAngle").subscribe(0);
            if (stageSub.get() < 3)
                stagePub.set(stageSub.get()+1);
            else
                stagePub.set(1);
            addCommands(new AutoAlign(3, (int) stageSub.get()));
        }
        else if (element == 4)
            addCommands(new AutoAlign(4, 0));
    }
}