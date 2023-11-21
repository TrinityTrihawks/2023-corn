// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Dumper;

/**
 * Simple command to allow movement of the dumper via the 
 * a and b buttons on an injected controller.
 */
public class BasicDumperTeleop extends CommandBase {

    private Dumper dumper;
    private CommandXboxController controller;
    private double throttle;

    /**
     * 
     * @param dumper
     * @param controller
     * @param throttle must be on [0, 1] (enforced by constructor, no throw)
     */
    public BasicDumperTeleop(Dumper dumper, CommandXboxController controller, double throttle) {
        this.dumper = dumper;
        this.controller = controller;
        this.throttle = Math.min(1, Math.abs(throttle)); // max = 1; must be +
        addRequirements(dumper);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        int runForwards = controller.a().getAsBoolean()? 1 : 0;
        int runBackwards = controller.b().getAsBoolean()? -1 : 0;
        int direction = runBackwards + runForwards;

        dumper.move(direction * throttle);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        dumper.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
