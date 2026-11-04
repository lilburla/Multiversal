package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMecanumDrive;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMotor;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewPositionalServo;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfig;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfigReader;

@Autonomous(name = "Shoot Intake Shoot Park Red", group = "Red")
public class ShootIntakeShootParkRed extends LinearOpMode {
    private static final double ALLIANCE_SIGN = -1.0;

    private static final double FLYWHEEL_POWER = 0.35;
    private static final double INTAKE_POWER = 0.6;
    private static final double GATE_OPEN = 1.0;
    private static final double GATE_CLOSED = 0.5;
    private static final double DRIVE_POWER = 0.4;
    private static final double STRAFE_POWER = 0.4;

    private static final long SPIN_UP_MS = 1500;
    private static final long SHOOT_MS = 3000;
    private static final long TO_BALLS_MS = 1500;
    private static final long INTAKE_MS = 2000;
    private static final long TO_SHOT_MS = 1500;
    private static final long PARK_MS = 1200;

    private final NewMecanumDrive drive = new NewMecanumDrive();

    private NewMotor flywheel1;
    private NewMotor flywheel2;
    private NewMotor intake;
    private NewPositionalServo gate;

    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfigReader.read(hardwareMap);
        drive.init(hardwareMap, config);
        flywheel1 = new NewMotor(hardwareMap, config.getFlywheelMotor1Name());
        flywheel2 = new NewMotor(hardwareMap, config.getFlywheelMotor2Name());
        intake = new NewMotor(hardwareMap, config.getIntakeMotorName());
        gate = new NewPositionalServo(hardwareMap, config.getShootingServoName());
        gate.setServoPos(GATE_CLOSED);

        waitForStart();

        shoot();
        strafe(STRAFE_POWER * ALLIANCE_SIGN, TO_BALLS_MS);
        intakeBalls();
        strafe(-STRAFE_POWER * ALLIANCE_SIGN, TO_SHOT_MS);
        shoot();
        forward(DRIVE_POWER, PARK_MS);
        stopAll();
    }

    private void shoot() {
        spinFlywheels(FLYWHEEL_POWER);
        sleep(SPIN_UP_MS);
        gate.setServoPos(GATE_OPEN);
        intake.setPower(INTAKE_POWER);
        sleep(SHOOT_MS);
        gate.setServoPos(GATE_CLOSED);
        intake.setPower(0);
        spinFlywheels(0);
    }

    private void intakeBalls() {
        intake.setPower(INTAKE_POWER);
        sleep(INTAKE_MS);
        intake.setPower(0);
    }

    private void forward(double power, long milliseconds) {
        drive.drive(power, 0, 0, false);
        sleep(milliseconds);
        drive.drive(0, 0, 0, false);
    }

    private void strafe(double power, long milliseconds) {
        drive.drive(0, power, 0, false);
        sleep(milliseconds);
        drive.drive(0, 0, 0, false);
    }

    private void spinFlywheels(double power) {
        flywheel1.setPower(power);
        flywheel2.setPower(-power);
    }

    private void stopAll() {
        drive.drive(0, 0, 0, false);
        spinFlywheels(0);
        intake.setPower(0);
        gate.setServoPos(GATE_CLOSED);
    }
}
