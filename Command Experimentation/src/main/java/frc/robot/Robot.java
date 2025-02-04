// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Kilo;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot {

  public final CommandXboxController controller = new CommandXboxController(0);
  //private final XboxController controller = new XboxController(0);
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final Limelight limelight = new Limelight();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  private Command m_autonomousCommand;

  public Robot() {
    configureBindings();
    defaultCommands();
  }

  public void configureBindings(){
    //controller.leftTrigger().whileTrue(driveSubsystem.reducedDrive(controller.getLeftY(), controller.getRightX()));
    controller.rightTrigger().whileTrue(limelight.getID());
    controller.x().whileTrue(shooterSubsystem.intakeWithID());
  }

  public void defaultCommands(){
    driveSubsystem.setDefaultCommand(driveSubsystem.set(controller.getLeftY(),controller.getRightX()));
    controller.a().whileTrue(driveSubsystem.driveForward());
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Left Y",controller.getLeftY());
    SmartDashboard.putNumber("Right X",controller.getRightX());
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
