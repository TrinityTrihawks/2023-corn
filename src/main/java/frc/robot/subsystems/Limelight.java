// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

    private static Limelight instance;

    private Limelight() {
    }

    public static Limelight getInstance() {
        return instance == null ? instance = new Limelight() : instance;
    }


    private NetworkTable table;
    private NetworkTableEntry xEntry;
    private NetworkTableEntry yEntry;
    private NetworkTableEntry areaEntry;
    private NetworkTableEntry idEntry;


    public double getTagX() {
        return xEntry.getDouble(0.0);
    }

    public double getTagY() {
        return yEntry.getDouble(0.0);
    }

    public double getTagArea() {
        return areaEntry.getDouble(0.0);
    }

    public double getTagID() {
        return idEntry.getDouble(0.0);
    }

    public boolean hasTag() {
        return false; //TODO: figure out how to get whether the limelight has detected a tag
    }

    @Override
    public void periodic() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        xEntry = table.getEntry("tx");
        yEntry = table.getEntry("ty");
        areaEntry = table.getEntry("ta");
        idEntry = table.getEntry("tid");
    }
}
