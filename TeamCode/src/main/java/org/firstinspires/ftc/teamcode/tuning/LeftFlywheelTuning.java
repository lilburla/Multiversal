package org.firstinspires.ftc.teamcode.tuning;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "Left Flywheel Tuning")
public class LeftFlywheelTuning extends OpMode {
    private DcMotorEx fly1;
    public double highVelocity = 1500;
    double lowVelocity = 900;
    double curTargetVelocity = highVelocity;
    double P = 0;
    double F = 0;
    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 1;

    @Override
    public void init() {
        fly1 = hardwareMap.get(DcMotorEx.class, "flywheel_1");
        fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        PIDFCoefficients pidCoefficients = new PIDFCoefficients(P, 0, 0, F);
        fly1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidCoefficients);
        telemetry.addLine("Init Complete");
    }

    @Override
    public void loop() {
        if (gamepad1.yWasPressed()) {
            if(curTargetVelocity == highVelocity) {
                curTargetVelocity = lowVelocity;
            } else { curTargetVelocity = highVelocity; }
        }

        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            F -= stepSizes[stepIndex];
        }

        if (gamepad1.dpadRightWasPressed()) {
            F += stepSizes[stepIndex];
        }

        if (gamepad1.dpadDownWasPressed()) {
            P += stepSizes[stepIndex];
        }

        if (gamepad1.dpadUpWasPressed()) {
            P -= stepSizes[stepIndex];
        }

        PIDFCoefficients pidCoefficients = new PIDFCoefficients(P, 0, 0, F);
        fly1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidCoefficients);
        fly1.setVelocity(curTargetVelocity);

        double curAvgVelocity = fly1.getVelocity();
        double error = curTargetVelocity - curAvgVelocity;

        telemetry.addData("TargetVelocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curAvgVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("---------------------------------------");
        telemetry.addData("Tuning P", "%.4f (D-pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);
    }
}
