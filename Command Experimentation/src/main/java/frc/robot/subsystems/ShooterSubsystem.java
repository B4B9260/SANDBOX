package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private final WPI_VictorSPX intakeMotor = new WPI_VictorSPX(5);
    
    private final Limelight limelight = new Limelight();

    public ShooterSubsystem(){


    }

    public Command intakeWithID(){
        return runEnd(
            () -> {
                limelight.getTID();
                if (limelight.getTID()==0){
                    intakeMotor.set(0.2);
                } else {
                    intakeMotor.stopMotor();
                }
            },
            () -> {
                intakeMotor.stopMotor();
            }
        );

    }

}
