package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Nebula Teleop", group = "TeleOp")
public class NebulaTeleop extends LinearOpMode{
    private final DriveTrain driveTrain = new DriveTrain();
    private final Outtake outTake = new Outtake();
    private final Intake intake = new Intake();
    private final ElapsedTime timer = new ElapsedTime();

    private boolean outtakeOn = false;
    private boolean lastA = false;
    private boolean running = false;
    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain.HardwareMapSubsystem(hardwareMap);
        outTake.HardwareMapSubsystem(hardwareMap);
        intake.HardwareMapSubsystem(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rotation = gamepad1.right_stick_x;


            //right trigger for intake
            if (gamepad1.right_trigger > 0.1) {
                intake.in();
            }
            //ELSE left trigger for getting jammed stuff out
            else if (gamepad1.left_trigger > 0.1) {
                intake.out();
            }
            //LASTLY voids the motor if no trigger is pressed
            else {
                intake.stop();
            }

            if (gamepad1.a && !lastA)
                outtakeOn = !outtakeOn;

            lastA = gamepad1.a;
//
            if (outtakeOn) {
                outTake.turn_motor();
            } else {
                outTake.stop();
            }

            if (gamepad1.right_bumper && !running){
                outTake.turnServo();
                timer.reset();
                running = true;
            }

            if (running && timer.seconds() >= 0.5){
                outTake.offServo();
                running = false;
            }

            //calls the Mecanum Drive method every time it looks
            driveTrain.MecanumDrive(drive, strafe, rotation, gamepad1.left_stick_button ? 0.15 : 1);
            //updates telemetry lines
//            intake.update_telemetry(telemetry);
//            outTake.update_telemetry(telemetry);
            driveTrain.Telemetry(telemetry);

            //flushes the telemetry lines to update it
            telemetry.update();
        }
    }
}

