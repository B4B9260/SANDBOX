// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.LimelightHelpers;

public class Limelight extends SubsystemBase {

    /*double tx = LimelightHelpers.getTX(""); //Horizontal offset from target in degrees
    double ty = LimelightHelpers.getTY(""); //Vertical offset from target in degrees
    double ta = LimelightHelpers.getTA(""); //Target area (0% to 100% of image)
    boolean hasTarget = LimelightHelpers.getTV(""); //Checks if there is a valid target

    double txnc = LimelightHelpers.getTXNC(""); //Horizontal offset from principal pixel/point to target in degrees
    double tync = LimelightHelpers.getTYNC(""); //Vertical offset from principal pixel/point to target in degrees */

    CommandXboxController controller = new CommandXboxController(0);

    NetworkTableInstance limelight = NetworkTableInstance.getDefault();
    NetworkTable limelightTable = limelight.getTable("limelight");
    NetworkTableEntry tx = limelightTable.getEntry("tx");
    NetworkTableEntry ty = limelightTable.getEntry("ty");
    NetworkTableEntry tv = limelightTable.getEntry("tv");
    NetworkTableEntry ta = limelightTable.getEntry("ta");
    NetworkTableEntry tid = limelightTable.getEntry("tid");
    NetworkTableEntry botpose = limelightTable.getEntry("botpose_wpired");
    /* double[] temp = {0,0,0,0,0,0};
    double[] result = botpose.getDoubleArray(temp);
    Translation3d tran3d = new Translation3d(result[0],result[1],result[2]);
    Rotation3d r3d = new Rotation3d(result[3],result[4],result[5]);
    Pose3d p3d = new Pose3d(tran3d,r3d); */

    public long currentID = tid.getInteger(-1);

    public Limelight(){
        LimelightHelpers.setPipelineIndex("",0);
    }

    public Command getID(){
        return run(
            () -> {
                currentID = tid.getInteger(-1);
                if (currentID!=-1){
                    controller.setRumble(RumbleType.kBothRumble,0.5);
                }
                SmartDashboard.putNumber("Tracking ID: ", currentID);
                SmartDashboard.putNumber("ID 2", tid.getInteger(-1));
                SmartDashboard.putNumber("Target Acquired:", ta.getDouble(0));
                SmartDashboard.putNumber("Horizontal Offset", tx.getDouble(0));
                SmartDashboard.putNumberArray("botpose",botpose.getDoubleArray(new double[6]));
            }
        );

    }
    
    public long getTID(){
        return tid.getInteger(-1);
    }

    public double getTA(){
        return ta.getDouble(0);
    }

    public double getTX(){
        return tx.getDouble(0);
    }

    public double getTY(){
        return ty.getDouble(0);
    }

}
