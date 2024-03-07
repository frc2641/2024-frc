package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AlignmentChooser extends ParallelCommandGroup {

    private IntegerSubscriber stageSub;
    
    public AlignmentChooser(int element) {
        if (element == 1)
            addCommands(new AutoAlign(1, 0));
        else if (element == 2)
            addCommands(new AutoAlign(2, 0));
        else if (element == 4)
            addCommands(new AutoAlign(4, 0));
        else if (element == 3) {
            addCommands(new AutoAlign(4, stageSub.get()));
        }
    }
}