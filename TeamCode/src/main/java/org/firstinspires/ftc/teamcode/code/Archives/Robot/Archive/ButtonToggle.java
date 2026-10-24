package org.firstinspires.ftc.teamcode.code.Robot.Archive;

/**
 * Rising-edge detector for gamepad buttons.
 *
 * <p>Because {@code loop()} runs many times per second, a held button reads
 * {@code true} for dozens of cycles. This class reports {@code true} only on the
 * cycle where the button first goes down, which is what you almost always want
 * for toggling a mechanism on/off or stepping through modes.
 *
 * <pre>{@code
 * ButtonToggle clawToggle = new ButtonToggle();
 * ...
 * // in loop():
 * if (clawToggle.update(gamepad1.a)) {
 *     clawOpen = !clawOpen;   // flips once per press
 * }
 * }</pre>
 */
public class ButtonToggle {

    private boolean lastState = false;

    /**
     * Feed the current button state each loop.
     *
     * @return true only on the loop where the button transitions from up to down
     */
    public boolean update(boolean currentState) {
        boolean pressed = currentState && !lastState;
        lastState = currentState;
        return pressed;
    }

    /** Resets the detector as if the button had never been pressed. */
    public void reset() {
        lastState = false;
    }
}
