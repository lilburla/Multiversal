package org.firstinspires.ftc.teamcode.code.Robot.Archive;

/**
 * Limits how fast a value is allowed to change over time. Wrapping joystick
 * inputs in a slew rate limiter smooths sudden stick movements, which reduces
 * wheel slip and keeps a tall robot from tipping when the driver slams the stick.
 *
 * <pre>{@code
 * // Allow going from 0 to full power in no less than 0.4 s (rate = 2.5 / sec).
 * SlewRateLimiter driveLimiter = new SlewRateLimiter(2.5);
 * ...
 * double smooth = driveLimiter.calculate(-gamepad1.left_stick_y);
 * }</pre>
 */
public class SlewRateLimiter {

    private final double ratePerSecond;
    private double lastValue = 0.0;
    private long lastTimeNanos = System.nanoTime();

    /**
     * @param ratePerSecond maximum change in the value per second (must be &gt; 0)
     */
    public SlewRateLimiter(double ratePerSecond) {
        this.ratePerSecond = Math.abs(ratePerSecond);
    }

    /**
     * @param input desired target value
     * @return the value moved toward {@code input}, capped by the allowed rate
     */
    public double calculate(double input) {
        long now = System.nanoTime();
        double dt = (now - lastTimeNanos) / 1e9;
        lastTimeNanos = now;

        double maxChange = ratePerSecond * dt;
        double delta = MathUtil.clamp(input - lastValue, -maxChange, maxChange);
        lastValue += delta;
        return lastValue;
    }

    /** Snaps the internal state to {@code value} with no rate limiting. */
    public void reset(double value) {
        lastValue = value;
        lastTimeNanos = System.nanoTime();
    }
}
