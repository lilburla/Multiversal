package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.function.BooleanSupplier;
import com.qualcomm.robotcore.util.Range;


public class MechanumDrive {
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private IMU imu;
    public void init(HardwareMap hwMap) {
        frontLeftMotor = hwMap.get(DcMotor.class, "front_left_drive");
        backLeftMotor = hwMap.get(DcMotor.class, "back_left_drive");
        frontRightMotor = hwMap.get(DcMotor.class, "front_right_drive");
        backRightMotor = hwMap.get(DcMotor.class, "back_right_drive");

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT, RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    public void drive(double forward, double strafe, double rotate) {
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeftMotor.setPower(maxSpeed * (frontLeftPower / maxPower));
        backLeftMotor.setPower(maxSpeed * (backLeftPower / maxPower));
        frontRightMotor.setPower(maxSpeed * (frontRightPower / maxPower));
        backRightMotor.setPower(maxSpeed * (backRightPower / maxPower));
    }

    public void driveFieldOriented(double forward, double strafe, double rotate) {
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        drive(newForward, newStrafe, rotate);
    }

    public void resetHeading() {
        imu.resetYaw();
    }

    public void turnToAngle(double targetAngleDegrees, double maxPower, BooleanSupplier opModeActive) {
        double kP = 0.04; // Proportional constant. Adjust this if the robot overshoots or is too slow.
        double tolerance = 1.5; // Stop turning within 1.5 degrees of accuracy
        double minPower = 0.15; // Minimum power to overcome friction and static slip

        double error = getAngleError(targetAngleDegrees);

        // Keep turning as long as error is outside tolerance AND user hasn't hit stop
        while (Math.abs(error) > tolerance && opModeActive.getAsBoolean()) {
            error = getAngleError(targetAngleDegrees);

            // Proportional power: bigger error = faster turn, smaller error = slower turn
            double turnPower = error * kP;

            // Cap the speed at maxPower, but make sure it has at least minPower to move
            turnPower = Range.clip(turnPower, -maxPower, maxPower);
            if (Math.abs(turnPower) < minPower) {
                turnPower = Math.signum(turnPower) * minPower;
            }

            drive(0, 0, turnPower);
        }

        // Always stop motors when done
        drive(0, 0, 0);
    }

    // Helper method to keep error calculated perfectly between -180 and 180
    private double getAngleError(double targetAngle) {
        double error = targetAngle - getHeading();
        while (error > 180)  error -= 360;
        while (error < -180) error += 360;
        return error;
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
