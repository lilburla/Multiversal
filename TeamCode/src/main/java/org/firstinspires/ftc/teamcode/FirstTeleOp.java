package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ConfiguredRobot;
import org.firstinspires.ftc.teamcode.mechanisms.Constants;

@TeleOp
public class FirstTeleOp extends OpMode {
    ConfiguredRobot robot = new ConfiguredRobot();
    double forward, strafe, rotate;
    private int velocityToAddToFlywheel = 100;

    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addLine("Init Complete");
    }

    @Override
    public void loop() {
        robot.runShooter();
        forward = -1 * gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad1.left_trigger >= Constants.TRIGGER_THRESHOLD) {
            robot.drive(forward * Constants.SLOW_MODE, strafe * Constants.SLOW_MODE, rotate * Constants.SLOW_MODE);
        } else {
            robot.drive(forward * Constants.REG_MODE, strafe * Constants.REG_MODE, rotate * Constants.REG_MODE);
        }

        if (gamepad1.right_trigger >= Constants.TRIGGER_THRESHOLD) {
            robot.openServoGate();
        } else {
            robot.closeServoGate();
        }

        if (gamepad1.b) {
            robot.outtake();
        } else if (gamepad1.a){
            robot.intake();
        }else{
            robot.stopIntakeMotors();
        }

        if (gamepad1.dpad_left) {
            robot.resetHeading();
        }

        //Testing
        if(gamepad1.dpadRightWasPressed()){
            if(velocityToAddToFlywheel == 1){
                velocityToAddToFlywheel = 100;
            }else {
                velocityToAddToFlywheel /= 10;
            }
        }
        if(gamepad1.leftBumperWasPressed()){
            Constants.FLYWHEEL_VELOCITY -= velocityToAddToFlywheel;
        }else if (gamepad1.rightBumperWasPressed()){
            Constants.FLYWHEEL_VELOCITY += velocityToAddToFlywheel;

        }
        telemetry.addData("velocity: ", Constants.FLYWHEEL_VELOCITY);
        telemetry.addData("step: ", velocityToAddToFlywheel);



    }
}