package org.firstinspires.ftc.teamcode.code.Robot;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AprilTag {
    private Limelight3A limelight;
    private IMU imu;

    private double tx;
    private double ty;
    private double ta;
    private boolean hasTarget;

    public void init(ConfiguredRobot robot) {
        limelight = robot.limelight;
        imu = robot.imu;
        if (limelight != null) {
            limelight.pipelineSwitch(0);
        }
    }

    public void start() {
        if (limelight != null) {
            limelight.start();
        }
    }

    public void stop() {
        if (limelight != null) {
            limelight.stop();
        }
    }

    public void loop() {
        if (limelight == null) {
            return;
        }
        if (imu != null) {
            limelight.updateRobotOrientation(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        }
        LLResult result = limelight.getLatestResult();
        hasTarget = result != null && result.isValid();
        if (hasTarget) {
            tx = result.getTx();
            ty = result.getTy();
            ta = result.getTa();
        }
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public double getTa() {
        return ta;
    }
}
