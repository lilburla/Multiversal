package org.firstinspires.ftc.teamcode.code.Robot.Archive;

/**
 * A small, self-contained PID controller for closed-loop control such as
 * holding a heading, driving to an encoder target, or spinning a flywheel to a
 * target velocity.
 *
 * <p>Usage each loop:
 * <pre>{@code
 * PIDController pid = new PIDController(0.03, 0.0, 0.001);
 * ...
 * double output = pid.calculate(targetTicks, currentTicks);
 * motor.setPower(MathUtil.clampPower(output));
 * }</pre>
 *
 * Call {@link #reset()} whenever you start pursuing a new target so the
 * integral term and derivative history don't carry over.
 */
public class PIDController {

    private double kP, kI, kD;

    private double integralSum = 0.0;
    private double lastError = 0.0;
    private long lastTimeNanos = 0L;
    private boolean hasPrevious = false;

    /** Limit on the accumulated integral term to prevent windup. */
    private double integralLimit = Double.POSITIVE_INFINITY;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /** Updates the gains on the fly (e.g. from dashboard tuning). */
    public void setGains(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /** Caps the magnitude of the integral accumulator to prevent windup. */
    public void setIntegralLimit(double limit) {
        this.integralLimit = Math.abs(limit);
    }

    /**
     * Computes the control output for the given target and measurement.
     * Uses the real elapsed time between calls so tuning stays consistent even
     * if the loop rate varies.
     */
    public double calculate(double target, double current) {
        double error = target - current;
        long now = System.nanoTime();

        double derivative = 0.0;
        if (hasPrevious) {
            double dt = (now - lastTimeNanos) / 1e9;
            if (dt > 0) {
                integralSum += error * dt;
                integralSum = MathUtil.clamp(integralSum, -integralLimit, integralLimit);
                derivative = (error - lastError) / dt;
            }
        }

        lastError = error;
        lastTimeNanos = now;
        hasPrevious = true;

        return kP * error + kI * integralSum + kD * derivative;
    }

    /** Clears integral and derivative state. Call before pursuing a new target. */
    public void reset() {
        integralSum = 0.0;
        lastError = 0.0;
        hasPrevious = false;
    }
}
