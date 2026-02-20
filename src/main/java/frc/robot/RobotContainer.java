// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.lang.management.OperatingSystemMXBean;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RobotConstants;
import frc.robot.commands.IndexerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.RunMotor;
import frc.robot.commands.ShootBall;
import frc.robot.commands.ShooterCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.MotorOpperator;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private final SendableChooser<Command> autoChooser;

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    Intake intake = new Intake();
    Shooter shooter = new Shooter();
    Indexer indexer = new Indexer();

    public final MotorOpperator shooterMotor = new MotorOpperator(41);
    public final MotorOpperator indexMotor = new MotorOpperator(42);

    private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private final CommandXboxController m_gameOperatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);


    public final CommandSwerveDrivetrain swerveDrivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();

        NamedCommands.registerCommand("printWhenDone", Commands.print("I am done with pathplanner auto"));
        NamedCommands.registerCommand("shootBall", new ShootBall(indexMotor, shooterMotor));

          // Build an auto chooser. This will use Commands.none() as the default option.
        autoChooser = AutoBuilder.buildAutoChooser();
        
        autoChooser.addOption("test", new PathPlannerAuto("test"));
        autoChooser.addOption("FirstAuto", new PathPlannerAuto("FirstAuto"));
        autoChooser.addOption("averysauto", new PathPlannerAuto("averysauto"));

        // Another option that allows you to specify the default auto by its name
        // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

        SmartDashboard.putData("Auto Chooser", autoChooser);
    }


    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
       swerveDrivetrain.setDefaultCommand(
            //swerveDrivetrain will execute this command periodically
           swerveDrivetrain.applyRequest(() ->
                drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed * swerveDrivetrain.governor.getGovernor()) // Drive forward with negative Y (forward)
                    .withVelocityY(-m_driverController.getLeftX() * MaxSpeed * swerveDrivetrain.governor.getGovernor()) // Drive left with negative X (left)
                    .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate * swerveDrivetrain.governor.getGovernor()) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
           swerveDrivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );


        Trigger driverBackLeft = m_driverController.button(Constants.OperatorConstants.kLeftBackButton);
        Trigger driverBackRight = m_driverController.button(Constants.OperatorConstants.kRightBackButton);
        Trigger slowMode = m_driverController.leftBumper();
        Trigger fastMode = m_driverController.rightBumper();
        Trigger reduceSpeed = m_driverController.axisGreaterThan(Constants.OperatorConstants.leftTriggerAxis,.6);
        Trigger increaseSpeed = m_driverController.axisGreaterThan(Constants.OperatorConstants.rightTriggerAxis,.6);
        Trigger driverRightBack = m_driverController.button(Constants.OperatorConstants.kRightBackButton);
        Trigger resetDriverGyro = m_driverController.button(Constants.OperatorConstants.kLeftBackButton);

        slowMode.onTrue(Commands.runOnce(swerveDrivetrain.governor::setSlowMode, swerveDrivetrain));
        fastMode.onTrue(Commands.runOnce(swerveDrivetrain.governor::setFastMode, swerveDrivetrain));
        reduceSpeed.onTrue(Commands.runOnce(swerveDrivetrain.governor::decrement, swerveDrivetrain));
        increaseSpeed.onTrue(Commands.runOnce(swerveDrivetrain.governor::increment, swerveDrivetrain));
        m_driverController.povUp().whileTrue(new ShootBall(indexMotor, shooterMotor));
        

        resetDriverGyro.whileTrue(new ResetGyro(swerveDrivetrain));

        m_driverController.a().whileTrue(swerveDrivetrain.applyRequest(() -> brake));
        m_driverController.b().whileTrue(swerveDrivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        m_driverController.back().and(m_driverController.y()).whileTrue(swerveDrivetrain.sysIdDynamic(Direction.kForward));
        m_driverController.back().and(m_driverController.x()).whileTrue(swerveDrivetrain.sysIdDynamic(Direction.kReverse));
        m_driverController.start().and(m_driverController.y()).whileTrue(swerveDrivetrain.sysIdQuasistatic(Direction.kForward));
        m_driverController.start().and(m_driverController.x()).whileTrue(swerveDrivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        m_driverController.leftBumper().onTrue(swerveDrivetrain.runOnce(swerveDrivetrain::seedFieldCentric));

       swerveDrivetrain.registerTelemetry(logger::telemeterize);


        // m_gameOperatorController.x().whileTrue(new RunMotor(shooterMotor, 0.8));
        // m_gameOperatorController.y().whileTrue(new RunMotor(indexMotor, -0.5));
        m_gameOperatorController.y().debounce(OperatorConstants.debounceTimeForButton).onTrue(new IntakeCommand(intake));
        m_gameOperatorController.b().whileTrue(Commands.runOnce(intake::backwardIntakeOn, intake)).onFalse(Commands.runOnce(intake::stopIntake, intake));
        m_gameOperatorController.x().debounce(OperatorConstants.debounceTimeForButton).onTrue(new ShooterCommand(shooter));
        m_gameOperatorController.a().whileTrue(new IndexerCommand(indexer));
    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        // final var idle = new SwerveRequest.Idle();
        // return Commands.sequence(
        //     // Reset our field centric heading to match the robot
        //     // facing away from our alliance station wall (0 deg).
        //    swerveDrivetrain.runOnce(() ->swerveDrivetrain.seedFieldCentric(Rotation2d.kZero)),
        //     // Then slowly drive forward (away from us) for 5 seconds.
        //    swerveDrivetrain.applyRequest(() ->
        //         drive.withVelocityX(0.5)
        //             .withVelocityY(0)
        //             .withRotationalRate(0)
        //     )
        //     .withTimeout(5.0),
        //     // Finally idle for the rest of auton
        //    swerveDrivetrain.applyRequest(() -> idle)
        // );


        // Build an auto chooser. This will use Commands.none() as the default option.
        return autoChooser.getSelected();
    }
}
