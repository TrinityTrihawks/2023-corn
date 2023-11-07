package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase{
    public static ADIS16470_IMU imu = new ADIS16470_IMU();
    
    private final CANSparkMax frontLeftSparkMax = new CANSparkMax(DriveConstants.kFrontLeftMotorId, MotorType.kBrushless);
    private final CANSparkMax rearLeftSparkMax = new CANSparkMax(DriveConstants.kBackLeftMotorId, MotorType.kBrushless);
    private final CANSparkMax frontRightSparkMax = new CANSparkMax(DriveConstants.kFrontRightMotorId, MotorType.kBrushless);
    private final CANSparkMax rearRightSparkMax = new CANSparkMax(DriveConstants.kBackRightMotorId, MotorType.kBrushless);

    private final MecanumDrive mecanumDrive = new MecanumDrive(frontLeftSparkMax, rearLeftSparkMax, frontRightSparkMax, rearRightSparkMax);

    private SlewRateLimiter ylimiter = new SlewRateLimiter(DriveConstants.kSlewValue);
    private SlewRateLimiter xlimiter = new SlewRateLimiter(DriveConstants.kSlewValue);
    private SlewRateLimiter zlimiter = new SlewRateLimiter(DriveConstants.kSlewValue);

    private static Drivetrain inst = null;

    /** Creates a new Drivetrain. */
    private Drivetrain() {

        frontRightSparkMax.setInverted(true);
        rearRightSparkMax.setInverted(true);
    }

    public static Drivetrain getInstance() {
        return inst == null ? inst = new Drivetrain() : inst;
    }

    public void drive(double x, double y, double theta) {
        mecanumDrive.driveCartesian(
            xlimiter.calculate(x * DriveConstants.kStaticThrottleScalar), 
            ylimiter.calculate(y * DriveConstants.kStaticThrottleScalar), 
            zlimiter.calculate(theta * DriveConstants.kStaticThrottleScalar));

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
