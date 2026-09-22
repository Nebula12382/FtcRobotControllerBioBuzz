package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private DcMotor intake;
    private Telemetry telemetry;

    private static final double intake_power = 1.0;
    private static final double outtake_power = -1.0;



    public void HardwareMapSubsystem(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotor.class,"intake");
        intake.setDirection(DcMotor.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void in(){
        intake.setPower(intake_power);
    }

    public void out(){
        intake.setPower(outtake_power);
    }

    public void stop(){
        intake.setPower(0);
    }

    public double get_power(){
        return (intake.getPower());
    }

    public void update_telemetry(){
        if (get_power() == intake_power){
            telemetry.addLine("Intake motor current state: Intaking");
        }
        else if(get_power() == outtake_power){
            telemetry.addLine("Intake motor current state: Outtaking");
        }
        else if(get_power() == 0){
            telemetry.addLine("Intake motor current state: 0");
        }
        else {
            telemetry.addLine("Null");
        }
    }
}
