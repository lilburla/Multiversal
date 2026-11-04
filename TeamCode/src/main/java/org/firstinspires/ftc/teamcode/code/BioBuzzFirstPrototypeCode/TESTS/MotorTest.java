package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.TESTS;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MotorTest extends OpMode {
    private DcMotor motor; //Creates the motor reference
    private DcMotor motor2;
    private DcMotor motor3;
    double motorPowers = 0;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "leftShooter");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2 = hardwareMap.get(DcMotor.class, "rightShooter");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor3 = hardwareMap.get(DcMotor.class, "intakeMotor");
        motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {
        telemetry.addData("Motor Power:", motorPowers);
        motor.setPower(motorPowers);
        motor2.setPower((-1) * motorPowers);
        if(gamepad1.rightBumperWasPressed()){
            motorPowers += 0.05;
        }
        if(gamepad1.a) {
            motor3.setPower(.3);
        }
    }
}
