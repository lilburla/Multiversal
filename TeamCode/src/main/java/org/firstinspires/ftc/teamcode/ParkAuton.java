package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.MechanumDrive;

@Autonomous
public class ParkAuton extends LinearOpMode {

    private static final double TRIGGER_THRESHOLD = 0.8;
    private static final double FLYWHEEL_POWER = 0.25;
    private static final double INTAKE_POWER = 0.4;
    private static final double GATE_OPEN = 0;
    private static final double GATE_CLOSED = 1;

    MechanumDrive drive = new MechanumDrive();
    private DcMotor fly1, fly2, intake;
    private Servo gate;
    private ElapsedTime timer;

    public void setPos(double angle) {
        gate.setPosition(angle);
    }

    @Override
    public void runOpMode() {
        drive.init(hardwareMap);
        fly1 = hardwareMap.get(DcMotor.class, "flywheel_1");
        fly2 = hardwareMap.get(DcMotor.class, "flywheel_2");
        intake = hardwareMap.get(DcMotor.class, "intake_motor");
        gate = hardwareMap.get(Servo.class, "shooting_servo");
        setPos(GATE_CLOSED);
        timer = new ElapsedTime();

        waitForStart();

        if (opModeIsActive()) {
            drive.drive(0.05, 0.0, 0.0);
            timer.reset();

            while (opModeIsActive() && timer.milliseconds() < 1500) {}

            drive.drive(0.0, 0.0, 0.0);
        }
    }
}
