// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class DriveToTag extends CommandBase {

    private final Drivetrain drive;
    private final Limelight limelight;
    private final Timer timer;
    private final double timeout;
    private final double forwardSpeed = .5;
    private final double turnDZ = 3;

    public DriveToTag(Drivetrain drive, Limelight limelight, double timeout) {

        this.drive = drive;
        this.limelight = limelight;

        addRequirements(this.drive, this.limelight);

        timer = new Timer();
        this.timeout = timeout;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double x = limelight.getTagX();
        double rotSpeed = Math.abs(x) > turnDZ ? x / 150.0 : 0;

        drive.drive(forwardSpeed, 0, rotSpeed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeout);
    }
}
