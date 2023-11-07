package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

public class DriveJoystick extends CommandBase{
    private final Drivetrain drive;
    private CommandXboxController controller;

    public DriveJoystick(Drivetrain drive, CommandXboxController controller) {
        this.drive = drive;
        this.controller = controller;  

        addRequirements(this.drive);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        
        int deadzoneScalarLeftY = Math.abs(controller.getLeftY()) <= DriveConstants.kDeadzone? 0 : 1;
        int deadzoneScalarLeftX = Math.abs(controller.getLeftX()) <= DriveConstants.kDeadzone? 0 : 1;
        int deadzoneScalarRightX = Math.abs(controller.getRightX()) <= DriveConstants.kDeadzone? 0 : 1;
        
        drive.drive(-controller.getLeftY() * deadzoneScalarLeftY, 
            controller.getLeftX() * deadzoneScalarLeftX, 
            controller.getRightX() * deadzoneScalarRightX);
    }


    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
