package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlyShooter {
    private DcMotorEx fly1, fly2;

    public void init(HardwareMap hwMap) {
        fly1 = hwMap.get(DcMotorEx.class, "flywheel_1");
        fly2 = hwMap.get(DcMotorEx.class, "flywheel_2");
        // Add the following lines once the flywheel has been tuned:
        // PIDFCoefficients pidCoefficients = new PIDFCoefficients(Constants.F, 0, 0, Constants.P);
        // fly1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidCoefficients);
        // fly2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidCoefficients);
    }

    public void run() {
        fly1.setVelocity(Constants.FLYWHEEL_VELOCITY);
        fly2.setVelocity(-1 * Constants.FLYWHEEL_VELOCITY);
    }
}
