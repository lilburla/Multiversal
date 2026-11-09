package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;
import org.firstinspires.ftc.teamcode.mechanisms.MechanumDrive;

@Autonomous
public class ParkAuton extends LinearOpMode {
    ConfiguredRobot robot = new ConfiguredRobot();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        telemetry.addLine("Init Complete");

        waitForStart();

        if (opModeIsActive()) {
            robot.driveAuton(0.05, 0.0, 0.0);
            timer.reset();

            while (opModeIsActive() && timer.milliseconds() < 1500) {}

            robot.driveAuton(0.0, 0.0, 0.0);
        }
    }
}
