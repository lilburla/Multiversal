package org.firstinspires.ftc.teamcode.HardcodedAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;

@Autonomous
public class Shoot4ParkRed extends LinearOpMode {
    private static final double ALLIANCE_SIGN = -1.0;
    private static final double DRIVE_POWER = 0.4;
    private static final long TINY_MOVE_MS = 300; // Adjust this time if it moves too much/little
    private static final long SPIN_UP_MS = 1500;
    private static final long SHOOT_MS = 4500;
    private static final long INTAKE_MS = 3000;
    private static final long PARK_MS = 1500;

    private final ConfiguredRobot robot = new ConfiguredRobot();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            shoot4Balls();
            park();
            stopAll();
        }
    }

    private void shoot4Balls() {
        robot.driveAuton(0.07, 0, 0);
        sleep(200);
        robot.openServoGate();
        sleep(2000);

        robot.intake();
        robot.runShooter();

        // Wait for flywheels to spin up
        sleep(SPIN_UP_MS + 2000 + SHOOT_MS);

        // Open gate to shoot balls
//        robot.openServoGate();
  //      sleep(SHOOT_MS + 1000);


        robot.closeServoGate();
        robot.stopIntake();
    }

    private void park() {
        robot.driveAuton(DRIVE_POWER * ALLIANCE_SIGN, 0, 0);
        sleep(PARK_MS);
        robot.driveAuton(0, 0, 0);
    }

    private void stopAll() {
        robot.driveAuton(0, 0, 0);
        robot.closeServoGate();
        robot.stopIntake();
    }
}