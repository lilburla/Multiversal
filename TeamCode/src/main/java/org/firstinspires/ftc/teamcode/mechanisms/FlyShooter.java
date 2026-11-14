package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

public class FlyShooter {
    //Ryan edits: moved shooting servo gate from intake class to this class because it made more sense
    private DcMotorEx fly1, fly2;

    private Servo gate;
    public void setPos(double angle) {
        gate.setPosition(angle);
    }


    public void init(HardwareMap hwMap) {
        gate = hwMap.get(Servo.class, "shooting_servo");
        setPos(Constants.GATE_CLOSED);
        fly1 = hwMap.get(DcMotorEx.class, "flywheel_1");
        fly2 = hwMap.get(DcMotorEx.class, "flywheel_2");
        PIDFCoefficients leftFlywheelPidCoefficients = new PIDFCoefficients(Constants.LEFT_FLYWHEEL_F, 0, 0, Constants.LEFT_FLYWHEEL_P);
        PIDFCoefficients rightFlywheelPidCoefficients = new PIDFCoefficients(Constants.RIGHT_FLYWHEEL_F, 0, 0, Constants.RIGHT_FLYWHEEL_P);

        fly1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, leftFlywheelPidCoefficients);
        fly2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, rightFlywheelPidCoefficients);
    }
    public void run() {
        fly1.setVelocity(Constants.FLYWHEEL_VELOCITY);
        fly2.setVelocity(-1 * Constants.FLYWHEEL_VELOCITY);
    }

    public void open() {
        setPos(Constants.GATE_OPEN);
    }
    public void close() {
        setPos(Constants.GATE_CLOSED);
    }
}
