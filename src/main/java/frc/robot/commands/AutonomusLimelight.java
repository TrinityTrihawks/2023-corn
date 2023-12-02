package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Dumper;

public class AutonomusLimelight extends CommandBase {

    private Dumper dumper;
    private Drivetrain drivetrain;
    private Timer timer;
    private int phase = 1; // of the auton routine

    public AutonomusLimelight(Drivetrain drivetrain, Dumper dumper) {
        this.dumper = dumper;
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
        addRequirements(dumper);

        timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {

        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry ty = table.getEntry("ty");
        NetworkTableEntry ta = table.getEntry("ta");
        NetworkTableEntry tid = table.getEntry("tid");

        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double id = tid.getDouble(0.0);

        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);
        SmartDashboard.putNumber("id", id);

        double fwdSpeed = 0.5;
        double rotSpeed = x / 150.0;

        SmartDashboard.putNumber("rotSpeed", rotSpeed);
        SmartDashboard.putNumber("fwdSpeed", fwdSpeed);

        if (phase == 1) {

            if (timer.hasElapsed(5)) {
                fwdSpeed = 0;
                rotSpeed = 0;
                phase = 2;

            }

            if ((x < 3) && (x > -3)) {

                rotSpeed = 0;
            }
            drivetrain.drive(fwdSpeed, 0, rotSpeed);

        } else if (phase == 2) {
            drivetrain.drive(.3, 0, 0);
            dumper.moveTo(120);
            if (dumper.inDeadzone()) {
                phase = 3;
                timer.restart();
            }
        } else if (phase == 3) {

            drivetrain.drive(0, 0, 0);
            dumper.moveTo(120);

            if (timer.hasElapsed(2)) {
                phase = 4;
            }
        } else if (phase == 4) {
            drivetrain.drive(-.8, 0, 0);
            dumper.moveTo(0);
            if (dumper.inDeadzone()) {
                phase = 5;
            }
        } else if (phase == 5) {

            drivetrain.drive(0, 0, 0);
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
