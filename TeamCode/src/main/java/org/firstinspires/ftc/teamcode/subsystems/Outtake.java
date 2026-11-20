package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    //We need two motors for outtake
    private DcMotorEx flywheelPollen,flywheelNectar;
    private CRServo pollenServo;

    //encoder ticks per second at full speed for a 6000 RPM motor (goBILDA Yellow Jacket / REV HD Hex)
    private static final double MAX_VELOCITY = 2800;
    private static final double TARGET_VELOCITY = 4000 * 28 / 60.0;

    private static final double NOMINAL_VOLTAGE = 12.0;

    //kV is base power to hold speed, kP pushes it back up after a shot
    //tune kV first w kP at 0, then bump kP till it recovers fast
    private static final double kV = 1.0 / MAX_VELOCITY;
    private static final double kP = 0.0005;

    //how close to target speed before it can shoot
    private static final double READY_TOLERANCE = 50;

    private double power = 0;

    public void HardwareMapSubsystem(HardwareMap hardwareMap){
        //flywheelNectar = hardwareMap.get(DcMotorEx.class,"flywheelNectar");
        flywheelPollen = hardwareMap.get(DcMotorEx.class,"flywheelPollen");
        pollenServo = hardwareMap.get(CRServo.class,"pollenServo");
        //flywheelNectar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //we do our own speed control, encoder still works
        flywheelPollen.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //coasts instead of braking
        flywheelPollen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    //call every loop, low battery gets more power so speed stays the same
    public void turn_motor(double volt){
        double error = TARGET_VELOCITY - flywheelPollen.getVelocity();
        power = (kV * TARGET_VELOCITY) * (NOMINAL_VOLTAGE / volt) + kP * error;
        power = Math.max(0, Math.min(power, 1));
        flywheelPollen.setPower(power);
        //flywheelNectar.setPower(power);
    }

    public void stop(){
        //flywheelNectar.setPower(0);
        power = 0;
        flywheelPollen.setPower(0);
    }

    //true when flywheel is up to speed
    public boolean isReady(){
        return Math.abs(TARGET_VELOCITY - flywheelPollen.getVelocity()) < READY_TOLERANCE;
    }

    public void update_telemetry(Telemetry telemetry){
        telemetry.addData("Outtake target (ticks/s)", TARGET_VELOCITY);
        //telemetry.addData("Outtake Nectar (ticks/s)", flywheelNectar.getVelocity());
        telemetry.addData("Outtake Pollen (ticks/s)", flywheelPollen.getVelocity());
        telemetry.addData("Outtake power", power);
        telemetry.addData("Outtake ready", isReady());
    }
    public void turnServo(){
        pollenServo.setPower(1.0);
    }

    public void offServo(){
        pollenServo.setPower(0.0);
    }
}
