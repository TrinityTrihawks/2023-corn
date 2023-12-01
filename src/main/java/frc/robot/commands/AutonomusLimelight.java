package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Dumper;

public class AutonomusLimelight extends CommandBase {

    private Dumper dumper;
    private Drivetrain drivetrain;
    public AutonomusLimelight(Drivetrain drivetrain, Dumper dumper) {
        this.dumper = dumper;
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
        addRequirements(dumper); 
    }

       
   

    @Override
    public void initialize() {
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


        double fwdSpeed = (10 - area) / 21;
        double rotSpeed = x / 150;
        double angle = dumper.getArmAngle();

        SmartDashboard.putNumber("rotSpeed", rotSpeed);
        SmartDashboard.putNumber("fwdSpeed", fwdSpeed);

        
        int phase = 1; // of the auton routine

        if (phase == 1) {
            if ((id != 4) && (id != 5)) {
                fwdSpeed = 0;
                rotSpeed = 0;
            }
            if (area == 0) {
                fwdSpeed = 0;
                rotSpeed = 0;
            } else {
                if (area > 9.5) {
                    fwdSpeed = 0;
                    rotSpeed = 0;
                    phase = 2;

                }

                if ((x < 3) && (x > -3)) {

                    rotSpeed = 0;
                }
                drivetrain.drive(fwdSpeed, 0, rotSpeed);
            }
        } else if (phase == 2) {

            dumper.moveTo(120);
            if (angle == 120) {
                phase = 3;
            }
        } else if (phase == 3) {
            drivetrain.drive(-1, 0, 0);
            dumper.moveTo(0);
            if (angle == 0) {
                phase = 4;
            }
        } else if (phase == 4) {
           
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
