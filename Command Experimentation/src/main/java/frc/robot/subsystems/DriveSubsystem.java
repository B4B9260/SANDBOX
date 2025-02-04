package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants;
import frc.robot.Constants.driveMotors;

public class DriveSubsystem extends SubsystemBase {

    private final WPI_VictorSPX leftDrive = new WPI_VictorSPX(driveMotors.kLeftDrive);
    private final WPI_VictorSPX leftFollower = new WPI_VictorSPX(driveMotors.kLeftFollower);
    private final WPI_VictorSPX rightDrive = new WPI_VictorSPX(driveMotors.kRightDrive);
    private final WPI_VictorSPX rightFollower = new WPI_VictorSPX(driveMotors.kRightFollower);

    private final Limelight limelight = new Limelight();

    private final DifferentialDrive differentialDrive = new DifferentialDrive(leftDrive, rightDrive);

    private final PIDController autoAlignPID = new PIDController(0.5,0,0);

    double drive;
    double turn;


    public DriveSubsystem() {

        leftDrive.setInverted(true);
        leftFollower.follow(leftDrive);
        leftFollower.setInverted(true);
        rightFollower.follow(rightDrive);
    

    }

    @Override
    public void periodic() {

    }

    public Command set(double drive, double turn){
        return runEnd(
            () -> {
                this.drive = drive;
                this.turn = turn;
                SmartDashboard.putNumber("Drive",this.drive);
                differentialDrive.arcadeDrive(this.drive, this.turn);
            },
            () -> {
                differentialDrive.stopMotor();
            }
        );
    }

    /*public Command reducedDrive (double drive, double turn){
        return runEnd(
            () -> {
                differentialDrive.arcadeDrive(drive*0.5,turn*0.7);
            },
            () -> {
                differentialDrive.stopMotor();
            }
        );
    } */

    public Command driveForward (){
        return runEnd(
            () -> {
                differentialDrive.arcadeDrive(0.4,0);
            },
            () -> {
                differentialDrive.stopMotor();
            }
        );
    }

    public Command autoAlign(){
        return runEnd(
            () -> {
                if (limelight.getTID()==7){

                }
            },
            () -> {

            }
        );
    }

    public void stop(){
        differentialDrive.stopMotor();
    }
    
}
