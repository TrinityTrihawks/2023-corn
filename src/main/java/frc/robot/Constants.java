package frc.robot;

public class Constants {
    public static class OperatorConstants {
        public static final int kDriverControllerPort = 0;
      }
    
      public static class DriveConstants {
    
        public static double kDeadzone = 0.4;
    
        public static final int kFrontLeftMotorId = 11;
        public static final int kBackLeftMotorId = 14;
        public static final int kFrontRightMotorId = 13;
        public static final int kBackRightMotorId = 12;
        public static final double kSlewValue = 1.0/ (1.0/2.0); // rate limit, ie bigger, less slew ie = 1/ time to max
        public static double kStaticThrottleScalar = .3;
    
      }
}
