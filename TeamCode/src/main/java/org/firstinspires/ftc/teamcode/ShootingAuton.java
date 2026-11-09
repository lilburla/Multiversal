package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;
import org.firstinspires.ftc.teamcode.mechanisms.FlyShooter;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.MechanumDrive;

@Autonomous
public class ShootingAuton extends LinearOpMode {
    ConfiguredRobot robot = new ConfiguredRobot();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        telemetry.addLine("Init Complete");

        waitForStart();

        if (opModeIsActive()) {

        }
    }
}
