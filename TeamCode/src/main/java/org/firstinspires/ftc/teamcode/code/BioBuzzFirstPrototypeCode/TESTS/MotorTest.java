package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.TESTS;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class MotorTest extends OpMode {
    private DcMotor motor; //Creates the motor reference
    private DcMotor motor2;
    double motorPowers = 0;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "left_shooter");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2 = hardwareMap.get(DcMotor.class, "right_shooter");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        telemetry.addData("Motor Power:", motorPowers);
        motor.setPower(motorPowers);
        motor2.setPower((-1) * motorPowers);
        if(gamepad1.a){
            motorPowers += 0.05;
        }
    }
}
