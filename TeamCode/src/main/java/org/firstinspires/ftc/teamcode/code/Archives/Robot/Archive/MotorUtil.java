package org.firstinspires.ftc.teamcode.code.Robot.Archive;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Helpers for driving motors safely. Powers are always clamped to the legal
 * [-1.0, 1.0] range so a bad calculation can't be sent straight to the hardware.
 */
public class MotorUtil {

    /**
     * Sets a single motor's power, clamped to [-1.0, 1.0].
     */
    public void setMotorSpeed(DcMotor motor, double speed) {
        motor.setPower(MathUtil.clampPower(speed));
    }

    /**
     * Applies four powers to a drivetrain in the order
     * {frontLeft, frontRight, backLeft, backRight} — matching {@link DriveUtil}.
     * Each power is clamped before it is sent.
     *
     * @param powers array of exactly four values
     */
    public void setPowers(DcMotor frontLeft, DcMotor frontRight,
                          DcMotor backLeft, DcMotor backRight, double[] powers) {
        if (powers == null || powers.length != 4) {
            throw new IllegalArgumentException("Expected exactly 4 powers, got "
                    + (powers == null ? "null" : powers.length));
        }
        frontLeft.setPower(MathUtil.clampPower(powers[0]));
        frontRight.setPower(MathUtil.clampPower(powers[1]));
        backLeft.setPower(MathUtil.clampPower(powers[2]));
        backRight.setPower(MathUtil.clampPower(powers[3]));
    }

    /**
     * Stops one or more motors immediately by setting power to zero.
     */
    public void stop(DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setPower(0.0);
        }
    }

    /**
     * Sets the same run mode on one or more motors.
     */
    public void setMode(DcMotor.RunMode mode, DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    /**
     * Sets the same zero-power (BRAKE / FLOAT) behavior on one or more motors.
     */
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior, DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setZeroPowerBehavior(behavior);
        }
    }
}
