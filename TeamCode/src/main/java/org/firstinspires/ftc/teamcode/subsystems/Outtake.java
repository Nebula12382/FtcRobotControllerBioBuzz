package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    //We need two motors for outtake
    private DcMotorEx flywheelPollen,flywheelNectar;

    //encoder ticks per second at full speed for a 6000 RPM motor (goBILDA Yellow Jacket / REV HD Hex)
    private static final double MAX_VELOCITY = 2800;
    private static final double TARGET_VELOCITY = 4000 * 28 / 60.0;

    public void HardwareMapSubsystem(HardwareMap hardwareMap){
        flywheelNectar = hardwareMap.get(DcMotorEx.class,"flywheelNectar");
        flywheelPollen = hardwareMap.get(DcMotorEx.class,"flywheelPollen");
        flywheelNectar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheelPollen.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turn_motor(){
        flywheelPollen.setVelocity(TARGET_VELOCITY);
        flywheelNectar.setVelocity(TARGET_VELOCITY);
    }

    public void stop(){
        flywheelNectar.setPower(0);
        flywheelPollen.setPower(0);
    }

    public void update_telemetry(Telemetry telemetry){
        telemetry.addData("Outtake target (ticks/s)", TARGET_VELOCITY);
        telemetry.addData("Outtake Nectar (ticks/s)", flywheelNectar.getVelocity());
        telemetry.addData("Outtake Pollen (ticks/s)", flywheelPollen.getVelocity());
    }
}
