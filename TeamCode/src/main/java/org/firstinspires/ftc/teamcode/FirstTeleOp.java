package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.MechanumDrive;

@TeleOp
public class FirstTeleOp extends OpMode {
    private static final double TRIGGER_THRESHOLD = 0.8;
    private static final double FLYWHEEL_POWER = 0.305;
    private static final double INTAKE_POWER = 0.45;

    private static final double GATE_OPEN = 0;
    private static final double GATE_CLOSED = 1;
    private static final double REG_MODE = 0.6;
    private static final double SLOW_MODE = 0.15;
    MechanumDrive drive = new MechanumDrive();
    double forward, strafe, rotate;
    private DcMotor fly1, fly2, intake;
    private Servo gate;

    public void setPos(double angle) {
        gate.setPosition(angle);
    }

    @Override
    public void init() {
        drive.init(hardwareMap);
        fly1 = hardwareMap.get(DcMotor.class, "flywheel_1");
        fly2 = hardwareMap.get(DcMotor.class, "flywheel_2");
        intake = hardwareMap.get(DcMotor.class, "intake_motor");
        gate = hardwareMap.get(Servo.class, "shooting_servo");
        setPos(GATE_CLOSED);
    }

    @Override
    public void loop() {
        fly1.setPower(FLYWHEEL_POWER);
        fly2.setPower(-1 * FLYWHEEL_POWER);

        forward = -1 * gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad1.left_trigger >= TRIGGER_THRESHOLD) {
            drive.driveFieldOriented(forward * SLOW_MODE, strafe * SLOW_MODE, rotate * SLOW_MODE);
        } else {
            drive.driveFieldOriented(forward * REG_MODE, strafe * REG_MODE, rotate * REG_MODE);
        }

        if (gamepad1.right_trigger >= TRIGGER_THRESHOLD) {
            setPos(GATE_OPEN);
        } else {
            setPos(GATE_CLOSED);
        }

        if (gamepad1.b) {
            intake.setPower(INTAKE_POWER);
        } else {
            intake.setPower(-1 * INTAKE_POWER);
        }

        if (gamepad1.dpad_left) {
            drive.resetHeading();
        }
    }
}