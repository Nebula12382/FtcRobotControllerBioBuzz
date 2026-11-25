package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import com.qualcomm.robotcore.util.ElapsedTime;


//same as NebulaTeleop but the shooter runs at 1/3 speed for outreach demos
@TeleOp(name = "Outreach Teleop", group = "TeleOp")
public class OutreachTeleop extends LinearOpMode{
    private final DriveTrain driveTrain = new DriveTrain();
    private final Outtake outTake = new Outtake();
    private final Intake intake = new Intake();
    private final ElapsedTime timer = new ElapsedTime();
    private final ElapsedTime voltTimer = new ElapsedTime();

    private boolean outtakeOn = false;
    private boolean lastA = false;
    private boolean running = false;
    private double volt = 12.0;
    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain.HardwareMapSubsystem(hardwareMap);
        outTake.HardwareMapSubsystem(hardwareMap);
        intake.HardwareMapSubsystem(hardwareMap);

        //shooter at 1/3 speed
        outTake.speedScale = 1.0 / 3.0;

        waitForStart();
        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rotation = gamepad1.right_stick_x;

            //checks battery every 0.25 sec
            if (voltTimer.seconds() >= 0.25){
                volt = driveTrain.GetVolt(hardwareMap);
                voltTimer.reset();
            }

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

            //A toggles flywheel
            if (gamepad1.a && !lastA)
                outtakeOn = !outtakeOn;

            lastA = gamepad1.a;

            if (outtakeOn) {
                outTake.turn_motor(volt);
            } else {
                outTake.stop();
            }

            //right bumper shoots, only when flywheel is up to speed
            if (gamepad1.right_bumper && !running && outtakeOn && outTake.isReady()){
                outTake.turnServo();
                timer.reset();
                running = true;
            }

            if (running && timer.seconds() >= 2.0){
                outTake.offServo(); // <-- Add this so it stops spinning!
                running = false;
            }

            //calls the Mecanum Drive method every time it looks, left stick click is slow mode
            driveTrain.MecanumDriveVoltage(drive, strafe, rotation, gamepad1.left_stick_button ? 0.15 : 1, volt);
            //updates telemetry lines
//            intake.update_telemetry(telemetry);
            outTake.update_telemetry(telemetry);
            driveTrain.Telemetry(telemetry);
            telemetry.addData("Battery (V)", volt);

            //flushes the telemetry lines to update it
            telemetry.update();
        }
    }
}
