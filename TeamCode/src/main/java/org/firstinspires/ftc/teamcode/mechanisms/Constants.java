package org.firstinspires.ftc.teamcode.mechanisms;

public class Constants {
    //MechanumDrive
    public static final double TRIGGER_THRESHOLD = 0.8;
    public static final double REG_MODE = 0.6;
    public static final double SLOW_MODE = 0.15;

    //Intake
    public static final double INTAKE_POWER = 1;
    public static final double GATE_OPEN = 0;
    public static final double GATE_CLOSED = 1;

    //Flywheel
//TEMPORARILY NOT FINAL TO TEST FLYWHEEL SPEED FOR TELEOP
    public static double FLYWHEEL_VELOCITY = 155;
    //LEFT FLYWHEEL = FLYWHEEL1 (idk if thats left or right but whatever u get for tuning flywheel 1 put below)
    public static final double LEFT_FLYWHEEL_P = 95.5964;
    public static final double LEFT_FLYWHEEL_F = 12.8500;
    //RIGHT FLYWHEEL = FLYWHEEL2 (same thing whatever u get for tuning flywheel 2 put below)

    public static final double RIGHT_FLYWHEEL_P = 94.9098;
    public static final double RIGHT_FLYWHEEL_F = 13.8523;
}
