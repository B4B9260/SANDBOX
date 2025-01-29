// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.DriveSubsystem;

public class Robot extends TimedRobot {

  private final CommandXboxController controller = new CommandXboxController(0);
  //private final XboxController controller = new XboxController(0);
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

  private Command m_autonomousCommand;

  public Robot() {
    configureBindings();
    defaultCommands();
  }

  private void configureBindings(){
    //controller.leftTrigger().whileTrue(driveSubsystem.reducedDrive(controller.getLeftY(), controller.getRightX()));

  }

  private void defaultCommands(){
    driveSubsystem.setDefaultCommand(driveSubsystem.set(controller.getLeftY(), controller.getRightX()));
    controller.a().whileTrue(driveSubsystem.driveForward());
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    driveSubsystem.set(controller.getLeftY(),controller.getRightX());
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
