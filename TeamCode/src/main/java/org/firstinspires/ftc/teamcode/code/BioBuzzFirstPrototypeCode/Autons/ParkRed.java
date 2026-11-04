package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.Autons;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.PedroPathing.Constants;

@Autonomous(name = "Park Red", group = "Red")
public class ParkRed extends OpMode {
    private final PoseFactory poses = PoseFactory.degrees();
    private final Pose startPose = poses.of(8, 40, 0);
    private final Pose parkPose = poses.of(30, 40, 0);

    private Follower follower;

    private Path park() {
        return line(startPose, parkPose).linear(startPose, parkPose);
    }

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        follower.follow(park());
    }

    @Override
    public void loop() {
        follower.update();
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Busy", follower.isBusy());
    }

    @Override
    public void stop() {
        follower.stop();
    }
}
