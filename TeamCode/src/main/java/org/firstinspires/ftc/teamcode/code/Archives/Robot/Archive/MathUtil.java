package org.firstinspires.ftc.teamcode.code.Robot.Archive;

/**
 * General math helpers used across TeleOp and Autonomous op modes.
 *
 * <p>Everything here is static and stateless, so call it directly, e.g.
 * {@code MathUtil.clamp(power, -1.0, 1.0)}.
 */
public final class MathUtil {

    // Utility class: prevent instantiation.
    private MathUtil() {}

    /**
     * Constrains {@code value} to the inclusive range [{@code min}, {@code max}].
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Constrains a motor power to the legal [-1.0, 1.0] range.
     */
    public static double clampPower(double power) {
        return clamp(power, -1.0, 1.0);
    }

    /**
     * Zeroes out small joystick values so a resting stick doesn't creep the robot.
     *
     * @param value     raw joystick axis, typically in [-1.0, 1.0]
     * @param deadband  magnitude below which the value is treated as zero (e.g. 0.05)
     */
    public static double applyDeadband(double value, double deadband) {
        return Math.abs(value) < deadband ? 0.0 : value;
    }

    /**
     * Squares the input while preserving its sign. Gives finer control near the
     * center of a joystick and full power at the extremes.
     */
    public static double squareInput(double value) {
        return Math.copySign(value * value, value);
    }

    /**
     * Raises the input to a power while preserving its sign. Useful for tuning
     * how aggressive the control curve feels ({@code exponent} of 2 or 3 is common).
     */
    public static double curveInput(double value, double exponent) {
        return Math.copySign(Math.pow(Math.abs(value), exponent), value);
    }

    /**
     * Linearly maps {@code value} from the input range to the output range.
     * Does not clamp — pass the result through {@link #clamp} if you need bounds.
     */
    public static double map(double value, double inMin, double inMax,
                             double outMin, double outMax) {
        return outMin + (value - inMin) * (outMax - outMin) / (inMax - inMin);
    }

    /**
     * @return true if {@code value} is within {@code tolerance} of {@code target}.
     * Handy for "have we reached the setpoint?" checks.
     */
    public static boolean atTarget(double value, double target, double tolerance) {
        return Math.abs(value - target) <= tolerance;
    }

    /**
     * Wraps an angle in degrees into the range (-180, 180].
     */
    public static double wrapDegrees(double degrees) {
        while (degrees > 180) degrees -= 360;
        while (degrees <= -180) degrees += 360;
        return degrees;
    }

    /**
     * Wraps an angle in radians into the range (-PI, PI].
     */
    public static double wrapRadians(double radians) {
        while (radians > Math.PI) radians -= 2 * Math.PI;
        while (radians <= -Math.PI) radians += 2 * Math.PI;
        return radians;
    }

    /**
     * Linear interpolation between {@code a} and {@code b} by fraction {@code t}.
     */
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }
}
