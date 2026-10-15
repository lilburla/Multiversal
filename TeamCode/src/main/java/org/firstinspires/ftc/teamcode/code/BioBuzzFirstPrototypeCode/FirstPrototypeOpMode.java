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

    //Shooting and flywheels
    NewPositionalServo shootingServo = new NewPositionalServo(hardwareMap, "shooting_servo");
    NewMotor flyWheelMotor1 = new NewMotor(hardwareMap, "flywheel_1");
    NewMotor flyWheelMotor2 = new NewMotor(hardwareMap, "flywheel_2");

    //Intake/Outtake Motors
    NewMotor intakeMotor = new NewMotor(hardwareMap, "intake_motor");
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        //THE gamepad1.lefttrigger>0.8 IS FOR SLOW MODE, WILL ACTIVATE WHEN LEFTTRIGGER IS AT LEAST 80% PRESSED
        drive.driveFieldRelative(forward,strafe,rotate, gamepad1.left_trigger > 0.8);
        //THE gamepad1.righttrigger>0.8 IS FOR SHOOTING, WILL SHOOT WHEN RIGHTTRIGGER IS AT LEAST 80% PRESSED
        if(gamepad1.right_trigger > 0.8){
            flyWheelMotor1.setPower(0.5);
            flyWheelMotor2.setPower(-0.5);
            shootingServo.setServoPos(1);
        }else{
            flyWheelMotor1.setPower(0);
            flyWheelMotor2.setPower(0);
            shootingServo.setServoPos(0.5);
        }
        //REMAP TO BACK RIGHT/BACK LEFT BUTTONS WHEN I FIND OUT HOW :skull:
        if(gamepad1.a){ //intake - back right button
            intakeMotor.setPower(0.6);
        }else if(gamepad1.b){ //intake - back left button
            intakeMotor.setPower(-0.6);
        }else{
            intakeMotor.setPower(0);
        }
    }
}


