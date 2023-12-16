package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.util.MoveDumperTo;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Dumper;
import frc.robot.subsystems.Limelight;

public class Autos {
    
    public static Command scoreOneAndBackOff() {
        return new SequentialCommandGroup(
            new DriveToTag(Drivetrain.getInstance(), Limelight.getInstance(), 5),
            new ParallelRaceGroup(
                new MoveDumperTo(Dumper.getInstance(), 140),
                new RunCommand(() -> Drivetrain.getInstance().drive(0, 0, 0), Drivetrain.getInstance())
            ),
            new ParallelRaceGroup(
                new MoveDumperTo(Dumper.getInstance(), 0),
                new RunCommand(() -> Drivetrain.getInstance().drive(-.8, 0, 0), Drivetrain.getInstance())
            )
        );
    }
}
