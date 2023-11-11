// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveJoystick;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Dumper;

public class RobotContainer {
    private final Drivetrain drive = Drivetrain.getInstance();
    private final Dumper dumpyDumper = Dumper.getInstance();

    private final CommandXboxController driverController = new CommandXboxController(
            OperatorConstants.kDriverControllerPort);
  private final CommandXboxController subsysController = new CommandXboxController(
      OperatorConstants.kSubsystemControllerPort);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        drive.setDefaultCommand(new DriveJoystick(drive, driverController));
        
        driverController.a().whileTrue(new InstantCommand(() -> dumpyDumper.move(0.2), dumpyDumper));
        driverController.a().whileFalse(new InstantCommand(() -> dumpyDumper.move(0), dumpyDumper));
        driverController.b().whileTrue(new InstantCommand(() -> dumpyDumper.move(-.2), dumpyDumper));
        driverController.b().whileFalse(new InstantCommand(() -> dumpyDumper.move(0), dumpyDumper));

    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
