package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.BooleanSupplier;

public class ConfiguredRobot {
    MechanumDrive drive = new MechanumDrive();
    Intake intake = new Intake();
    FlyShooter shooter = new FlyShooter();

    public void init(HardwareMap hwMap) {
        drive.init(hwMap);
        shooter.init(hwMap);
        intake.init(hwMap);
    }
    public void drive(double forward, double strafe, double rotate) {
        drive.driveFieldOriented(forward, strafe, rotate);
    }

    public void driveAuton(double forward, double strafe, double rotate) {
        drive.drive(forward, strafe, rotate);
    }

    public void turnToAngle(double targetAngleDegrees, double power, BooleanSupplier opModeActive) {
        drive.turnToAngle(targetAngleDegrees, power, opModeActive);
    }

    public void resetHeading() {
        drive.resetHeading();
    }
    public void runShooter() {
        shooter.run();
    }


    public void intake() {
        intake.inTake();
    }

    public void outtake() {
        intake.outTake();
    }

    public void openServoGate() {
        shooter.open();
    }

    public void closeServoGate() {
        shooter.close();
    }
    public void stopIntake(){intake.stop();}
}
