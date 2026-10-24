package org.firstinspires.ftc.teamcode.code.Tests;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.code.Robot.ConfiguredRobot;

@TeleOp(name = "Limelight Test", group = "Tests")
public class LimelightTest extends OpMode {
    private ConfiguredRobot robot;

    @Override
    public void init() {
        robot = new ConfiguredRobot(hardwareMap);
        if (robot.limelight != null) {
            robot.limelight.pipelineSwitch(9);
        }
    }

    @Override
    public void start() {
        if (robot.limelight != null) {
            robot.limelight.start();
        }
    }

    @Override
    public void loop() {
        if (robot.limelight == null) {
            telemetry.addData("Limelight", "not set in robotConfig.yml");
            return;
        }
        LLResult result = robot.limelight.getLatestResult();
        telemetry.addData("Valid", result != null && result.isValid());
        if (result != null && result.isValid()) {
            telemetry.addData("Tx", result.getTx());
            telemetry.addData("Ty", result.getTy());
            telemetry.addData("Ta", result.getTa());
        }
    }

    @Override
    public void stop() {
        if (robot.limelight != null) {
            robot.limelight.stop();
        }
    }
}
