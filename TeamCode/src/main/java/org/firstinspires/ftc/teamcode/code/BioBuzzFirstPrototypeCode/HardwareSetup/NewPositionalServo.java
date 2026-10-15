package org.firstinspires.ftc.teamcode.code.BioBuzzFirstPrototypeCode.HardwareSetup;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class NewPositionalServo {
    private Servo servoPos;
    public NewPositionalServo(HardwareMap hwMap, String deviceName){
        servoPos = hwMap.get(Servo.class, deviceName);
    }
    public void setServoPos(double angle){
        servoPos.setPosition(angle);
    }
    public void reverse(){
        servoPos.setDirection(Servo.Direction.REVERSE);
    }
    public void forward(){
        servoPos.setDirection(Servo.Direction.FORWARD);
    }

    public void setScaleRange(double min, double max){
        servoPos.scaleRange(min, max);
    }
}
