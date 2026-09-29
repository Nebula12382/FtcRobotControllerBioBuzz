package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private final DcMotor motor;

    private static final double INTAKE_POWER = 1.0;
    private static final double OUTTAKE_POWER = -1.0;

    public Intake(HardwareMap hardwareMap) {
        this(hardwareMap, "intakeMotor");
    }

    public Intake(HardwareMap hardwareMap, String motorName) {
        motor = hardwareMap.get(DcMotor.class, motorName);
        motor.setDirection(DcMotor.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void in() {
        motor.setPower(INTAKE_POWER);
    }

    public void out() {
        motor.setPower(OUTTAKE_POWER);
    }

    public void stop() {
        motor.setPower(0);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public double getPower() {
        return motor.getPower();
    }
}
