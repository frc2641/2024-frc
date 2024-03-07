package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AlignmentChooser extends ParallelCommandGroup {
    
    public AlignmentChooser(int element) {
        if (element == 1) {
            addCommands(new AutoAlign(1));
        }
        else if (element == 2) {
            addCommands(new AutoAlign(2));
        }
        else if (element == 3) {
            addCommands(new AutoAlign(2));
        }
    }
}