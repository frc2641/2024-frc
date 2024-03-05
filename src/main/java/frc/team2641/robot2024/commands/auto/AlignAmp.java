package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AlignAmp extends ParallelCommandGroup {
    
    public AlignAmp() {
        addCommands(new AutoAlign(1));
    }
}