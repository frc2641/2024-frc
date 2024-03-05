package frc.team2641.robot2024.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AlignSpeaker extends ParallelCommandGroup {
    
    public AlignSpeaker() {
        addCommands(new AutoAlign(3));
    }
}