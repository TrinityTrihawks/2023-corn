// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmToTarget;
import frc.robot.commands.DriveJoystick;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Dumper;

public class RobotContainer {
    private final Drivetrain drive = Drivetrain.getInstance();
    private final Dumper dumpyDumper = Dumper.getInstance();

    private final CommandXboxController driverController = new CommandXboxController(
            OperatorConstants.kDriverControllerPort);
    private final CommandXboxController subsystemController = new CommandXboxController(
            OperatorConstants.kSubsystemControllerPort);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {

        drive.setDefaultCommand(new DriveJoystick(drive, driverController));
        ArmToTarget def = new ArmToTarget(dumpyDumper);
        dumpyDumper.setDefaultCommand(def);
        subsystemController.a().onTrue(new InstantCommand(() -> def.setTarget(120)));
        subsystemController.b().onTrue(new InstantCommand(() -> def.setTarget(0)));
    }

    public Command getAutonomousCommand() {
        return 
            new RepeatCommand(new InstantCommand(() -> drive.drive(.7, 0, 0), drive))
            .withTimeout(2)
            .andThen(new RepeatCommand(new InstantCommand(() -> drive.drive(0, 0, 0), drive)));
    }
}
