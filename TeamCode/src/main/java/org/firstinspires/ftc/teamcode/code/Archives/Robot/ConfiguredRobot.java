package org.firstinspires.ftc.teamcode.code.Robot;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.code.Robot.Util.RobotConfig;
import org.firstinspires.ftc.teamcode.code.Robot.Util.RobotConfigReader;

public class ConfiguredRobot {
    public final RobotConfig config;

    public DcMotorEx frontLeftDrive;
    public DcMotorEx frontRightDrive;
    public DcMotorEx backLeftDrive;
    public DcMotorEx backRightDrive;
    public DcMotorEx intakeMotor;
    public DcMotorEx flywheelMotor1;
    public DcMotorEx flywheelMotor2;
    public Servo servo;
    public Limelight3A limelight;
    public IMU imu;

    public ConfiguredRobot(HardwareMap hardwareMap) {
        config = RobotConfigReader.read(hardwareMap);

        frontLeftDrive = getMotor(hardwareMap, config.getFrontLeftDriveName(), DcMotor.Direction.REVERSE);
        backLeftDrive = getMotor(hardwareMap, config.getBackLeftDriveName(), DcMotor.Direction.REVERSE);
        frontRightDrive = getMotor(hardwareMap, config.getFrontRightDriveName(), DcMotor.Direction.FORWARD);
        backRightDrive = getMotor(hardwareMap, config.getBackRightDriveName(), DcMotor.Direction.FORWARD);
        intakeMotor = getMotor(hardwareMap, config.getIntakeMotorName(), DcMotor.Direction.FORWARD);
        flywheelMotor1 = getMotor(hardwareMap, config.getFlywheelMotor1Name(), DcMotor.Direction.FORWARD);
        flywheelMotor2 = getMotor(hardwareMap, config.getFlywheelMotor2Name(), DcMotor.Direction.FORWARD);

        if (isConfigured(config.getServoName())) {
            servo = hardwareMap.get(Servo.class, config.getServoName());
            servo.setDirection(Servo.Direction.FORWARD);
        }
        if (isConfigured(config.getLimelightName())) {
            limelight = hardwareMap.get(Limelight3A.class, config.getLimelightName());
        }
        if (isConfigured(config.getImuName())) {
            imu = hardwareMap.get(IMU.class, config.getImuName());
            imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.LEFT)));
        }
    }

    private static boolean isConfigured(String deviceName) {
        return !deviceName.trim().isEmpty();
    }

    private static DcMotorEx getMotor(HardwareMap hardwareMap, String deviceName, DcMotor.Direction direction) {
        if (!isConfigured(deviceName)) {
            return null;
        }
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, deviceName);
        motor.setDirection(direction);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        return motor;
    }

    private static void setPower(DcMotorEx motor, double power) {
        if (motor != null) {
            motor.setPower(power);
        }
    }

    public void drive(double forward, double strafe, double rotate) {
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxSpeed = 0.7;
        double maxPower = 1.0;
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        setPower(frontLeftDrive, maxSpeed * (frontLeftPower / maxPower));
        setPower(backLeftDrive, maxSpeed * (backLeftPower / maxPower));
        setPower(frontRightDrive, maxSpeed * (frontRightPower / maxPower));
        setPower(backRightDrive, maxSpeed * (backRightPower / maxPower));
    }

    public void setServoPosition(double position) {
        if (servo != null) {
            servo.setPosition(position);
        }
    }

    public double getHeading(AngleUnit angleUnit) {
        return imu == null ? 0.0 : imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

    public void intake(double power) {
        setPower(intakeMotor, power);
    }

    public void launch(double power) {
        setPower(flywheelMotor1, power);
        setPower(flywheelMotor2, power);
    }
}
