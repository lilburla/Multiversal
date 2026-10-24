package org.firstinspires.ftc.teamcode.code.Robot.Archive;

/**
 * Converts between real-world distances and motor encoder ticks.
 *
 * <p>Create one instance per drivetrain (or per mechanism) with the numbers
 * from your motor and wheel spec sheet, then reuse it. Example for a goBILDA
 * 5202 series 312 RPM motor (537.7 ticks/rev) on a 96&nbsp;mm wheel:
 *
 * <pre>{@code
 * EncoderUtil drive = new EncoderUtil(537.7, 96.0 / 25.4, 1.0);
 * int ticks = drive.inchesToTicks(24);   // drive two feet
 * }</pre>
 */
public final class EncoderUtil {

    private final double ticksPerRev;
    private final double wheelDiameterInches;
    private final double gearRatio;

    /**
     * @param ticksPerRev          encoder counts per output-shaft revolution
     * @param wheelDiameterInches  driven wheel diameter in inches
     * @param gearRatio            external gearing (driven/driver); use 1.0 if direct
     */
    public EncoderUtil(double ticksPerRev, double wheelDiameterInches, double gearRatio) {
        this.ticksPerRev = ticksPerRev;
        this.wheelDiameterInches = wheelDiameterInches;
        this.gearRatio = gearRatio;
    }

    /** Distance the wheel travels in one motor revolution, in inches. */
    public double inchesPerRev() {
        return Math.PI * wheelDiameterInches * gearRatio;
    }

    /** Converts a target distance in inches to encoder ticks (rounded). */
    public int inchesToTicks(double inches) {
        return (int) Math.round(inches / inchesPerRev() * ticksPerRev);
    }

    /** Converts encoder ticks to a distance in inches. */
    public double ticksToInches(int ticks) {
        return ticks / ticksPerRev * inchesPerRev();
    }

    /** Converts a target distance in centimeters to encoder ticks (rounded). */
    public int cmToTicks(double cm) {
        return inchesToTicks(cm / 2.54);
    }

    /** Converts encoder ticks to a distance in centimeters. */
    public double ticksToCm(int ticks) {
        return ticksToInches(ticks) * 2.54;
    }
}
