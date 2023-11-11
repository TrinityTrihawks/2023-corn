// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Dumper extends SubsystemBase {

    private static Dumper instance;

    private CANSparkMax armMotor = new CANSparkMax(DriveConstants.kDumpMotorId, MotorType.kBrushless);
    private RelativeEncoder beforeChainEnc = armMotor.getEncoder();

    private Dumper() {
    }

    public static Dumper getInstance() {
        return instance == null? instance = new Dumper() : instance;
    }

    /**
     * bottom line arm movement. input on [-1, 1].
     * 
     */
    public void move(double percentOutput) {

        armMotor.set(percentOutput);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Arm Motor (before gears) enc val", beforeChainEnc.getPosition());
    }
}
