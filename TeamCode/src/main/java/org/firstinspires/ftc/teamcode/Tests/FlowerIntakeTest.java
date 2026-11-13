package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class FlowerIntakeTest extends OpMode {
    private static final double intake = 0;
    private static final double none = 1;
    private Servo leftFlowerIntakeServo, rightFlowerIntakeServo;

    @Override
    public void init() {
        leftFlowerIntakeServo = hardwareMap.get(Servo.class, "leftFlowerMech");
        rightFlowerIntakeServo = hardwareMap.get(Servo.class, "rightFlowerMech");
        rightFlowerIntakeServo.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            leftFlowerIntakeServo.setPosition(intake);
            rightFlowerIntakeServo.setPosition(intake);
        }else{
            leftFlowerIntakeServo.setPosition(none);
            rightFlowerIntakeServo.setPosition(none);
        }
    }
}

