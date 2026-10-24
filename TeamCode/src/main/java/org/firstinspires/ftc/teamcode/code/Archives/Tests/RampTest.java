package org.firstinspires.ftc.teamcode.code;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.code.Robot.AprilTag;
import org.firstinspires.ftc.teamcode.code.Robot.ConfiguredRobot;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Competition")
public class TeleOp extends OpMode {
    private ConfiguredRobot robot;
    private final AprilTag aprilTag = new AprilTag();

    @Override
    public void init() {
        robot = new ConfiguredRobot(hardwareMap);
        aprilTag.init(robot);
        telemetry.addData("Robot", robot.config.getName());
    }

    @Override
    public void start() {
        aprilTag.start();
    }

    @Override
    public void loop() {
        robot.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        robot.intake(gamepad1.right_bumper ? 1.0 : 0.0);
        robot.launch(gamepad1.left_bumper ? 1.0 : 0.0);

        double heading = robot.getHeading(AngleUnit.RADIANS);
        robot.setServoPosition(heading < 0.5 && heading > -0.5 ? 0.75 : 0.25);

        aprilTag.loop();

        telemetry.addData("Heading (deg)", robot.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Has Target", aprilTag.hasTarget());
        telemetry.addData("Target X", aprilTag.getTx());
        telemetry.addData("Target Y", aprilTag.getTy());
        telemetry.addData("Target A", aprilTag.getTa());
    }

    @Override
    public void stop() {
        robot.drive(0, 0, 0);
        robot.intake(0);
        robot.launch(0);
        aprilTag.stop();
    }
}
