package org.firstinspires.ftc.teamcode.HardcodedAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;
//GUYS PLEASE TEST THIS IDK IF THIS IS RIGHT

//Ryan this is different from the blue only in that the ALLIANCE_SIGN is different at -1, so parking goes the opposite way.
//IF THERE IS ANY LOGIC BUG, which there most CERTAINLY IS PLEASE CHANGE BOTH OF THe two autons
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
        robot.runShooter();
        sleep(SPIN_UP_MS);
        robot.openServoGate();
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