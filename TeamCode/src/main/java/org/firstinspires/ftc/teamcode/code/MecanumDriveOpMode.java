package org.firstinspires.ftc.teamcode.code;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


class MecanumDriveSetup{
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private IMU imu;

    public void init(HardwareMap hwMap){
        frontLeftMotor = hwMap.get(DcMotor.class, "front_left_drive");
        frontRightMotor = hwMap.get(DcMotor.class, "front_right_drive");
        backLeftMotor = hwMap.get(DcMotor.class, "back_left_drive");
        backRightMotor = hwMap.get(DcMotor.class, "back_right_drive");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(RevOrientation));

    }

    public void drive(double forward, double strafe, double rotate){
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeftMotor.setPower(frontLeftPower/maxPower);
        frontRightMotor.setPower(frontRightPower/maxPower);
        backLeftMotor.setPower(backLeftPower/maxPower);
        backRightMotor.setPower(backRightPower/maxPower);


    }

    public void driveFieldRelative(double forward, double strafe, double rotate){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe,forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward,newStrafe,rotate);
    }
}

@TeleOp
public class MecanumDriveOpMode extends OpMode {
    MecanumDriveSetup drive = new MecanumDriveSetup();
    double forward, strafe, rotate;
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward,strafe,rotate);

    }


}