package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;


@TeleOp(name = "Nebula Teleop", group = "TeleOp")
public class NebulaTeleop extends LinearOpMode{
    private final DriveTrain driveTrain = new DriveTrain();
    private final Outtake outTake = new Outtake();
    private final Intake intake = new Intake();

    @Override
    public void runOpMode() throws InterruptedException{
        driveTrain.HardwareMapSubsystem(hardwareMap);
        outTake.HardwareMapSubsystem(hardwareMap);
        intake.HardwareMapSubsystem(hardwareMap);

        waitForStart();
        while (opModeIsActive()){
            double drive = gamepad1.left_stick_x;
            double strafe = -gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;


            if (gamepad1.right_trigger > 0.01){
                intake.in();
            }
            else if (gamepad1.left_trigger > 0.01){
                intake.out();
            }
            else{
                intake.stop();
            }


            driveTrain.MecanumDrive(drive,strafe,rotation, gamepad1.left_stick_button ? 0.15 : 1);

            intake.update_telemetry();
            driveTrain.Telemetry();

            telemetry.update();
        }
    }
}
