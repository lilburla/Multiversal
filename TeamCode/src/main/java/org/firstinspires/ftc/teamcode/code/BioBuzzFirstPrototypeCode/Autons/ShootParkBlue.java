package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Autons;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewMotor;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup.NewPositionalServo;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfig;
import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Util.RobotConfigReader;

@Autonomous(name = "Shoot Park Blue", group = "Blue")
public class ShootParkBlue extends OpMode {
    private static final double FLYWHEEL_POWER = 0.35;
    private static final double INTAKE_POWER = 0.6;
    private static final double GATE_OPEN = 1.0;
    private static final double GATE_CLOSED = 0.5;
    private static final double SPIN_UP_SECONDS = 1.5;
    private static final double SHOOT_SECONDS = 3.0;

    private enum Step {
        SPIN_UP,
        SHOOT,
        PARK,
        DONE
    }

    private final PoseFactory poses = PoseFactory.degrees();
    private final Pose startPose = poses.of(8, 128, 0);
    private final Pose parkPose = poses.of(30, 128, 0);
    private final ElapsedTime timer = new ElapsedTime();

    private Follower follower;
    private NewMotor flywheel1;
    private NewMotor flywheel2;
    private NewMotor intake;
    private NewPositionalServo gate;
    private Step step = Step.SPIN_UP;

    private Path park() {
        return line(startPose, parkPose).linear(startPose, parkPose);
    }

    @Override
    public void init() {
        RobotConfig config = RobotConfigReader.read(hardwareMap);
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
        flywheel1 = new NewMotor(hardwareMap, config.getFlywheelMotor1Name());
        flywheel2 = new NewMotor(hardwareMap, config.getFlywheelMotor2Name());
        intake = new NewMotor(hardwareMap, config.getIntakeMotorName());
        gate = new NewPositionalServo(hardwareMap, config.getShootingServoName());
        gate.setServoPos(GATE_CLOSED);
    }

    @Override
    public void start() {
        step = Step.SPIN_UP;
        timer.reset();
    }

    @Override
    public void loop() {
        follower.update();
        switch (step) {
            case SPIN_UP:
                spinFlywheels(FLYWHEEL_POWER);
                if (timer.seconds() >= SPIN_UP_SECONDS) {
                    next(Step.SHOOT);
                }
                break;
            case SHOOT:
                gate.setServoPos(GATE_OPEN);
                intake.setPower(INTAKE_POWER);
                if (timer.seconds() >= SHOOT_SECONDS) {
                    stopShooter();
                    follower.follow(park());
                    next(Step.PARK);
                }
                break;
            case PARK:
                if (!follower.isBusy()) {
                    next(Step.DONE);
                }
                break;
            case DONE:
                stopShooter();
                break;
        }
        telemetry.addData("Step", step);
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
    }

    @Override
    public void stop() {
        stopShooter();
        follower.stop();
    }

    private void spinFlywheels(double power) {
        flywheel1.setPower(power);
        flywheel2.setPower(-power);
    }

    private void stopShooter() {
        gate.setServoPos(GATE_CLOSED);
        intake.setPower(0);
        spinFlywheels(0);
    }

    private void next(Step nextStep) {
        step = nextStep;
        timer.reset();
    }
}
