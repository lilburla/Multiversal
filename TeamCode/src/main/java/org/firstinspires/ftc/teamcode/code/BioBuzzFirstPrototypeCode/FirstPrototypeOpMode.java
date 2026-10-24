package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMecanumDrive;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMotor;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewPositionalServo;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfig;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfigReader;
/*
SO FAR I HAVE FINISHED:
INTAKE AND OUTTAKE (change keybinds though i cant find back right and back left button)
SHOOT
SLOW MODE
 */
@TeleOp
public class FirstPrototypeOpMode extends OpMode {
    //Mecanum Drive
    NewMecanumDrive drive = new NewMecanumDrive();
    double forward, strafe, rotate;
    RobotConfig config = RobotConfigReader.read(hardwareMap);

    //Shooting and flywheels
    NewPositionalServo shootingServo = new NewPositionalServo(hardwareMap, config.getShootingServoName());
    NewMotor flyWheelMotor1 = new NewMotor(hardwareMap, config.getFlywheelMotor1Name());
    NewMotor flyWheelMotor2 = new NewMotor(hardwareMap, config.getFlywheelMotor2Name());

    //Intake/Outtake Motors
    NewMotor intakeMotor = new NewMotor(hardwareMap, config.getIntakeMotorName());

    //Flower intake servos flower_intake_servo_1
    NewPositionalServo flowerIntakeServo1 = new NewPositionalServo(hardwareMap, config.getFlowerIntakeServo1Name());
    NewPositionalServo flowerIntakeServo2 = new NewPositionalServo(hardwareMap, config.getFlowerIntakeServo2Name());

    //booleans
    boolean leftTriggerPressed, rightTriggerPressed, m1Pressed, m2Pressed;
    @Override
    public void init() {
        drive.init(hardwareMap);
        flowerIntakeServo2.reverse();
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        m1Pressed = gamepad1.a;
        m2Pressed = gamepad1.b;

        //THE gamepad1.lefttrigger>0.8 IS FOR SLOW MODE, WILL ACTIVATE WHEN LEFTTRIGGER IS AT LEAST 80% PRESSED
        leftTriggerPressed = gamepad1.left_trigger > 0.8;

        //THE gamepad1.righttrigger>0.8 IS FOR SHOOTING, WILL SHOOT WHEN RIGHTTRIGGER IS AT LEAST 80% PRESSED
        rightTriggerPressed = gamepad1.right_trigger > 0.8;

        drive.driveFieldRelative(forward,strafe,rotate, leftTriggerPressed);
        if(rightTriggerPressed){
            shootingServo.setServoPos(1);
            intakeMotor.setPower(0.6);
        }else{
            shootingServo.setServoPos(0.5);
        }
        if(gamepad1.left_bumper){
            flowerIntakeServo1.setServoPos(0.5);
            flowerIntakeServo2.setServoPos(0.5);
        }else{
            flowerIntakeServo1.setServoPos(1);
            flowerIntakeServo2.setServoPos(1);
        }
        flyWheelMotor1.setPower(0.35);
        flyWheelMotor2.setPower(-0.35);
        //REMAP TO BACK RIGHT/BACK LEFT BUTTONS WHEN I FIND OUT HOW :skull:
        if(m1Pressed){ //intake - back right button
            intakeMotor.setPower(0.6);
        }else if(m2Pressed){ //outtake - back left button
            intakeMotor.setPower(-0.6);
        }else{
            intakeMotor.setPower(0);
        }
    }
}


