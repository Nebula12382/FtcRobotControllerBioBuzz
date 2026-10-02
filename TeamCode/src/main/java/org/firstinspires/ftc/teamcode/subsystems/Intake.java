package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private DcMotorEx intake;

    //900 RPM on the 1000 RPM motor: 28 ticks per motor turn x 6:1 gearbox = 168 ticks per output turn
    private static final double TARGET_VELOCITY = 900 * 168 / 60.0;

    //the speed the intake was last told to run at, used for telemetry
    private double targetVelocity = 0;



    public void HardwareMapSubsystem(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotorEx.class,"intake");
        intake.setDirection(DcMotor.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void in(){
        targetVelocity = TARGET_VELOCITY;
        intake.setVelocity(targetVelocity);
    }

    public void out(){
        targetVelocity = -TARGET_VELOCITY;
        intake.setVelocity(targetVelocity);
    }

    public void stop(){
        targetVelocity = 0;
        intake.setPower(0);
    }

    public void update_telemetry(Telemetry telemetry){
        if (targetVelocity > 0){
            telemetry.addLine("Intake motor current state: Intaking");
        }
        else if(targetVelocity < 0){
            telemetry.addLine("Intake motor current state: Outtaking");
        }
        else {
            telemetry.addLine("Intake motor current state: 0");
        }
        telemetry.addData("Intake speed (ticks/s)", intake.getVelocity());
    }
}
