package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.PedroPathing;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Localizer;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig(PedroConfig cfg) {
        return new MecanumConfig(c -> {
            c.frontLeftName.set(cfg.getString("frontLeftName", "front_left_drive"));
            c.frontRightName.set(cfg.getString("frontRightName", "front_right_drive"));
            c.backLeftName.set(cfg.getString("backLeftName", "back_left_drive"));
            c.backRightName.set(cfg.getString("backRightName", "back_right_drive"));
            c.frontLeftDirection.set(cfg.getMotorDirection("frontLeftDirection", DcMotorSimple.Direction.REVERSE));
            c.frontRightDirection.set(cfg.getMotorDirection("frontRightDirection", DcMotorSimple.Direction.FORWARD));
            c.backLeftDirection.set(cfg.getMotorDirection("backLeftDirection", DcMotorSimple.Direction.REVERSE));
            c.backRightDirection.set(cfg.getMotorDirection("backRightDirection", DcMotorSimple.Direction.FORWARD));
        });
    }

    public static PinpointConfig localizerConfig(PedroConfig cfg) {
        return new PinpointConfig(c -> {
            c.name.set(cfg.getString("pinpointName", "pinpoint"));
            c.podType.set(cfg.getPodType("podType", GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD));
            c.xPodOffset.set(cfg.getDouble("xPodOffset", 0.0));
            c.yPodOffset.set(cfg.getDouble("yPodOffset", 0.0));
            c.xPodDirection.set(cfg.getPodDirection("xPodDirection", GoBildaPinpointDriver.EncoderDirection.FORWARD));
            c.yPodDirection.set(cfg.getPodDirection("yPodDirection", GoBildaPinpointDriver.EncoderDirection.FORWARD));
            c.globalDistanceUnit.set(DistanceUnit.INCH);
            c.offsetUnits.set(DistanceUnit.INCH);
        });
    }

    public static ForesightConfig foresightConfig(PedroConfig cfg) {
        return new ForesightConfig(c -> {
            double threshold = cfg.getDouble("translationalThreshold", 2.5);

            Controller primaryForward = Controller.proportional(cfg.getDouble("forwardTranslationalPrimary", 0.1));
            Controller secondaryForward = Controller.proportional(cfg.getDouble("forwardTranslationalSecondary", 0.1));
            Controller primaryStrafe = Controller.proportional(cfg.getDouble("strafeTranslationalPrimary", 0.1));
            Controller secondaryStrafe = Controller.proportional(cfg.getDouble("strafeTranslationalSecondary", 0.1));

            c.forwardTranslational.set(Controller.piecewise(secondaryForward).put(threshold, primaryForward));
            c.strafeTranslational.set(Controller.piecewise(secondaryStrafe).put(threshold, primaryStrafe));

            c.coast.set(Controller.proportionalFeedforward(cfg.getDouble("coast", 0.0)));
            c.brake.set(Controller.proportionalFeedforward(cfg.getDouble("brake", 0.0)));

            c.headingFeedback.set(Controller.proportional(cfg.getDouble("headingFeedback", 1.0)));
            c.headingBrakeCoefficients.set(Vector2D.cartesian(
                    cfg.getDouble("headingBrakeLinear", 0.0),
                    cfg.getDouble("headingBrakeQuadratic", 0.0)));

            c.linearBrakeCoefficients.set(Matrix.diag(
                    cfg.getDouble("forwardBrakeLinear", 0.0),
                    cfg.getDouble("strafeBrakeLinear", 0.0)));
            c.quadraticBrakeCoefficients.set(Matrix.diag(
                    cfg.getDouble("forwardBrakeQuadratic", 0.0),
                    cfg.getDouble("strafeBrakeQuadratic", 0.0)));

            c.maxAchievableForwardVelocity.set(cfg.getDouble("maxAchievableForwardVelocity", 60.0));
            c.maxAchievableStrafeVelocity.set(cfg.getDouble("maxAchievableStrafeVelocity", 45.0));
            c.naturalForwardDeceleration.set(cfg.getDouble("naturalForwardDeceleration", 30.0));
            c.naturalStrafeDeceleration.set(cfg.getDouble("naturalStrafeDeceleration", 50.0));
        });
    }

    public static Localizer localizer(HardwareMap h, PedroConfig cfg) {
        return new PinpointLocalizer(h, localizerConfig(cfg));
    }

    public static Follower create(HardwareMap h) {
        PedroConfig cfg = PedroConfigReader.read(h);
        return new Follower(
                localizer(h, cfg),
                new Mecanum(h, drivetrainConfig(cfg)),
                new Foresight(foresightConfig(cfg))
        );
    }
}
