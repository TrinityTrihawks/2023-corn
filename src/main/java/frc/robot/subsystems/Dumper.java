// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DumpConstants;

public class Dumper extends SubsystemBase {

    private static Dumper instance;

    private CANSparkMax armMotor = new CANSparkMax(DumpConstants.kDumpMotorId, MotorType.kBrushless);
    private RelativeEncoder beforeChainEnc = armMotor.getEncoder();
    

    private SlewRateLimiter armLimiter = new SlewRateLimiter(DumpConstants.kSlewValue);

    private boolean inTheDeadzone = false;

    private Dumper() {
        beforeChainEnc.setPosition(0);
    }

    public static Dumper getInstance() {
        return instance == null ? instance = new Dumper() : instance;
    }

    /**
     * bottom line arm movement. input on [-1, 1].
     * 
     */
    public void move(double percentOutput) {

        armMotor.set(armLimiter.calculate(percentOutput));

    }

    public void moveTo(double targetDegrees) {

        if (targetDegrees < DumpConstants.kMinArmPos || targetDegrees > DumpConstants.kMaxArmPos) {

            System.err.println(
                    "HOW DARE YOU COMMAND AND OUTPUT (" + targetDegrees +
                            ") OUTSIDE OF THE RoM (" + DumpConstants.kMinArmPos + " : " + DumpConstants.kMaxArmPos
                            + ")!?\n\t-- The Code Genii");

            stop();
            return;
        }

        double curAngle = getArmAngle();

        if (Math.abs(curAngle - targetDegrees) < DumpConstants.kArmDeadZone) {

            SmartDashboard.putString("Arm State", "In deadzone: " + curAngle);
            inTheDeadzone = true;
            stop();

        } else if (curAngle - targetDegrees > 0) {

            SmartDashboard.putString("Arm State", "<< Reverse: " + curAngle + "; pwr: " + powerReverseAt(curAngle));
            inTheDeadzone = false;
            move(powerReverseAt(curAngle));

        } else {

            SmartDashboard.putString("Arm State", ">> Ahead: " + curAngle + "; pwr: " + powerAheadAt(curAngle));
            inTheDeadzone = false;
            move(powerAheadAt(curAngle));
        }
    }

    private double powerAheadAt(double degrees) {

        double retVal = 0;

        if (degrees < 35) {
            retVal = 1;
        } else if (degrees < 70) {
            retVal = 1;
        } else if (degrees < 105) {
            retVal = .9;
        } else if (degrees < 120) {
            retVal = .5;
        } else {
            retVal = 0;
            stop();
        }

        return -retVal;
    }

    private double powerReverseAt(double degrees) {

        double retVal = 0;

        if (degrees < 0) {

            retVal = 0;
            stop();

        } else if (degrees < 35) {
            retVal = .3;
        } else if (degrees < 70) {
            retVal = .4;
        } else if (degrees < 95) {
            retVal = .5;
        } else if (degrees < 120) {
            retVal = .8;
        } else {
            retVal = .6;
        }

        return retVal;
    }

    public void stop() {
        armMotor.stopMotor();
    }

    public double getArmAngle() {
        return -beforeChainEnc.getPosition();
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Arm Motor (before gears) enc val", beforeChainEnc.getPosition());
        SmartDashboard.putNumber("Arm Axle (after gears) enc val", getArmAngle());
    }

    public boolean inDeadzone() {
        return inTheDeadzone;
    }

}
