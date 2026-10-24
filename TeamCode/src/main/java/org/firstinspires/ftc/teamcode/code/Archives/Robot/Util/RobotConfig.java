package org.firstinspires.ftc.teamcode.code.Robot.Util;

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
        return get("frontLeftDriveName");
    }

    public String getFrontRightDriveName() {
        return get("frontRightDriveName");
    }

    public String getBackLeftDriveName() {
        return get("backLeftDriveName");
    }

    public String getBackRightDriveName() {
        return get("backRightDriveName");
    }

    public String getServoName() {
        return get("servoName");
    }

    public String getLimelightName() {
        return get("limelightName");
    }

    public String getIntakeMotorName() {
        return get("intakeMotorName");
    }

    public String getImuName() {
        return get("imuName");
    }

    public String getFlywheelMotor1Name() {
        return get("flywheelMotor1Name");
    }

    public String getFlywheelMotor2Name() {
        return get("flywheelMotor2Name");
    }
}
