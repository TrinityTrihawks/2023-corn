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
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        int deadzoneScalarLeftY = Math.abs(controller.getLeftY()) <= DriveConstants.kDeadzone? 0 : 1;
        int deadzoneScalarLeftX = Math.abs(controller.getLeftX()) <= DriveConstants.kDeadzone? 0 : 1;
        int deadzoneScalarRightX = Math.abs(controller.getRightX()) <= DriveConstants.kDeadzone? 0 : 1;
        
        drive.drive(-controller.getLeftY() * deadzoneScalarLeftY, 
            controller.getLeftX() * deadzoneScalarLeftX, 
            controller.getRightX() * deadzoneScalarRightX);
    }

    /**
     * calculates strafe from joystick inputs:
     * 
     * intended behavior: lower of the two sideways joysticks is used, so
     * both must be moved the same way to strafe
     * 
     */
    private double getStrafe() {

        if (Math.signum(controller.getLeftX()) == Math.signum(controller.getRightX())) {
            return controller.getLeftX() > 0
                    ? Math.min(controller.getLeftX(), controller.getRightX())
                    : Math.max(controller.getLeftX(), controller.getRightX());
        } else {
            return 0;
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
