package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private DcMotor intake;
    private Servo gate;

    public void setPos(double angle) {
        gate.setPosition(angle);
    }

    public void init(HardwareMap hwMap) {
        intake = hwMap.get(DcMotor.class, "intake_motor");
        gate = hwMap.get(Servo.class, "shooting_servo");
        setPos(Constants.GATE_CLOSED);
    }

    public void inTake() {
        intake.setPower(-1 * Constants.INTAKE_POWER);
    }

    public void outTake() {
        intake.setPower(Constants.INTAKE_POWER);
    }

    public void open() {
        setPos(Constants.GATE_OPEN);
    }

    public void close() {
        setPos(Constants.GATE_CLOSED);
    }
}
