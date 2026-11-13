package org.firstinspires.ftc.teamcode.AutonWithPedropathingVisualizer;

//useless cuz we dont have odom but alr made it so (slowed + reverb)
/*
import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class ShootCollectThenShootBlue {

    private final PoseFactory poseFactory = PoseFactory.degrees().mirrorX(72);
    poseFactory.mirrorY(72);

    private final Pose start = poseFactory.of(57.5969, 12.2583, 90);
    private final Pose point1 = poseFactory.of(11.3555, 20.4044, -16.3903);
    private final Pose point1Control1 = poseFactory.of(57.5323, 6.8223, 0);
    private final Pose point2 = poseFactory.of(9.2347, 9.4357, 89.5323);
    private final Pose point2Control1 = poseFactory.of(9.3018, 17.6544, 0);
    private final Pose point3 = poseFactory.of(9.3339, 15.5009, 89.0629);
    private final Pose point4 = poseFactory.of(57.6514, 130.5861, -90.864);
    private final Pose point4Control1 = poseFactory.of(14.9204, 85.028, 0);
    private final Pose point4Control2 = poseFactory.of(57.6986, 133.7137, 0);

    public Path path1() {
        return Paths.curve(start, point1Control1, point1).reverseTangent();
    }

    public Path path2() {
        return Paths.curve(point1, point2Control1, point2).reverseTangent();
    }

    public Path path3() {
        return Paths.line(point2, point3).tangent();
    }

    public Path path4() {
        return Paths.curve(point3, point4Control1, point4Control2, point4).tangent();
    }
}
*/