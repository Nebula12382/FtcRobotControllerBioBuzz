package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Intake TeleOp", group = "TeleOp")
public class IntakeTeleOp extends LinearOpMode {

    private Intake intake;

    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);

        telemetry.addLine("Ready to start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                intake.in();
            } else if (gamepad1.left_bumper) {
                intake.out();
            } else {
                intake.stop();
            }

            telemetry.addData("Intake Power", intake.getPower());
            telemetry.update();
        }
    }
}
