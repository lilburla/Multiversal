package org.firstinspires.ftc.teamcode.code.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.code.Robot.ConfiguredRobot;

@TeleOp(name = "IMU Test", group = "Tests")
public class IMUtest extends OpMode {
    private ConfiguredRobot robot;

    @Override
    public void init() {
        robot = new ConfiguredRobot(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("IMU configured", robot.imu != null);
        telemetry.addData("Heading (deg)", robot.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Heading (rad)", robot.getHeading(AngleUnit.RADIANS));
    }
}
