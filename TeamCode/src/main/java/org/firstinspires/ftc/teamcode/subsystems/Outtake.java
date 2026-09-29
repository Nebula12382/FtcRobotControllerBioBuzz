package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    //We need two motors for outtake
    private DcMotor outtakePollen,outtakeNectar;
    private static final double outtake_speed = 1.0;


    public void HardwareMapSubsystem(HardwareMap hardwareMap){
        outtakeNectar = hardwareMap.get(DcMotor.class,"outtakeNectar");
        outtakePollen = hardwareMap.get(DcMotor.class,"outtakePollen");
        outtakePollen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        outtakeNectar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void turn_motor(){
        outtakePollen.setPower(outtake_speed);
        outtakeNectar.setPower(outtake_speed);
    }

    public void stop(){
        outtakeNectar.setPower(0);
        outtakePollen.setPower(0);
    }

    public double get_power(){
        return ((outtakeNectar.getPower()+outtakePollen.getPower())/2);
    }
    public void update_telemetry(Telemetry telemetry){
        telemetry.addData("Outtake power", get_power());
    }
}
