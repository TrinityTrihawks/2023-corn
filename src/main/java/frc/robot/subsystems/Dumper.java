// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DumpConstants;

public class Dumper extends SubsystemBase {

    private static Dumper instance;

    private CANSparkMax armMotor = new CANSparkMax(DumpConstants.kDumpMotorId, MotorType.kBrushless);
    private RelativeEncoder beforeChainEnc = armMotor.getEncoder();
    private RelativeEncoder afterChainEnc = armMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, DumpConstants.kAltEncCPR);

    private SlewRateLimiter armLimiter = new SlewRateLimiter(DumpConstants.kSlewValue);

    private Dumper() {
        beforeChainEnc.setPosition(0);
        afterChainEnc.setPosition(0);
    }

    public static Dumper getInstance() {
        return instance == null ? instance = new Dumper() : instance;
    }

    /**
     * bottom line arm movement. input on [-1, 1].
     * 
     */
    public void move(double percentOutput) {
        // needs the new encoder working
        // if (percentOutput < 0) {
        //     if (beforeChainEnc.getPosition() > 1) {
                armMotor.set(armLimiter.calculate(percentOutput));
        //     }
        // } else {
        //     if (beforeChainEnc.getPosition() < 139) {
        //         armMotor.set(armLimiter.calculate(percentOutput));
        //     }
        // }
    }

    public void stop() {
        armMotor.stopMotor();
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Arm Motor (before gears) enc val", beforeChainEnc.getPosition());
        SmartDashboard.putNumber("Arm Axle (after gears) enc val", afterChainEnc.getPosition());
    }

}