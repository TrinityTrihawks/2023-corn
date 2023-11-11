// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveJoystick;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private final Drivetrain drive = Drivetrain.getInstance();
  private final CommandXboxController driverController = new CommandXboxController(
			OperatorConstants.kDriverControllerPort);
  private final CommandXboxController subsysController = new CommandXboxController(
      OperatorConstants.kSubsystemControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    drive.setDefaultCommand(new DriveJoystick(drive, driverController));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
