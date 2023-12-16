// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Dumper;

public class MoveDumperTo extends CommandBase {

    private final Dumper dumper;
    private final double to;

    public MoveDumperTo(Dumper dumper, double to) {

        this.dumper = dumper;
        this.to = to;

        addRequirements(this.dumper);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        dumper.moveTo(to);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        dumper.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return dumper.inDeadzone();
    }
}
