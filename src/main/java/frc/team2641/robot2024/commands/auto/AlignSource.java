package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AlignSource extends ParallelCommandGroup {

    public AlignSource() {
        addCommands(new AutoAlign(3));
    }
}