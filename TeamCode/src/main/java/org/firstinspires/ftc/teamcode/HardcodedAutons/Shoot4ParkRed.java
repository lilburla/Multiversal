package org.firstinspires.ftc.teamcode.HardcodedAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;
//TEST WHICH ONE IS UNIVERSAL AT THE MEETING
@Autonomous
public class Shoot4ParkRed extends LinearOpMode {
    private static final double ALLIANCE_SIGN = -1.0;
    private static final double DRIVE_POWER = 0.4;
    private static final long SPIN_UP_MS = 1500;
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
        //Method for shooting quatro balls
        robot.runShooter();
        sleep(SPIN_UP_MS);
        robot.openServoGate();
        //It's flywheels move for 4500 MS where 4 pollen - please change el value
        sleep(SHOOT_MS);
        robot.closeServoGate();
    }

    private void park() {
        robot.driveAuton(DRIVE_POWER * ALLIANCE_SIGN, 0, 0);
        sleep(PARK_MS);
        robot.driveAuton(0, 0, 0);
    }

    private void stopAll() {
        robot.driveAuton(0, 0, 0);
        robot.closeServoGate();
    }
}