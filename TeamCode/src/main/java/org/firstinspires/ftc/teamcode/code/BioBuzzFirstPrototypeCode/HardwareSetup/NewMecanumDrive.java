package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfig;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfigReader;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class NewMecanumDrive{
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private IMU imu;

    public void init(HardwareMap hwMap, RobotConfig config){
        frontLeftMotor = hwMap.get(DcMotor.class, config.getFrontLeftDriveName());
        frontRightMotor = hwMap.get(DcMotor.class, config.getFrontRightDriveName());
        backLeftMotor = hwMap.get(DcMotor.class, config.getBackLeftDriveName());
        backRightMotor = hwMap.get(DcMotor.class, config.getBackRightDriveName());

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, config.getImuName());
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(RevOrientation));

    }

    public void drive(double forward, double strafe, double rotate, boolean slowMode){
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));
        if(slowMode){
            frontLeftMotor.setPower((frontLeftPower/maxPower)*(0.05));
            frontRightMotor.setPower((frontRightPower/maxPower)*(0.05));
            backLeftMotor.setPower((backLeftPower/maxPower)*(0.05));
            backRightMotor.setPower((backRightPower/maxPower)*(0.05));
        }else{
            frontLeftMotor.setPower(frontLeftPower/maxPower);
            frontRightMotor.setPower(frontRightPower/maxPower);
            backLeftMotor.setPower(backLeftPower/maxPower);
            backRightMotor.setPower(backRightPower/maxPower);
        }


    }

    public void driveFieldRelative(double forward, double strafe, double rotate, boolean slowMode){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe,forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward,newStrafe,rotate, slowMode);
    }
}