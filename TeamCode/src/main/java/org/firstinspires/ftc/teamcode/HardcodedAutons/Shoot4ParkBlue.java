package org.firstinspires.ftc.teamcode.HardcodedAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;

@Autonomous
public class Shoot4ParkBlue extends LinearOpMode {
    private static final double DRIVE_POWER = 0.4;
    private static final long SPIN_UP_MS = 1500;
    //Provides Pause between the two flywheels
    private static final long SHOOT_MS = 4500;
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
        robot.runShooter();
        sleep(SPIN_UP_MS);
        robot.openServoGate();
        sleep(SHOOT_MS);
        robot.closeServoGate();
    }

    private void park() {
        robot.driveAuton(DRIVE_POWER, 0, 0);
        sleep(PARK_MS);
        robot.driveAuton(0, 0, 0);
    }

    private void stopAll() {
        robot.driveAuton(0, 0, 0);
        robot.closeServoGate();
    }
}