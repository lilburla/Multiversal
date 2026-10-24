package org.firstinspires.ftc.teamcode.code.Robot.Archive;

/**
 * A simple wall-clock timer for time-based autonomous steps and timeouts.
 *
 * <pre>{@code
 * StopWatch timer = new StopWatch();
 * timer.reset();
 * while (opModeIsActive() && !timer.hasElapsed(2.0)) {
 *     // drive forward for two seconds
 * }
 * }</pre>
 */
public class StopWatch {

    private long startNanos = System.nanoTime();

    /** Restarts the timer from zero. */
    public void reset() {
        startNanos = System.nanoTime();
    }

    /** @return elapsed time since the last reset, in seconds. */
    public double seconds() {
        return (System.nanoTime() - startNanos) / 1e9;
    }

    /** @return elapsed time since the last reset, in milliseconds. */
    public double milliseconds() {
        return (System.nanoTime() - startNanos) / 1e6;
    }

    /** @return true once at least {@code seconds} have passed since the reset. */
    public boolean hasElapsed(double seconds) {
        return seconds() >= seconds;
    }
}
