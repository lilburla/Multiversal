package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util;

import java.util.Map;

public class RobotConfig {
    private final Map<String, String> values;

    public RobotConfig(Map<String, String> values) {
        this.values = values;
    }

    private String get(String key) {
        String value = values.get(key);
        return value == null ? "" : value;
    }

    public String getName() {
        return get("name");
    }

    public String getFrontLeftDriveName() {
        return get("frontLeftDrive");
    }

    public String getFrontRightDriveName() {
        return get("frontRightDrive");
    }

    public String getBackLeftDriveName() {
        return get("backLeftDrive");
    }

    public String getBackRightDriveName() {
        return get("backRightDrive");
    }

    public String getShootingServoName() {
        return get("servoGate");
    }

    public String getLimelightName() {
        return get("limelightName");
    }

    public String getIntakeMotorName() {
        return get("intakeMotor");
    }

    public String getImuName() {
        return get("imuName");
    }

    public String getFlywheelMotor1Name() {
        return get("rightShooter");
    }

    public String getFlywheelMotor2Name() {
        return get("leftShooter");
    }

}
