package org.firstinspires.ftc.teamcode.HardcodedAutons;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;

//IMPORTANT: THIS AUTON IS MADE WITH ESTIMATED VALUES, MOST LIKELY WONT WORK PERFECTLY, NEED TO BRUTE FORCE DEBUG
//REASON I NAMED THIS BOTH IS BECAUSE THE FIELD IS SYMMETRICAL WITH RESPECT TO THE VERY MIDDLE (rotating half the field upside down will result in them perfectly layering on eachother)
@Autonomous
public class ShootCollectThenShoot extends LinearOpMode {

    private static final double TRIGGER_THRESHOLD = 0.8;
    private static final double FLYWHEEL_POWER = 0.25;
    private static final double INTAKE_POWER = 0.4;
    private static final double GATE_OPEN = 0;
    private static final double GATE_CLOSED = 1;

    ConfiguredRobot robot = new ConfiguredRobot();

    private ElapsedTime timer;

    private Servo leftFlowerIntakeServo, rightFlowerIntakeServo;
    private final double MOVING_CONSTANT = 0.4;
    private final double ROTATE_CONSTANT = 0.4;

    private final int TURN_NINETY_DEGREES_WAIT_TIME_MILLISECONDS = 500;


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        leftFlowerIntakeServo = hardwareMap.get(Servo.class, "leftFlowerMech");
        rightFlowerIntakeServo = hardwareMap.get(Servo.class, "rightFlowerMech");
        rightFlowerIntakeServo.setDirection(Servo.Direction.REVERSE);
        timer = new ElapsedTime();
        robot.runShooter();
        waitForStart();

        if (opModeIsActive()) {
            shoot();
            timer.reset();
            //Intake corner balls
            robot.driveAuton(0.0, 0.0, -ROTATE_CONSTANT);
            wait(TURN_NINETY_DEGREES_WAIT_TIME_MILLISECONDS);
            robot.driveAuton(MOVING_CONSTANT,0,0);
            wait(1000);
            robot.driveAuton(0.0, 0.0, ROTATE_CONSTANT);
            wait(TURN_NINETY_DEGREES_WAIT_TIME_MILLISECONDS);
            robot.driveAuton(-MOVING_CONSTANT, 0.0, 0.0);
            wait(250);
            //intaking balls
            robot.driveAuton(0,0,0);
            wait(2000);
            //Moving to other side of hive
            robot.driveAuton(MOVING_CONSTANT, MOVING_CONSTANT/2, 0);
            wait(3000);
            robot.driveAuton(0,0,ROTATE_CONSTANT);
            wait(TURN_NINETY_DEGREES_WAIT_TIME_MILLISECONDS*2);
            shoot();
            /*
            //Flower intake - HELLA scuffed
            robot.driveAuton(0,0,rotateConstant);
            wait(500);
            robot.driveAuton(movingConstant,0,0);
            wait(200);
            robot.driveAuton(0,0,rotateConstant);
            wait(500);
            robot.driveAuton(movingConstant,0,0);
            wait(200);
            intakeFlower();
            //Going back and shooting
            robot.driveAuton(0,0,rotateConstant);
            wait(1000);
            robot.driveAuton(movingConstant,0,0);
            wait(200);
            robot.driveAuton(0,0,-rotateConstant);
            wait(500);
            robot.driveAuton(movingConstant,0,0);
            wait(200);
            robot.driveAuton(0,0,-rotateConstant);
            wait(500);
            shoot();
            //parking
            */
            robot.driveAuton(0,0,ROTATE_CONSTANT);
            wait(TURN_NINETY_DEGREES_WAIT_TIME_MILLISECONDS);
            robot.driveAuton(MOVING_CONSTANT,0,0);
            wait(1000);
            robot.driveAuton(0,0,0);
        }

    }
    public void shoot(){
        robot.openServoGate();
        wait(3000);
        robot.closeServoGate();
    }
    public void wait(int milliseconds){
        while(opModeIsActive()&& timer.milliseconds() < milliseconds){}
        timer.reset();
    }

    public void intakeFlower() {
        leftFlowerIntakeServo.setPosition(0);
        rightFlowerIntakeServo.setPosition(0);
        wait(2000);
        leftFlowerIntakeServo.setPosition(1);
        rightFlowerIntakeServo.setPosition(1);

    }
}

