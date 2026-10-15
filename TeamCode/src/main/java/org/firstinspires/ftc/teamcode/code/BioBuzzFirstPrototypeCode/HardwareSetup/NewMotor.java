package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class NewMotor{
    private DcMotor motor;
    public NewMotor(HardwareMap hwMap, String motorName) {
        motor = hwMap.get(DcMotor.class, motorName);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //Encoders are sensors that track motion and turn it into electrical signals to measure position, speed, and direction
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        motor.setZeroPowerBehavior(behavior);
    }
    public void setMode(DcMotor.RunMode behavior){
        motor.setMode(behavior);
    }
    public void reverse(){
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setPower(double power){
        motor.setPower(power);
    }
}