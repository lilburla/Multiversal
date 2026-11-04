package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMecanumDrive;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfig;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfigReader;

@Autonomous(name = "Park Blue", group = "Blue")
public class ParkBlue extends LinearOpMode {
    private static final double DRIVE_POWER = 0.4;
    private static final long PARK_MS = 1200;

    private final NewMecanumDrive drive = new NewMecanumDrive();

    @Override
    public void runOpMode() {
        RobotConfig config = RobotConfigReader.read(hardwareMap);
        drive.init(hardwareMap, config);

        waitForStart();

        forward(DRIVE_POWER, PARK_MS);
        drive.drive(0, 0, 0, false);
    }

    private void forward(double power, long milliseconds) {
        drive.drive(power, 0, 0, false);
        sleep(milliseconds);
        drive.drive(0, 0, 0, false);
    }
}
