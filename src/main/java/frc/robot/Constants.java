package frc.robot;

public class Constants {
    
    public static class OperatorConstants {
        public static final int kDriverControllerPort = 0;
        public static final int kSubsystemControllerPort = 1;
    }

    public static class DriveConstants {

        public static double kDeadzone = 0.4;

        public static final int kFrontLeftMotorId = 11;
        public static final int kBackRightMotorId = 12;
        public static final int kFrontRightMotorId = 13;
        public static final int kBackLeftMotorId = 14;
        public static final double kSlewValue = 1.0 / .5; // rate limit, ie bigger, less slew ie = 1/ time to max
        public static double kStaticThrottleScalar = 0.3;

    }

    public static class DumpConstants {

        public static final double kSlewValue = 4;
        public static final int kDumpMotorId = 15;
        public static final int kAltEncCPR = 8192;
        public static final int kMinArmPos = 0;
        public static final int kMaxArmPos = 140;
        public static final double kArmDeadZone = 10;

    }
}
