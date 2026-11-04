package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMecanumDrive;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMotor;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewPositionalServo;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfig;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfigReader;

/*
Controls (gamepad1):
  Left stick   - drive (forward / strafe), field relative
  Right stick  - rotate
  Left trigger - slow mode (at least 80% pressed)
  Right trigger- shoot: opens the gate and runs the intake (at least 80% pressed)
  A            - intake
  B            - outtake
Flywheels run constantly.
 */
@TeleOp
public class FirstPrototypeOpMode extends OpMode {
    private static final double TRIGGER_THRESHOLD = 0.8;
    private static final double FLYWHEEL_POWER = 0.35;
    private static final double INTAKE_POWER = 0.6;
    private static final double GATE_OPEN = 1;
    private static final double GATE_CLOSED = 0.5;

    // These need the hardwareMap, which only exists once init() runs, so they are created there.
    private final NewMecanumDrive drive = new NewMecanumDrive();
    private RobotConfig config;
    private NewPositionalServo shootingServo;
    private NewMotor flyWheelMotor1;
    private NewMotor flyWheelMotor2;
    private NewMotor intakeMotor;

    // Flower intake servos (not in use yet)
    //private NewPositionalServo flowerIntakeServo1;
    //private NewPositionalServo flowerIntakeServo2;

    @Override
    public void init() {
        config = RobotConfigReader.read(hardwareMap);

        drive.init(hardwareMap, config);

        shootingServo = new NewPositionalServo(hardwareMap, config.getShootingServoName());
        flyWheelMotor1 = new NewMotor(hardwareMap, config.getFlywheelMotor1Name());
        flyWheelMotor2 = new NewMotor(hardwareMap, config.getFlywheelMotor2Name());
        intakeMotor = new NewMotor(hardwareMap, config.getIntakeMotorName());
    }

    @Override
    public void loop() {
        double forward = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        boolean slowMode = gamepad1.left_trigger > TRIGGER_THRESHOLD;
        boolean shooting = gamepad1.right_trigger > TRIGGER_THRESHOLD;
        boolean intake = gamepad1.a;
        boolean outtake = gamepad1.b;

        drive.driveFieldRelative(forward, strafe, rotate, slowMode);

        // Gate opens only while shooting
        shootingServo.setServoPos(shooting ? GATE_OPEN : GATE_CLOSED);

        flyWheelMotor1.setPower(FLYWHEEL_POWER);
        flyWheelMotor2.setPower(-FLYWHEEL_POWER);

        // Intake motor: A / B take priority, and shooting also feeds the intake.
        // (Previously the A/B block overwrote the shooting power with 0, so the intake never fed.)
        if (intake) {
            intakeMotor.setPower(INTAKE_POWER);
        } else if (outtake) {
            intakeMotor.setPower(-INTAKE_POWER);
        } else if (shooting) {
            intakeMotor.setPower(INTAKE_POWER);
        } else {
            intakeMotor.setPower(0);
        }
    }
}
