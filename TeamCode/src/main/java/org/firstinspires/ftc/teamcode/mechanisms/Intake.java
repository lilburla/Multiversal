package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    //Ryan changes: removed shooting servo from this class
    private DcMotor intake;


    public void init(HardwareMap hwMap) {
        intake = hwMap.get(DcMotor.class, "intake_motor");

    }

    public void inTake() {
        intake.setPower(-1 * Constants.INTAKE_POWER);
    }

    public void outTake() {
        intake.setPower(Constants.INTAKE_POWER);
    }
    public void stop() {
        intake.setPower(0);
    }
}
