package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    //We need two motors for outtake
    private DcMotorEx outtakePollen,outtakeNectar;

    // Encoder ticks per second at full power; measure on your robot
    private static final double MAX_VELOCITY = 2800;
    // Keep below MAX_VELOCITY so the PID has room to speed back up after a shot
    private static final double TARGET_VELOCITY = 0.85 * MAX_VELOCITY;

    // Starting values from the FTC motor PIDF tuning guide; tune on the robot
    private static final double F = 32767 / MAX_VELOCITY;
    private static final double P = 0.1 * F;
    private static final double I = 0.1 * P;
    private static final double D = 0;


    public void HardwareMapSubsystem(HardwareMap hardwareMap){
        outtakeNectar = hardwareMap.get(DcMotorEx.class,"outtakeNectar");
        outtakePollen = hardwareMap.get(DcMotorEx.class,"outtakePollen");

        outtakeNectar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakePollen.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        outtakePollen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeNectar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        outtakeNectar.setVelocityPIDFCoefficients(P, I, D, F);
        outtakePollen.setVelocityPIDFCoefficients(P, I, D, F);
    }

    public void turn_motor(){
        outtakePollen.setVelocity(TARGET_VELOCITY);
        outtakeNectar.setVelocity(TARGET_VELOCITY);
    }

    public void stop(){
        outtakeNectar.setPower(0);
        outtakePollen.setPower(0);
    }

    public double get_power(){
        return ((outtakeNectar.getPower()+outtakePollen.getPower())/2);
    }

    public void update_telemetry(Telemetry telemetry){
        telemetry.addData("Outtake target", TARGET_VELOCITY);
        telemetry.addData("Outtake Nectar velocity", outtakeNectar.getVelocity());
        telemetry.addData("Outtake Pollen velocity", outtakePollen.getVelocity());
    }
}
